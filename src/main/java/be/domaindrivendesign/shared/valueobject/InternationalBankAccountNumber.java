package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

public class InternationalBankAccountNumber extends ValueObject {

    protected String part01;
    protected String part02;
    protected String part03;
    protected String part04;

    private InternationalBankAccountNumber() {
    }

    public InternationalBankAccountNumber(String part01, String part02, String part03, String part04) {
        RuleGuard.digitOnly(this, this::getPart01);
        RuleGuard.digitOnly(this, this::getPart02);
        RuleGuard.digitOnly(this, this::getPart03);
        RuleGuard.digitOnly(this, this::getPart04);

        RuleGuard.length(this, this::getPart01, 2);
        RuleGuard.length(this, this::getPart02, 4);
        RuleGuard.length(this, this::getPart03, 4);
        RuleGuard.length(this, this::getPart04, 4);

        this.part01 = part01;
        this.part02 = part02;
        this.part03 = part03;
        this.part04 = part04;

    }

    @Override
    public String toString() {
        return String.format("BE-%s-%s-%s-%s", part01, part02, part03, part04);
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
}
