package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Adresse extends ValueObject {

    @Column(name = "ADRESSE_ADR")
    protected String adr;
    @Column(name = "ADRESSE_CODEPOSTAL")
    protected int codePostal;
    @Column(name = "ADRESSE_LOCALITE")
    protected String localite;

    protected Adresse() {
    }

    public Adresse(String adr, int codePostal, String localite) {
        //noinspection UnusedAssignment
        @SuppressWarnings("UnusedAssignment")
        Adresse adresse = this;

        // Assign
        this.adr = adr;
        this.codePostal = codePostal;
        this.localite = localite;

        // Guard
        RuleGuard.mandatory(this, this::getAdr);
        RuleGuard.mandatory(this, this::getLocalite);
        RuleGuard.between(this, this::getCodePostal, 1000, 9999, RuleSeverityType.Error);

    }
    //endregion

    // region Getters
    public String getAdr() {
        return adr;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public String getLocalite() {
        return localite;
    }
    //endregion
}
