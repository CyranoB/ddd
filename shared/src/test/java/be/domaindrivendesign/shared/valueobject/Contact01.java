package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.translation.LanguageType;

/**
 * Created by eddie on 24/11/15.
 */
public class Contact01 extends Contact {
    public Contact01() {
        super(new Adresse01(),
                new NumeroTelephone01(),
                new NumeroTelephone02(),
                new Email01(),
                "Storm Trooper",
                LanguageType.French);
    }
}
