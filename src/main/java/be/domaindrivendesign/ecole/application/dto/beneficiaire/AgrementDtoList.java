package be.domaindrivendesign.ecole.application.dto.beneficiaire;

import be.domaindrivendesign.ecole.application.dto.common.AnneeScolaireDto;
import be.domaindrivendesign.ecole.module.beneficiaire.domain.model.Agrement;
import be.domaindrivendesign.kernel.module.dto.DtoList;

import java.time.LocalDateTime;
import java.util.UUID;

public class AgrementDtoList extends DtoList {

    public UUID id;
    public LocalDateTime dateDepot;
    public LocalDateTime dateDemande;
    public int numeroDossier;
    public AnneeScolaireDto premiereAnneeScolaire;
    public AnneeScolaireDto derniereAnneeScolaire;
    public ValidationDemandeDto validationDemande;
    public String numeroDgarne;
    public String denominationBeneficiare;
    public UUID beneficiaireId;

    public AgrementDtoList() {

    }

    public static AgrementDtoList convertir(Agrement agrement, String beneficiaire) {
        if (agrement != null)
            return null;

        AgrementDtoList adl = new AgrementDtoList();

        adl.id = agrement.getId();
        adl.beneficiaireId = agrement.getBeneficiaireId();
        adl.dateDemande = agrement.getDateDemande();
        adl.dateDepot = agrement.getDateDepot();
        adl.denominationBeneficiare = beneficiaire;
        adl.derniereAnneeScolaire = AnneeScolaireDto.convertir(agrement.getDerniereAnneeScolaire());
        adl.numeroDgarne = agrement.getNumeroDgarne();
        adl.numeroDossier = agrement.getNumeroDossier();
        adl.premiereAnneeScolaire = AnneeScolaireDto.convertir(agrement.getPremiereAnneeScolaire());
        adl.validationDemande = ValidationDemandeDto.convertir(agrement.getValidationDemande());
        return adl;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(LocalDateTime dateDepot) {
        this.dateDepot = dateDepot;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    public int getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(int numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public AnneeScolaireDto getPremiereAnneeScolaire() {
        return premiereAnneeScolaire;
    }

    public void setPremiereAnneeScolaire(AnneeScolaireDto premiereAnneeScolaire) {
        this.premiereAnneeScolaire = premiereAnneeScolaire;
    }

    public AnneeScolaireDto getDerniereAnneeScolaire() {
        return derniereAnneeScolaire;
    }

    public void setDerniereAnneeScolaire(AnneeScolaireDto derniereAnneeScolaire) {
        this.derniereAnneeScolaire = derniereAnneeScolaire;
    }

    public ValidationDemandeDto getValidationDemande() {
        return validationDemande;
    }

    public void setValidationDemande(ValidationDemandeDto validationDemande) {
        this.validationDemande = validationDemande;
    }

    public String getNumeroDgarne() {
        return numeroDgarne;
    }

    public void setNumeroDgarne(String numeroDgarne) {
        this.numeroDgarne = numeroDgarne;
    }

    public String getDenominationBeneficiare() {
        return denominationBeneficiare;
    }

    public void setDenominationBeneficiare(String denominationBeneficiare) {
        this.denominationBeneficiare = denominationBeneficiare;
    }

    public UUID getBeneficiaireId() {
        return beneficiaireId;
    }

    public void setBeneficiaireId(UUID beneficiaireId) {
        this.beneficiaireId = beneficiaireId;
    }
}
