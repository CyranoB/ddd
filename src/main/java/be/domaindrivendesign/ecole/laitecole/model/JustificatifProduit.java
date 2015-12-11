package be.domaindrivendesign.ecole.laitecole.model;

import be.domaindrivendesign.ecole.laitecole.type.CodeProduitType;
import be.domaindrivendesign.ecole.laitecole.type.UniteProduitType;
import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import java.math.BigDecimal;
import java.util.UUID;

public class JustificatifProduit extends Aggregate implements RuleObject {

    //region Propriétés
    /// <summary>
    /// Obtient le code du produit.
    /// </summary>
    /// <value>
    /// Le code du produit.
    /// </value>
    protected CodeProduitType codeProduit;
    /// <summary>
    /// Obtient le libellé du produit.
    /// </summary>
    /// <value>
    /// Le libellé du produit.
    /// </value>
    protected String libelleProduit;
    /// <summary>
    /// Obtient l'unité du produit. 
    /// </summary>
    /// <value>
    /// L'unité du produit.
    /// </value>
    protected UniteProduitType uniteProduit;
    /// <summary>
    /// Obtient la quantité fournie.
    /// </summary>
    /// <value>
    /// La quantité fournie.
    /// </value>
    protected int quantiteFournie;
    /// <summary>
    /// Obtient le montant unitaire.
    /// </summary>
    /// <value>
    /// Le montant unitaire.
    /// </value>
    protected BigDecimal montantUnitaire;
    /// <summary>
    /// Obtient le montant total.
    /// </summary>
    /// <value>
    /// Le montant total.
    /// </value>
    protected BigDecimal montantTotal;
    /// <summary>
    /// Obtient le conditionnement unitaire. 
    /// </summary>
    /// <value>
    /// Le conditionnement unitaire.
    /// </value>
    protected BigDecimal conditionnementUnitaire;
    /// <summary>
    /// Obtient le prix par élève par portion.
    /// </summary>
    /// <value>
    /// Le prix par élève par portion.
    /// </value>
    protected BigDecimal prixPayeParEleveParPortion;
    /// <summary>
    /// Obtient la quantité de portion. 
    /// </summary>
    /// <value>
    /// La quantité de portion.
    /// </value>
    protected BigDecimal portion;
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected JustificatifProduit(UUID id) {
        super(id);
    }

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="JustificatifProduit"/>.
    /// </summary>
    /// <param name="codeProduit">Le code du produit.</param>
    /// <param name="libelleProduit">Le libellé du produit.</param>
    /// <param name="uniteProduit">L'unité du produit.</param>
    /// <param name="quantiteFournie">La quantité fournie.</param>
    /// <param name="montantUnitaire">Le montant unitaire.</param>
    /// <param name="montantTotal">Le montant total.</param>
    /// <param name="conditionnementUnitaire">Le conditionnement unitaire.</param>
    /// <param name="prixPayerParEleveParPortion">Le prix par élève par portion.</param>
    /// <param name="portion">La quantité de portions.</param>
    /// <returns></returns>
    public static JustificatifProduit creer(CodeProduitType codeProduit, String libelleProduit, UniteProduitType uniteProduit,
                                            int quantiteFournie, BigDecimal montantUnitaire, BigDecimal montantTotal, BigDecimal conditionnementUnitaire,
                                            BigDecimal prixPayerParEleveParPortion, BigDecimal portion) {
        JustificatifProduit justificationProduit = new JustificatifProduit(UUID.randomUUID());

        justificationProduit.codeProduit = codeProduit;
        justificationProduit.libelleProduit = libelleProduit;
        justificationProduit.uniteProduit = uniteProduit;
        justificationProduit.quantiteFournie = quantiteFournie;
        justificationProduit.montantUnitaire = montantUnitaire;
        justificationProduit.montantTotal = montantTotal;
        justificationProduit.conditionnementUnitaire = conditionnementUnitaire;
        justificationProduit.prixPayeParEleveParPortion = prixPayerParEleveParPortion;
        justificationProduit.portion = portion;

        RuleGuard.mandatory(justificationProduit, justificationProduit::getCodeProduit);
        RuleGuard.mandatory(justificationProduit, justificationProduit::getLibelleProduit);
        RuleGuard.mandatory(justificationProduit, justificationProduit::getUniteProduit);
        RuleGuard.greaterOrEqualThanInvariant(justificationProduit, justificationProduit::getQuantiteFournie, 0);
        RuleGuard.greaterOrEqualThanInvariant(justificationProduit, justificationProduit::getMontantUnitaire, BigDecimal.valueOf(0));
        RuleGuard.greaterOrEqualThanInvariant(justificationProduit, justificationProduit::getMontantTotal, BigDecimal.valueOf(0));
        RuleGuard.greaterOrEqualThanInvariant(justificationProduit, justificationProduit::getConditionnementUnitaire, BigDecimal.valueOf(0));
        RuleGuard.greaterOrEqualThanInvariant(justificationProduit, justificationProduit::getPrixPayeParEleveParPortion, BigDecimal.valueOf(0));
        RuleGuard.greaterOrEqualThanInvariant(justificationProduit, justificationProduit::getPortion, BigDecimal.valueOf(0));

        return justificationProduit;
    }
    //endregion

    //region Getters & setters

    public CodeProduitType getCodeProduit() {
        return codeProduit;
    }

    protected void setCodeProduit(CodeProduitType codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getLibelleProduit() {
        return libelleProduit;
    }

    protected void setLibelleProduit(String libelleProduit) {
        this.libelleProduit = libelleProduit;
    }

    public UniteProduitType getUniteProduit() {
        return uniteProduit;
    }

    protected void setUniteProduit(UniteProduitType uniteProduit) {
        this.uniteProduit = uniteProduit;
    }

    public int getQuantiteFournie() {
        return quantiteFournie;
    }

    protected void setQuantiteFournie(int quantiteFournie) {
        this.quantiteFournie = quantiteFournie;
    }

    public BigDecimal getMontantUnitaire() {
        return montantUnitaire;
    }

    protected void setMontantUnitaire(BigDecimal montantUnitaire) {
        this.montantUnitaire = montantUnitaire;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    protected void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public BigDecimal getConditionnementUnitaire() {
        return conditionnementUnitaire;
    }

    protected void setConditionnementUnitaire(BigDecimal conditionnementUnitaire) {
        this.conditionnementUnitaire = conditionnementUnitaire;
    }

    public BigDecimal getPrixPayeParEleveParPortion() {
        return prixPayeParEleveParPortion;
    }

    protected void setPrixPayeParEleveParPortion(BigDecimal prixPayeParEleveParPortion) {
        this.prixPayeParEleveParPortion = prixPayeParEleveParPortion;
    }

    public BigDecimal getPortion() {
        return portion;
    }

    protected void setPortion(BigDecimal portion) {
        this.portion = portion;
    }

    //endrgion
}
