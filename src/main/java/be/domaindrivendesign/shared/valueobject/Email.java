package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

/**
 * Created by eddie on 23/11/15.
 */
public class Email extends ValueObject {

    private String mail;

    private Email() {
    }

    public Email(String mail) {
        //noinspection UnusedAssignment
        @SuppressWarnings("UnusedAssignment")
        Email email = null;

        this.mail = mail;

        RuleGuard.email(this, this::getMail);
        RuleGuard.mandatory(this, this::getMail);
    }

    public String getMail() {
        return mail;
    }

}