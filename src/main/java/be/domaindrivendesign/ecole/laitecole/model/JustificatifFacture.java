package be.domaindrivendesign.ecole.laitecole.model;

import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class JustificatifFacture extends Aggregate implements RuleObject {

    //region Propriétés
    /// <summary>
    /// Obtient la date de la facture.
    /// </summary>
    /// <value>
    /// La date de la facture.
    /// </value>
    protected LocalDateTime date;
    /// <summary>
    /// Obtient le numéro de la facture
    /// </summary>
    /// <value>
    /// Le numéro de la facture.
    /// </value>
    protected String numero;
    /// <summary>
    /// Obtient le montant de la facture.
    /// </summary>
    /// <value>
    /// Le montant de la facture.
    /// </value>
    protected BigDecimal montant;
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected JustificatifFacture(UUID id) {
        super(id);
    }

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="JustificatifFacture"/>.
    /// </summary>
    /// <param name="date">La date.</param>
    /// <param name="numero">Le numéro.</param>
    /// <param name="montant">Le montant.</param>
    /// <returns>Une référence sur l'objet <see cref="JustificatifFacture"/> nouvelle créé.</returns>
    public static JustificatifFacture creer(LocalDateTime date, String numero, BigDecimal montant) {
        JustificatifFacture justificatifFacture = new JustificatifFacture(UUID.randomUUID());

        justificatifFacture.date = date;
        justificatifFacture.numero = numero;
        justificatifFacture.montant = montant;

        RuleGuard.mandatory(justificatifFacture, justificatifFacture::getDate);
        RuleGuard.mandatory(justificatifFacture, justificatifFacture::getNumero);
        RuleGuard.greaterThanInvariant(justificatifFacture, justificatifFacture::getMontant, BigDecimal.valueOf(0));

        return justificatifFacture;
    }
    //endregion

    //region getters & setter*

    public LocalDateTime getDate() {
        return date;
    }

    protected void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNumero() {
        return numero;
    }

    protected void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    protected void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    //endregion
}
