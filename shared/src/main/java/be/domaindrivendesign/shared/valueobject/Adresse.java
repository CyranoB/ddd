package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;

/**
 * Created by eddie on 23/11/15.
 */
public class Adresse extends ValueObject {


    public String adr;
    public int codePostal;
    public String localite;

    private Adresse() {
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
        RuleGuard.nullOrEmpty(this, this::getAdr, RuleSeverityType.Error);
        RuleGuard.nullOrEmpty(this, this::getLocalite, RuleSeverityType.Error);
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
}
