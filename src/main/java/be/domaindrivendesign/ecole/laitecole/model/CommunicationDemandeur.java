package be.domaindrivendesign.ecole.laitecole.model;

import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommunicationDemandeur extends Aggregate implements RuleObject {
    //region Propriétés
    /// <summary>
    /// Obtient la date d'émission.
    /// </summary>
    /// <value>
    /// Date d'émission.
    /// </value>
    public LocalDateTime emisLe;
    /// <summary>
    /// Obtient la date de réponse.
    /// </summary>
    /// <value>
    /// Date de réponse.
    /// </value>
    public LocalDateTime reponduLe;
    /// <summary>
    /// Obtient la description.
    /// </summary>
    /// <value>
    /// La description.
    /// </value>
    public String description;
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected CommunicationDemandeur(UUID id) {
        super(id);
    }

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="CommunicationDemandeur"/>.
    /// </summary>
    /// <param name="emisLe">Date d'émission.</param>
    /// <param name="reponduLe">Date de réponse.</param>
    /// <param name="description">Description de la communication.</param>
    /// <returns>Une référence sur l'objet <see cref="CommunicationDemandeur"/> nouvellement créé.</returns>
    public static CommunicationDemandeur creer(LocalDateTime emisLe, LocalDateTime reponduLe, String description) {
        CommunicationDemandeur communication = new CommunicationDemandeur(UUID.randomUUID());

        communication.emisLe = emisLe;
        communication.reponduLe = reponduLe;
        communication.description = description;

        RuleGuard.mandatory(communication, communication::getEmisLe);
        RuleGuard.mandatory(communication, communication::getDescription);

        if (reponduLe != null && emisLe != null) {
            RuleGuard.after(communication, communication::getReponduLe, communication::getEmisLe);
        }

        return communication;
    }
    //endregion

    /// <summary>
    /// Répond au demandeur.
    /// </summary>
    /// <param name="reponduLe">Date de la réponse.</param>
    public void reponseDemandeur(LocalDateTime reponduLe) {
        this.reponduLe = reponduLe;

        // TODO RuleGuards
//        if (RuleGuard.After(this, () => ReponduLe, reponduLe, () => EmisLe, EmisLe)) {
//            RuleGuard.After(this, () => this.ReponduLe, reponduLe, () => this.EmisLe, this.EmisLe);
//        }

        setState(EntityStateType.Modified);
    }

    public LocalDateTime getEmisLe() {
        return emisLe;
    }

    protected void setEmisLe(LocalDateTime emisLe) {
        this.emisLe = emisLe;
    }

    public LocalDateTime getReponduLe() {
        return reponduLe;
    }

    protected void setReponduLe(LocalDateTime reponduLe) {
        this.reponduLe = reponduLe;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }
}
