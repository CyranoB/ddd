package be.domaindrivendesign.ecole.module.beneficiaire.data.interfaces;

import be.domaindrivendesign.ecole.application.dto.DemandeValidationDashBoardDto;
import be.domaindrivendesign.ecole.application.dto.beneficiaire.AgrementDtoList;
import be.domaindrivendesign.ecole.application.dto.beneficiaire.AgrementDtoSearch;
import be.domaindrivendesign.ecole.application.dto.common.AnneeScolaireDto;

import java.util.List;

public interface AgrementRepositoryDto {
    List<AgrementDtoList> listAgrement(AgrementDtoSearch search);

    DemandeValidationDashBoardDto getDemandeValidationDashBoard(AnneeScolaireDto anneeScolaireDto);
}
