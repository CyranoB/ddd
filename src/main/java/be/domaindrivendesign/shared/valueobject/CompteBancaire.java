package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

public class CompteBancaire extends ValueObject {

    protected String titulaire;
    protected BusinessIdentifierCode bic;
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
