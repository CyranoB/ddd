package be.domaindrivendesign.ecole.module.laitecole.domain.model;

import be.domaindrivendesign.ecole.module.laitecole.domain.type.CodeProduitType;
import be.domaindrivendesign.ecole.module.laitecole.domain.type.UniteProduitType;
import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.UUID;

//TODO mappings
@Entity
public class Subvention extends Aggregate implements RuleObject {

    //region Propriétés
    protected CodeProduitType codeProduit;
    protected int quantiteLivree;
    protected UniteProduitType unite;
    protected BigDecimal montantUnitaire;
    protected BigDecimal montantTotal;
    //endregion

    //region Constructeurs
    protected Subvention() {
    }
    protected Subvention(UUID id) {
        super(id);
    }

    /// <summary>
    public static Subvention creer(CodeProduitType codeProduit, int quantiteLivree, UniteProduitType unite, BigDecimal montantUnitaire, BigDecimal montantTotal) {
        Subvention subvention = new Subvention(UUID.randomUUID());
        subvention.codeProduit = codeProduit;
        subvention.montantTotal = montantTotal;
        subvention.montantUnitaire = montantUnitaire;
        subvention.quantiteLivree = quantiteLivree;
        subvention.unite = unite;

        RuleGuard.mandatory(subvention, subvention::getCodeProduit);
        RuleGuard.greaterThanInvariant(subvention, subvention::getQuantiteLivree, 0);
        RuleGuard.mandatory(subvention, subvention::getUnite);
        RuleGuard.greaterThanInvariant(subvention, subvention::getMontantUnitaire, BigDecimal.valueOf(0));
        RuleGuard.greaterThanInvariant(subvention, subvention::getMontantTotal, BigDecimal.valueOf(0));

        return subvention;
    }
    //endregion    


    public CodeProduitType getCodeProduit() {
        return codeProduit;
    }

    protected void setCodeProduit(CodeProduitType codeProduit) {
        this.codeProduit = codeProduit;
    }

    public int getQuantiteLivree() {
        return quantiteLivree;
    }

    protected void setQuantiteLivree(int quantiteLivree) {
        this.quantiteLivree = quantiteLivree;
    }

    public UniteProduitType getUnite() {
        return unite;
    }

    protected void setUnite(UniteProduitType unite) {
        this.unite = unite;
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
}
