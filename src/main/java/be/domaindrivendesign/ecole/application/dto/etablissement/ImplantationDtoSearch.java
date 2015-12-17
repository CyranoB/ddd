package be.domaindrivendesign.ecole.application.dto.etablissement;

import be.domaindrivendesign.kernel.module.dto.DtoSearch;

import java.util.UUID;

public class ImplantationDtoSearch extends DtoSearch {

    public UUID etablissementId;
    public UUID id;
    public String numeroReference;
    public String denomination;
    public int codePostal;

    public UUID getEtablissementId() {
        return etablissementId;
    }

    public void setEtablissementId(UUID etablissementId) {
        this.etablissementId = etablissementId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumeroReference() {
        return numeroReference;
    }

    public void setNumeroReference(String numeroReference) {
        this.numeroReference = numeroReference;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }
}
