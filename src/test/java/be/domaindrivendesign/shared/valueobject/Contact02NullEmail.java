package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.translation.LanguageType;

public class Contact02NullEmail extends Contact {
    public Contact02NullEmail() {
        super(new Adresse02(),
                new NumeroTelephone01(),
                new NumeroTelephone02(),
                null,
                "Spiderman",
                LanguageType.French);
    }
}
