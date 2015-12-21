package be.domaindrivendesign.ecole.module.beneficiaire.data.mock;

import be.domaindrivendesign.ecole.application.dto.DemandeValidationDashBoardDto;
import be.domaindrivendesign.ecole.application.dto.beneficiaire.AgrementDtoList;
import be.domaindrivendesign.ecole.application.dto.beneficiaire.AgrementDtoSearch;
import be.domaindrivendesign.ecole.application.dto.common.AnneeScolaireDto;
import be.domaindrivendesign.ecole.module.beneficiaire.data.interfaces.AgrementRepository;
import be.domaindrivendesign.ecole.module.beneficiaire.data.interfaces.AgrementRepositoryDto;
import be.domaindrivendesign.ecole.module.beneficiaire.domain.model.Agrement;
import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.data.mock.RepositoryMock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AgrementRepositoryMock extends RepositoryMock<Agrement> implements AgrementRepository, AgrementRepositoryDto {

    public static Agrement getAgrement01(UUID beneficiaireId, String numeroDgarne) {
        return Agrement.creer(numeroDgarne, LocalDateTime.now(), LocalDateTime.now(), 1, new AnneeScolaire(2014, 2015), beneficiaireId);
    }

    public List<AgrementDtoList> listAgrement(AgrementDtoSearch search) {
        return mocked.stream().map((a) -> AgrementDtoList.convertir(a, "Beneficiaire 1")).collect(Collectors.toList());

    }

    public DemandeValidationDashBoardDto getDemandeValidationDashBoard(AnneeScolaireDto anneeScolaireDto) {
        DemandeValidationDashBoardDto d = new DemandeValidationDashBoardDto();
        d.accepterNbr = 1;
        d.enConstitutionNbr = 2;
        d.enValidationNbr = 3;
        d.refuserNbr = 4;
        return d;

    }

    @Override
    public List<Agrement> list() {
        return null;
    }
}
