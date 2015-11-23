package be.domaindrivendesign.ecole.common.valueobject;

import be.domaindrivendesign.kernel.common.translation.LanguageType;
import be.domaindrivendesign.kernel.common.valueobject.ValueObject;

/**
 * Created by eddie on 23/11/15.
 */
public class Contact extends ValueObject {
    private Adresse adresse;
    private NumeroTelephone fax;
    private NumeroTelephone telephone;
    private EMail eMail;
    private String nomPrenom;
    private LanguageType langue;

    private Contact() {
    }

    public Contact(Adresse adresse, NumeroTelephone fax, NumeroTelephone telephone, EMail eMail, String nomPrenom, LanguageType langue) {
        this.adresse = adresse;
        this.fax = fax;
        this.telephone = telephone;
        this.eMail = eMail;
        this.nomPrenom = nomPrenom;
        this.langue = langue;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public NumeroTelephone getFax() {
        return fax;
    }

    public NumeroTelephone getTelephone() {
        return telephone;
    }

    public EMail geteMail() {
        return eMail;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public LanguageType getLangue() {
        return langue;
    }
}
