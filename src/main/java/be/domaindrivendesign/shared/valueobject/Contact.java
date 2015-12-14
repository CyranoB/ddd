package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.translation.LanguageType;
import be.domaindrivendesign.kernel.common.valueobject.ValueObject;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Contact extends ValueObject {

    @Column(name = "CONTACT_ADRESSE")
    @AttributeOverrides({
            @AttributeOverride(name ="adr", column = @Column(name="CONTACT_ADRESSE_ADR")),
            @AttributeOverride(name = "codePostal", column = @Column(name="CONTACT_ADRESSE_CODEPOSTAL")),
            @AttributeOverride(name = "localite", column = @Column(name="CONTACT_ADRESSE_LOCALITE"))})
    protected Adresse adresse;

    @Column(name = "CONTACT_FAX")
    @AttributeOverride(name = "numero", column = @Column(name = "CONTACT_FAX_NUMERO"))
    protected NumeroTelephone fax;

    @Column(name = "CONTACT_TELEPHONE")
    @AttributeOverride(name = "numero", column = @Column(name = "CONTACT_TELEPHONE_NUMERO"))
    protected NumeroTelephone telephone;
    @Column(name = "CONTACT_EMAIL")
    @AttributeOverride(name = "mail", column = @Column(name = "CONTACT_EMAIL_MAIL"))
    protected Email email;
    @Column(name = "CONTACT_NOMPRENOM")
    protected String nomPrenom;
    @Column(name = "CONTACT_LANGUE")
    protected LanguageType langue;

    protected Contact() {
    }

    public Contact(Adresse adresse, NumeroTelephone fax, NumeroTelephone telephone, Email email, String nomPrenom, LanguageType langue) {
        this.adresse = adresse;
        this.fax = fax;
        this.telephone = telephone;
        this.email = email;
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

    public Email getEmail() {
        return email;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public LanguageType getLangue() {
        return langue;
    }
}
