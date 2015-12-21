package be.domaindrivendesign.shared.dto;

import be.domaindrivendesign.kernel.module.dto.Dto;
import be.domaindrivendesign.shared.valueobject.Adresse;

public class AdresseDto extends Dto {
    public String adr;
    public int codePostal;
    public String localite;

    public static AdresseDto convertir(Adresse adresse) {
        if (adresse == null)
            return null;

        AdresseDto adresseDto = new AdresseDto();
        adresseDto.adr = adresse.getAdr();
        adresseDto.codePostal = adresse.getCodePostal();
        adresseDto.localite = adresse.getLocalite();
        return adresseDto;
    }

    public static Adresse convertir(AdresseDto adresseDto) {
        if (adresseDto == null)
            return null;
        return new Adresse(adresseDto.adr, adresseDto.codePostal, adresseDto.localite);
    }


    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }
}
