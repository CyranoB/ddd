package be.domaindrivendesign.ecole.module.laitecole.domain.model;

import be.domaindrivendesign.ecole.module.common.type.ClasseType;
import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import java.util.UUID;

public class ClasseParticipante extends Aggregate implements RuleObject {

    //region Propriétés
    protected ClasseType classe;
    protected int nbrEleves;
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected ClasseParticipante(UUID id) {
        super(id);
    }

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="ClasseParticipante"/>.
    /// </summary>
    /// <param name="classe">Le type de classe.</param>
    /// <param name="nbrEleves">Le nombre d'élève.</param>
    /// <returns></returns>
    public static ClasseParticipante Creer(ClasseType classe, int nbrEleves) {
        ClasseParticipante classeParticipante = new ClasseParticipante(UUID.randomUUID());

        classeParticipante.classe = classe;
        classeParticipante.nbrEleves = nbrEleves;

        RuleGuard.mandatory(classeParticipante, classeParticipante::getClasse);
        RuleGuard.greaterOrEqualThanInvariant(classeParticipante, classeParticipante::getNbrEleves, 1);

        return classeParticipante;
    }
    //endregion


    public ClasseType getClasse() {
        return this.classe;
    }

    protected void setClasse(ClasseType classe) {
        this.classe = classe;
    }

    public int getNbrEleves() {
        return nbrEleves;
    }

    protected void setNbrEleves(int nbrEleves) {
        this.nbrEleves = nbrEleves;
    }
}
