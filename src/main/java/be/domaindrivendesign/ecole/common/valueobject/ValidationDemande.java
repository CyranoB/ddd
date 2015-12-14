package be.domaindrivendesign.ecole.common.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class ValidationDemande extends ValueObject {
    @Column(name = "VALIDATIONDEMANDE_ENVALIDATIONLE", nullable = true)
    public LocalDateTime enValidationLe;
    @Column(name = "VALIDATIONDEMANDE_ACCEPTERLE")
    public LocalDateTime accepterLe;
    @Column(name = "VALIDATIONDEMANDE_REJETERLE")
    public LocalDateTime rejeterLe;
    @Column(name = "VALIDATIONDEMANDE_RAISONDEREJET")
    public String raisonDeRejet;

    //region Constructeurs
    /// <summary>
    /// Initialise une nouvelle instance de la classe <see cref="ValidationDemande"/>.
    /// </summary>
    public ValidationDemande() {
    }

    //endregion

    /// <summary>
    /// Initialise une nouvelle instance de la classe <see cref="ValidationDemande"/>.
    /// </summary>
    /// <param name="miseEnValidationLe">La date de mise en validation.</param>
    /// <param name="accepterLe">La date d'acceptation.</param>
    /// <param name="rejetLe">la date de rejet.</param>
    /// <param name="raisonDeRejet">La raison du rejet.</param>
    public ValidationDemande(LocalDateTime miseEnValidationLe, LocalDateTime accepterLe, LocalDateTime rejetLe, String raisonDeRejet) {
        this.enValidationLe = miseEnValidationLe;
        this.accepterLe = accepterLe;
        this.rejeterLe = rejetLe;
        this.raisonDeRejet = raisonDeRejet;
    }

    //region Gestion de la demande
    /// <summary>
    /// Met la demande en validation.
    /// </summary>
    /// <param name="enValidationLe">La date de mise en validation.</param>
    /// <returns>Une référence vers la <see cref="ValidationDemande"/> mise en validation.</returns>
    public ValidationDemande demanderLaValidation(LocalDateTime enValidationLe) {
        this.estImmutable();
        ValidationDemande nd = new ValidationDemande(enValidationLe, null, null, null);
        RuleGuard.mandatory(nd, nd::getEnValidationLe);
        return nd;
    }

    /// <summary>
    /// Accepte la demande.
    /// </summary>
    /// <param name="validerLe">La date d'acceptation.</param>
    /// <returns>Une référence vers la <see cref="ValidationDemande"/> acceptée.</returns>
    public ValidationDemande accepter(LocalDateTime validerLe) {
        this.estImmutable();
        ValidationDemande nd = new ValidationDemande(enValidationLe, validerLe, null, null);
        RuleGuard.mandatory(nd, nd::getAccepterLe);
        return nd;
    }

    /// <summary>
    /// Rejete la demande.
    /// </summary>
    /// <param name="rejeterLe">La date de rejet.</param>
    /// <param name="raisonDeRejet">La raison du rejet.</param>
    /// <returns>Une référence vers la <see cref="ValidationDemande"/> rejetée.</returns>
    public ValidationDemande rejeter(LocalDateTime rejeterLe, String raisonDeRejet) {
        this.estImmutable();
        ValidationDemande nd = new ValidationDemande(enValidationLe, null, rejeterLe, raisonDeRejet);
        RuleGuard.mandatory(nd, nd::getRejeterLe);
        RuleGuard.mandatory(nd, nd::getRaisonDeRejet);
        return nd;
    }

    /// <summary>
    /// Vérifie si l'objet est gelé ou s'il peut encore être modifié.
    /// </summary>
    /// <returns><c>True</c> si l'objet est immuable, sinon <c>False</c>.</returns>
    private boolean estImmutable() {
        // Calendrier non modifiable si demande agrément déjà acceptée ou refusée.
        if (this.accepterLe != null) {
            RuleGuard.raiseImmutableViolation(this, this::getAccepterLe, RuleSeverityType.BlockingError);
            return true;
        }

        if (rejeterLe != null) {
            RuleGuard.raiseImmutableViolation(this, this::getRejeterLe, RuleSeverityType.BlockingError);
            return true;
        }

        return false;
    }
    //endregion

    //region Propriétés

    //region Getters
    public LocalDateTime getEnValidationLe() {
        return enValidationLe;
    }

    public LocalDateTime getAccepterLe() {
        return accepterLe;
    }

    public LocalDateTime getRejeterLe() {
        return rejeterLe;
    }

    public String getRaisonDeRejet() {
        return raisonDeRejet;
    }
    //endregion
}
