package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumeroIdentificationRegistreNational extends ValueObject {

    @Column(name = "NIRN_PART01")
    protected String part01;
    @Column(name = "NIRN_PART02")
    protected String part02;
    @Column(name = "NIRN_PART03")
    protected String part03;
    @Column(name = "NIRN_PART04")
    protected String part04;
    @Column(name = "NIRN_PART05")
    protected String part05;

    private NumeroIdentificationRegistreNational() {

    }

    public NumeroIdentificationRegistreNational(String part01, String part02, String part03, String part04, String part05) {
        this.part01 = part01;
        this.part02 = part02;
        this.part03 = part03;
        this.part04 = part04;
        this.part05 = part05;

        RuleGuard.digitOnly(this, this::getPart01);
        RuleGuard.digitOnly(this, this::getPart02);
        RuleGuard.digitOnly(this, this::getPart03);
        RuleGuard.digitOnly(this, this::getPart04);
        RuleGuard.digitOnly(this, this::getPart05);

        RuleGuard.length(this, this::getPart01, 2);
        RuleGuard.length(this, this::getPart02, 2);
        RuleGuard.length(this, this::getPart03, 2);
        RuleGuard.length(this, this::getPart04, 3);
        RuleGuard.length(this, this::getPart05, 2);
    }

    @Override
    public String toString() {
        return String.format("%s-%s-%s-%s-%s", part01, part02, part03, part04, part05);
    }

    public String getPart01() {
        return part01;
    }

    public String getPart02() {
        return part02;
    }

    public String getPart03() {
        return part03;
    }

    public String getPart04() {
        return part04;
    }

    public String getPart05() {
        return part05;
    }
}
