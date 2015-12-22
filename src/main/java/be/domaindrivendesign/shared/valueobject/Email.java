package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import javax.persistence.Embeddable;

@Embeddable
public class Email extends ValueObject {

    private String mail;

    protected Email() {
    }

    public Email(String mail) {
        this.mail = mail;

        RuleGuard.mandatory(this, this::getMail);
        RuleGuard.email(this, this::getMail);
    }

    public String getMail() {
        return mail;
    }

    protected void setMail(String mail) {
        this.mail = mail;
    }
}