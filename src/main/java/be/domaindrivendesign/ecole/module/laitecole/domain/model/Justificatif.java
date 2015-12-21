package be.domaindrivendesign.ecole.module.laitecole.domain.model;

import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//TODO Faire mapping
@Entity
public class Justificatif extends Aggregate implements RuleObject {


    //region Propriétés
    /// <summary>
    /// Obtient le numéro de référence de l'école.
    /// </summary>
    /// <value>
    /// Le numéro de référence de l'école.
    /// </value>
    protected int ecoleNumeroReference;
    /// <summary>
    /// Obtient le numéro de référence de l'implantation.
    /// </summary>
    /// <value>
    /// Le numéro de référence de l'implantation.
    /// </value>
    protected int implantationNumeroReference;
    /// <summary>
    /// Obtient la liste des factures.
    /// </summary>
    /// <value>
    /// Les factures.
    /// </value>
    @OneToMany
    protected List<JustificatifFacture> factures;
    /// <summary>
    /// Obtient la liste des produits.
    /// </summary>
    /// <value>
    /// Les produits.
    /// </value>
    @OneToMany
    protected List<JustificatifProduit> produits;
    //endregion

    //region Constructeurs

    // <summary>
    /// Constructeur privé afin d'empêcher la création illicite d'objet.
    /// </summary>
    /// <remarks>
    /// Ce constructeur est exclusivement utilisé par l'ORM pour créer les objets.
    /// </remarks>
    protected Justificatif() {
    }

    /// <summary>
    /// Constructeur privé afin d'empêcher la création illicite d'objet.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected Justificatif(UUID id) {
        super(id);
    }

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="Justificatif"/>.
    /// </summary>
    /// <param name="ecoleId">Le numéro d'identification de l'école.</param>
    /// <param name="implantationId">Le numéro d'identification de l'implantation.</param>
    /// <returns>Une référence sur l'objet <see cref="Justificatif"/> nouvellement créé.</returns>
    public static Justificatif creer(int ecoleId, int implantationId) {
        Justificatif justificatif = new Justificatif(UUID.randomUUID());
        justificatif.factures = new ArrayList<>();
        justificatif.produits = new ArrayList<>();

        justificatif.ecoleNumeroReference = ecoleId;
        justificatif.implantationNumeroReference = implantationId;

        RuleGuard.mandatory(justificatif, justificatif::getEcoleNumeroReference);

        return justificatif;
    }
    //endregion

    //region Gestion des factures
    /// <summary>
    /// Ajoute une facture.
    /// </summary>
    /// <param name="facture">La facture.</param>
    public void ajouterFacture(JustificatifFacture facture) {
        RuleGuard.notInList(this, this::getFactures, facture, RuleSeverityType.BlockingError);
        if (RuleGuard.notInList(this, () -> this.getFactures().stream().map(JustificatifFacture::getNumero).collect(Collectors.toList()), facture.getNumero()))
            this.factures.add(facture);
    }
    //endregion

    //region Gestion des produits
    /// <summary>
    /// Ajoute un produit.
    /// </summary>
    /// <param name="produit">Le produit.</param>
    public void ajouterProduit(JustificatifProduit produit) {
        RuleGuard.notInList(this, this::getProduits, produit, RuleSeverityType.BlockingError);
        this.produits.add(produit);
    }
    //endregion

    //region Getters & setters

    public int getEcoleNumeroReference() {
        return ecoleNumeroReference;
    }

    public void setEcoleNumeroReference(int ecoleNumeroReference) {
        this.ecoleNumeroReference = ecoleNumeroReference;
    }

    public int getImplantationNumeroReference() {
        return implantationNumeroReference;
    }

    public void setImplantationNumeroReference(int implantationNumeroReference) {
        this.implantationNumeroReference = implantationNumeroReference;
    }

    public List<JustificatifFacture> getFactures() {
        return factures;
    }

    public void setFactures(List<JustificatifFacture> factures) {
        this.factures = factures;
    }

    public List<JustificatifProduit> getProduits() {
        return produits;
    }

    public void setProduits(List<JustificatifProduit> produits) {
        this.produits = produits;
    }

    //endregion
}
