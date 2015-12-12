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
        this.mail = mail;

        RuleGuard.mandatory(this, this::getMail);
        RuleGuard.email(this, this::getMail);
    }

    public String getMail() {
        return mail;
    }

}