package be.domaindrivendesign.shared.dto;


import be.domaindrivendesign.kernel.common.translation.LanguageType;
import be.domaindrivendesign.kernel.module.dto.Dto;
import be.domaindrivendesign.shared.valueobject.Contact;
import be.domaindrivendesign.shared.valueobject.Email;
import be.domaindrivendesign.shared.valueobject.NumeroTelephone;

public class ContactDto extends Dto {
    public AdresseDto adresse;
    public String fax;
    public String telephone;
    public String email;
    public String nomPrenom;
    public LanguageType langue;


    public static ContactDto convertir(Contact contact) {
        if (contact == null)
            return null;

        ContactDto contactDto = new ContactDto();
        contactDto.adresse = AdresseDto.convertir(contact.getAdresse());
        contactDto.email = contact.getEmail() == null ? null : contact.getEmail().getMail();
        contactDto.langue = contact.getLangue();
        contactDto.nomPrenom = contact.getNomPrenom();
        contactDto.telephone = contact.getTelephone() == null ? null : contact.getTelephone().getNumero();
        return contactDto;
    }

    public static Contact convertir(ContactDto contactDto) {
        if (contactDto == null)
            return null;

        return new Contact(AdresseDto.convertir(contactDto.adresse),
                new NumeroTelephone(contactDto.fax),
                new NumeroTelephone(contactDto.telephone),
                new Email(contactDto.email),
                contactDto.nomPrenom,
                contactDto.langue
        );
    }


    public AdresseDto getAdresse() {
        return adresse;
    }

    public void setAdresse(AdresseDto adresse) {
        this.adresse = adresse;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public LanguageType getLangue() {
        return langue;
    }

    public void setLangue(LanguageType langue) {
        this.langue = langue;
    }
}
