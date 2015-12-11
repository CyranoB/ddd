package be.domaindrivendesign.ecole.etablissement.domain.model;

import be.domaindrivendesign.kernel.common.translation.LanguageType;
import be.domaindrivendesign.shared.valueobject.Contact;
import be.domaindrivendesign.shared.valueobject.Email;
import be.domaindrivendesign.shared.valueobject.NumeroTelephone;

/**
 * Created by eddie on 24/11/15.
 */
public class Contact02 extends Contact {
    public Contact02() {
        super(new Adresse02(),
                new NumeroTelephone("+32 2 375 76 60"),
                new NumeroTelephone("+32 2 375 76 61"),
                new Email("Spiderman@spw.com"),
                "Spriderman",
                LanguageType.French);
    }
}
