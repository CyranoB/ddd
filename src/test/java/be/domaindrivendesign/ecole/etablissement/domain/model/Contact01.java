package be.domaindrivendesign.ecole.etablissement.domain.model;

import be.domaindrivendesign.kernel.common.translation.LanguageType;
import be.domaindrivendesign.shared.valueobject.Contact;
import be.domaindrivendesign.shared.valueobject.Email;
import be.domaindrivendesign.shared.valueobject.NumeroTelephone;

/**
 * Created by eddie on 24/11/15.
 */
public class Contact01 extends Contact {
    public Contact01() {
        super(new Adresse01(),
                new NumeroTelephone("+32 2 375 76 50"),
                new NumeroTelephone("+32 2 375 76 51"),
                new Email("Capitaine.America@spw.com"),
                "Captaine America",
                LanguageType.French);

    }
}
