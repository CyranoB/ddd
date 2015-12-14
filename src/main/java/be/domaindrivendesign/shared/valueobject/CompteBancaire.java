package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import javax.persistence.*;

@Embeddable
public class CompteBancaire extends ValueObject {

    @Column(name = "COMPTEBANCAIRE_TITULAIRE")
    protected String titulaire;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "part01", column = @Column(name = "COMPTEBANCAIRE_BIC_PART01")),
            @AttributeOverride(name = "part02", column = @Column(name = "COMPTEBANCAIRE_BIC_PART02")),
            @AttributeOverride(name = "part03", column = @Column(name = "COMPTEBANCAIRE_BIC_PART03"))
    })
    protected BusinessIdentifierCode bic;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "part01", column = @Column(name = "COMPTEBANCAIRE_IBAN_PART01")),
            @AttributeOverride(name = "part02", column = @Column(name = "COMPTEBANCAIRE_IBAN_PART02")),
            @AttributeOverride(name = "part03", column = @Column(name = "COMPTEBANCAIRE_IBAN_PART03")),
            @AttributeOverride(name = "part04", column = @Column(name = "COMPTEBANCAIRE_IBAN_PART04"))
    })
    protected InternationalBankAccountNumber iban;

    protected CompteBancaire() {
    }

    public CompteBancaire(BusinessIdentifierCode bic, InternationalBankAccountNumber iban, String titulaire) {

        this.titulaire = titulaire;
        this.iban = iban;
        this.bic = bic;

        RuleGuard.mandatory(this, this::getBic);
        RuleGuard.mandatory(this, this::getIban);
        RuleGuard.mandatory(this, this::getTitulaire);
    }

    public String getTitulaire() {
        return titulaire;
    }

    public BusinessIdentifierCode getBic() {
        return bic;
    }

    public InternationalBankAccountNumber getIban() {
        return iban;
    }
}
