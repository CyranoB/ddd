package be.domaindrivendesign.ecole.module.laitecole.domain.model;

import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//TODO mappings
@Entity
public class CalendrierProgramme extends Aggregate implements RuleObject {

    //region Propriétés
    @Embedded
    protected PeriodDateHeure periodeDistribution;
    @OneToMany
    protected List<Journalier> journaliers;
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected CalendrierProgramme(UUID id) {
        super(id);
        journaliers = new ArrayList<>();
    }

    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <remarks>
    /// Ce constructeur est exclusivement utilisé par l'ORM pour créer des objets.
    /// </remarks>
    protected CalendrierProgramme() {
    }

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="CalendrierProgramme"/>.
    /// </summary>
    /// <param name="periodeDistribution">La période de distribution.</param>
    /// <param name="journaliers">La liste jours de distribution.</param>
    /// <returns>Une référence sur l'objet <see cref="CalendrierProgramme"/> nouvellement créé.</returns>
    public static CalendrierProgramme Creer(PeriodDateHeure periodeDistribution, List<Journalier> journaliers) {
        CalendrierProgramme programme = new CalendrierProgramme(UUID.randomUUID());

        // PeriodDistribution obligatoire
        programme.periodeDistribution = periodeDistribution;
        RuleGuard.mandatory(programme, programme::getPeriodeDistribution);


        // Pas recouvrement entre les journaliers
        for (int i = 0; i < journaliers.size(); i++) {
            for (int j = i + 1; j < journaliers.size(); j++) {
                if (journaliers.get(i).getJour() == journaliers.get(j).getJour()) {
                    RuleGuard.raiseViolation(programme, programme::getJournaliers, journaliers.get(i).getJour().toString(), RuleType.Overlap.typeValue, RuleSeverityType.Error);
                }
            }
        }


        // Au moins un élément dans le journalier
        RuleGuard.nbrOfElements(programme, programme::getJournaliers, 1, 6, false);

        // Assign
        programme.periodeDistribution = periodeDistribution;
        programme.journaliers = journaliers;

        return programme;
    }

    //endregion


    public PeriodDateHeure getPeriodeDistribution() {
        return periodeDistribution;
    }

    protected void setPeriodeDistribution(PeriodDateHeure periodeDistribution) {
        this.periodeDistribution = periodeDistribution;
    }

    public List<Journalier> getJournaliers() {
        return journaliers;
    }

    protected void setJournaliers(List<Journalier> journaliers) {
        this.journaliers = journaliers;
    }
}
