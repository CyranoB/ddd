package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.common.type.ClasseType;
import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import java.time.LocalDateTime;
import java.util.UUID;

// <summary>
// Définit un décompte d'élève par classe.
// </summary>
public class ClasseComptage extends Aggregate implements RuleObject {

    //region Propriétés
    /// <summary>
    /// Obtient le type de classe
    /// </summary>
    public ClasseType classe;
    /// <summary>
    /// Obtient le nombre d'élèves
    /// </summary>
    public int nombreEleves;
    // Periode de validite du comptage
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objet.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected ClasseComptage(UUID id) {
        super(id);
    }

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="ClasseComptage"/>.
    /// </summary>
    /// <param name="classe">Le type de classe.</param>
    /// <param name="nombreEleves">Le nombre d'élève de la classe.</param>
    /// <returns>Une référence sur l'objet <see cref="ClasseComptage"/> nouvellement créé.</returns>
    public static ClasseComptage creer(ClasseType classe, int nombreEleves) {
        ClasseComptage classeComptage = new ClasseComptage(UUID.randomUUID());

        classeComptage.classe = classe;
        classeComptage.nombreEleves = nombreEleves;

        RuleGuard.mandatory(classeComptage, classeComptage::getClasse);
        RuleGuard.between(classeComptage, classeComptage::getNombreEleves, 0, 1000);

        return classeComptage;
    }
    //endregion

    /// <summary>
    /// Supprime (logiquement) la classe.
    /// </summary>
    /// <param name="supprimeLe">Date de suppression</param>
    public void supprimer(LocalDateTime supprimeLe) {
        deleteLogically(supprimeLe);
    }

    public ClasseType getClasse() {
        return classe;
    }

    public int getNombreEleves() {
        return nombreEleves;
    }
}
