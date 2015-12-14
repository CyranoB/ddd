package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BusinessIdentifierCode extends ValueObject {

    @Column(name = "BIC_PART01")
    protected String part01;
    @Column(name = "BIC_PART02")
    protected String part02;
    @Column(name = "BIC_PART03")
    protected String part03;

    private BusinessIdentifierCode() {
    }

    public BusinessIdentifierCode(String part01, String part02, String part03) {
        this.part01 = part01;
        this.part02 = part02;
        this.part03 = part03;

        RuleGuard.digitOnly(this, this::getPart01);
        RuleGuard.digitOnly(this, this::getPart02);
        RuleGuard.digitOnly(this, this::getPart03);

        RuleGuard.length(this, this::getPart01, 4);
        RuleGuard.length(this, this::getPart02, 4);
        RuleGuard.length(this, this::getPart03, 3);
    }

    @Override
    public String toString() {
        return String.format("%s-%s-%s", part01, part02, part03);
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
}
