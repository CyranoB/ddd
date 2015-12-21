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

    public static AgrementDtoList Convertir(Agrement agrement, String beneficiaire) {
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
}
