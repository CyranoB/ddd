package be.domaindrivendesign.ecole.module.laitecole.domain.model;

import be.domaindrivendesign.ecole.module.laitecole.domain.type.JourType;
import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.shared.valueobject.Heure;

import java.util.UUID;

public class Journalier extends Aggregate implements RuleObject {

    //region Propriétés
    /// <summary>
    /// Obtient le cadre de distribution.
    /// </summary>
    /// <value>
    /// Le cadre de distribution.
    /// </value>
    public String cadreDeDistribution;
    /// <summary>
    /// Obtient le jour de distribution.
    /// </summary>
    /// <value>
    /// Le jour de distribution.
    /// </value>
    public JourType jour;
    /// <summary>
    /// Obtient l'heure de distribution.
    /// </summary>
    /// <value>
    /// L'heure de distribution
    /// </value>
    public Heure heure;
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected Journalier(UUID id) {
        super(id);
    }

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="Journalier"/>.
    /// </summary>
    /// <param name="cadreDeDistribution">Le cadre de distribution.</param>
    /// <param name="jour">Le jour de distribution.</param>
    /// <param name="heure">L'heure de distribution.</param>
    /// <returns>Une référence sur l'objet <see cref="Journalier"/> nouvellement créé.</returns>
    public static Journalier Creer(String cadreDeDistribution, JourType jour, Heure heure) {
        Journalier journalier = new Journalier(UUID.randomUUID());

        journalier.cadreDeDistribution = cadreDeDistribution;
        journalier.jour = jour;
        journalier.heure = heure;

        RuleGuard.mandatory(journalier, journalier::getCadreDeDistribution);
        RuleGuard.mandatory(journalier, journalier::getJour);
        RuleGuard.mandatory(journalier, journalier::getHeure);

        return journalier;
    }
    //endregion


    public String getCadreDeDistribution() {
        return cadreDeDistribution;
    }

    protected void setCadreDeDistribution(String cadreDeDistribution) {
        this.cadreDeDistribution = cadreDeDistribution;
    }

    public JourType getJour() {
        return jour;
    }

    protected void setJour(JourType jour) {
        this.jour = jour;
    }

    public Heure getHeure() {
        return heure;
    }

    protected void setHeure(Heure heure) {
        this.heure = heure;
    }
}
