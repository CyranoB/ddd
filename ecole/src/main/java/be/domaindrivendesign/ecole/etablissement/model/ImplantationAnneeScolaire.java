package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.common.type.ClasseType;
import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/// <summary>
/// Définit une implantation par année scolaire.
/// </summary>
@Entity
public class ImplantationAnneeScolaire extends Aggregate implements RuleObject {

    @Column
    public String implantationNumeroReference;
    @Column
    public AnneeScolaire anneeScolaire;
    @OneToMany
    public List<ClasseComptage> classeComptages;

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected ImplantationAnneeScolaire(UUID id) {
        super(id);
    }

    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <remarks>
    /// Ce constructeur est exclusivement utilisé par l'ORM pour créer des objets.
    /// </remarks>
    protected ImplantationAnneeScolaire() {
    }

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="ImplantationAnneeScolaire"/>.
    /// </summary>
    /// <param name="implantationNumeroReference">Le numéro de référence de l'implantation.</param>
    /// <param name="anneScolaire">L'année scolaire.</param>
    /// <param name="classes">La liste des décomptes d'élèves par classe.</param>
    /// <returns>Une référence sur l'objet <see cref="ImplantationAnneeScolaire"/> nouvellement créé.</returns>
    public static ImplantationAnneeScolaire creer(String implantationNumeroReference, AnneeScolaire anneScolaire, ArrayList<ClasseComptage> classes) {
        ImplantationAnneeScolaire implantationAnneeScolaire = new ImplantationAnneeScolaire(UUID.randomUUID());

        implantationAnneeScolaire.implantationNumeroReference = implantationNumeroReference;
        implantationAnneeScolaire.anneeScolaire = anneScolaire;
        implantationAnneeScolaire.classeComptages = classes != null ? classes : new ArrayList<ClasseComptage>();


        RuleGuard.mandatory(implantationAnneeScolaire, implantationAnneeScolaire::getImplantationNumeroReference);
        RuleGuard.mandatory(implantationAnneeScolaire, implantationAnneeScolaire::getAnneeScolaire);
        RuleGuard.nbrOfElements(implantationAnneeScolaire, implantationAnneeScolaire::getClasseComptages, 1, Integer.MAX_VALUE);


        ImplantationAnneeScolaire.verifierDoublonClasseType(implantationAnneeScolaire.getClasseComptages());
        ImplantationAnneeScolaire.verifierEtCalculterLeTotalDesClasses(implantationAnneeScolaire);

        return implantationAnneeScolaire;
    }
    //endregion

    /// <summary>
    /// Vérifie la présence de doublons dans la liste des décomptes d'élève.
    /// </summary>
    /// <param name="classes">La liste des décomptes à vérifier.</param>
    private static void verifierDoublonClasseType(List<ClasseComptage> classes) {
        //TODO Simplify?
        List<ClasseType> classeTypes = classes.stream().map(ClasseComptage::getClasse).collect(Collectors.toList());
        Map<ClasseType, Long> counted = classeTypes.stream().collect(Collectors.groupingBy(o -> o, Collectors.counting()));
        counted.values().removeIf(x -> x <= 1);
        Set<ClasseType> duplicates = counted.keySet();
        for (ClasseType duplicate : duplicates) {
            RuleGuard.raiseUniqueViolation(classes.get(0), () -> classes.get(0).getClasse());
        }
    }

    /// <summary>
    /// Vérifie que tous les totaux par niveau (<see cref="ClasseType.Maternelle"/>, <see cref="ClasseType.Primaire"/> et <see cref="ClasseType.Secondaire"/>)
    /// sont présents et les calculs le cas échéans.
    /// </summary>
    /// <param name="implantationAnneeScolaire">L'implantation par année scolaire à utiliser</param>
    private static void verifierEtCalculterLeTotalDesClasses(ImplantationAnneeScolaire implantationAnneeScolaire) {
        // Calculer le nombre total maternelle
        Optional<ClasseComptage> maternelleTotalClasse =
                implantationAnneeScolaire.getClasseComptages().stream().filter(x -> x.getClasse().typeValue == ClasseType.Maternelle.typeValue).findFirst();
        if (!maternelleTotalClasse.isPresent())  // Si null, le total n'est pas effectué et on le calcul.
        {
            implantationAnneeScolaire.getClasseComptages().add(ClasseComptage.creer(ClasseType.Maternelle, 0));
        }

        // Calculer le nombre total primaire
        Optional<ClasseComptage> primaireTotalClasse =
                implantationAnneeScolaire.getClasseComptages().stream().filter(x -> x.getClasse().typeValue == ClasseType.Primaire.typeValue).findFirst();
        if (!primaireTotalClasse.isPresent())  // Si null, le total n'est pas effectué et on le calcul.
        {
            int totalPrimaire = implantationAnneeScolaire.getClasseComptages().stream().filter(
                    x -> x.getClasse().typeValue >= ClasseType.PremierePrimaire.typeValue && x.getClasse().typeValue <= ClasseType.SixiemePrimaire.typeValue).mapToInt(ClasseComptage::getNombreEleves).sum();
            implantationAnneeScolaire.getClasseComptages().add(ClasseComptage.creer(ClasseType.Primaire, totalPrimaire));
        }

        // Calculer le nombre total secondaire
        Optional<ClasseComptage> secondaireTotalClasse =
                implantationAnneeScolaire.getClasseComptages().stream().filter(x -> x.getClasse().typeValue == ClasseType.Secondaire.typeValue).findFirst();
        if (!secondaireTotalClasse.isPresent())  // Si null, le total n'est pas effectué et on le calcul.
        {
            int totalSecondaire = implantationAnneeScolaire.getClasseComptages().stream().filter(x -> x.getClasse().typeValue >= ClasseType.PremiereSecondaire.typeValue && x.getClasse().typeValue <= ClasseType.SixiemeSecondaire.typeValue)
                    .mapToInt(ClasseComptage::getNombreEleves).sum();
            implantationAnneeScolaire.getClasseComptages().add(ClasseComptage.creer(ClasseType.Secondaire, totalSecondaire));
        }
    }

    /// <summary>
    /// Modifie l'instance courante avec les informations d'une autre <see cref="ImplantationAnneeScolaire"/>.
    /// </summary>
    /// <param name="implantationAnneeScolaire">Les informations à utiliser.</param>
    /// <param name="dateApplication">La date de modification.</param>
    public void modifier(ImplantationAnneeScolaire implantationAnneeScolaire, LocalDateTime dateApplication) {
        // a modifier vs existant
        for (ClasseComptage aModifier : implantationAnneeScolaire.getClasseComptages()) {
            Optional<ClasseComptage> existant = this.classeComptages.stream().filter(x -> x.getClasse().typeValue == aModifier.getClasse().typeValue).findFirst();
            if (!existant.isPresent())
                this.classeComptages.add(aModifier);
            else if (existant.get().getNombreEleves() != aModifier.getNombreEleves()) {
                existant.get().supprimer(dateApplication);
                this.classeComptages.add(aModifier);
            }
        }

        // existant vs a modifier
        for (ClasseComptage existant : this.classeComptages) {
            Optional<ClasseComptage> aModifier = implantationAnneeScolaire.getClasseComptages().stream().filter(x -> x.getClasse().typeValue == existant.getClasse().typeValue).findFirst();
            if (!aModifier.isPresent())
                existant.supprimer(dateApplication);
        }

        ImplantationAnneeScolaire.verifierDoublonClasseType(implantationAnneeScolaire.getClasseComptages());
        ImplantationAnneeScolaire.verifierEtCalculterLeTotalDesClasses(implantationAnneeScolaire);
    }

    //region Getter
    public String getImplantationNumeroReference() {
        return implantationNumeroReference;
    }

    public AnneeScolaire getAnneeScolaire() {
        return anneeScolaire;
    }

    public List<ClasseComptage> getClasseComptages() {
        return classeComptages;
    }
    //endregion
}
