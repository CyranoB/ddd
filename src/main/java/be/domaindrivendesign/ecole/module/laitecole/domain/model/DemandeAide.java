package be.domaindrivendesign.ecole.module.laitecole.domain.model;

import be.domaindrivendesign.ecole.module.common.valueobject.ValidationDemande;
import be.domaindrivendesign.kernel.domain.model.AggregateRoot;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

//TODO finir entity mapping
@Entity
public class DemandeAide extends AggregateRoot implements RuleObject {

    protected String numeroDgarne;
    protected int anneeDeDistribution;
    protected int moisDeDistribution;
    @OneToMany
    protected List<Subvention> subventions;
    protected BigDecimal montant;
    protected BigDecimal montantTotalSubvention;
    protected BigDecimal montantTotalJustificatif;
    protected BigDecimal montantTotalFacture;
    protected ValidationDemande validationDemande;
    @OneToMany
    protected List<Justificatif> justificatifs;
    @OneToMany
    protected List<CommunicationDemandeur> communicationDemandeurs;


    //region Getters & setters
    public String getNumeroDgarne() {
        return numeroDgarne;
    }

    protected void setNumeroDgarne(String numeroDgarne) {
        this.numeroDgarne = numeroDgarne;
    }

    public int getAnneeDeDistribution() {
        return anneeDeDistribution;
    }

    protected void setAnneeDeDistribution(int anneeDeDistribution) {
        this.anneeDeDistribution = anneeDeDistribution;
    }

    public int getMoisDeDistribution() {
        return moisDeDistribution;
    }

    protected void setMoisDeDistribution(int moisDeDistribution) {
        this.moisDeDistribution = moisDeDistribution;
    }

    public List<Subvention> getSubventions() {
        return subventions;
    }

    protected void setSubventions(List<Subvention> subventions) {
        this.subventions = subventions;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    protected void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public BigDecimal getMontantTotalSubvention() {
        return montantTotalSubvention;
    }

    protected void setMontantTotalSubvention(BigDecimal montantTotalSubvention) {
        this.montantTotalSubvention = montantTotalSubvention;
    }

    public BigDecimal getMontantTotalJustificatif() {
        return montantTotalJustificatif;
    }

    protected void setMontantTotalJustificatif(BigDecimal montantTotalJustificatif) {
        this.montantTotalJustificatif = montantTotalJustificatif;
    }

    public BigDecimal getMontantTotalFacture() {
        return montantTotalFacture;
    }

    protected void setMontantTotalFacture(BigDecimal montantTotalFacture) {
        this.montantTotalFacture = montantTotalFacture;
    }

    public ValidationDemande getValidationDemande() {
        return validationDemande;
    }

    protected void setValidationDemande(ValidationDemande validationDemande) {
        this.validationDemande = validationDemande;
    }

    public List<Justificatif> getJustificatifs() {
        return justificatifs;
    }

    protected void setJustificatifs(List<Justificatif> justificatifs) {
        this.justificatifs = justificatifs;
    }

    public List<CommunicationDemandeur> getCommunicationDemandeurs() {
        return communicationDemandeurs;
    }

    protected void setCommunicationDemandeurs(List<CommunicationDemandeur> communicationDemandeurs) {
        this.communicationDemandeurs = communicationDemandeurs;
    }
    //endregion
}
