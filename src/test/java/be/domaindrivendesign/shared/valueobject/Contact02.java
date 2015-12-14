package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.translation.LanguageType;

public class Contact02 extends Contact {
    public Contact02() {
        super(new Adresse02(),
                new NumeroTelephone01(),
                new NumeroTelephone02(),
                new Email("spiderman@zaza.com"),
                "Spiderman",
                LanguageType.French);
    }
}
