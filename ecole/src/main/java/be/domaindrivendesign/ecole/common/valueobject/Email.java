package be.domaindrivendesign.ecole.common.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;

/**
 * Created by eddie on 23/11/15.
 */
public class EMail extends ValueObject {

    private String mail;

    private EMail() {
    }

    public EMail(String mail) {
        //noinspection UnusedAssignment
        @SuppressWarnings("UnusedAssignment")
        EMail email = null;

        // TODO EMail RuleGuard
        // RuleGuard.email(this, () => Mail, mail);
        RuleGuard.nullOrEmpty(this, this::getMail, RuleSeverityType.Error);

        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

}
