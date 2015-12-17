package be.domaindrivendesign.ecole.application.interfaces;

import be.domaindrivendesign.ecole.application.dto.budget.BudgetAnnuelDto;
import be.domaindrivendesign.ecole.application.dto.etablissement.*;
import be.domaindrivendesign.kernel.application.interfaces.ApplicationService;
import be.domaindrivendesign.shared.dto.ContactDto;

import java.util.List;
import java.util.UUID;

public interface EcoleService extends ApplicationService {

    //region BudgetAnnuel
    List<BudgetAnnuelDto> listBudgetAnnuel();

    BudgetAnnuelDto creerBudgetAnnuel(BudgetAnnuelDto budgetAnnuelDto);
    //endregion


    //region Etablissement
    List<EtablissementDtoList> ListEtablissement(EtablissementDtoSearch search);

    EtablissementDto GetEtablissement(UUID etablissementId);

    void ModifierEtablissementContact(UUID etablissementId, ContactDto contactDto);

    List<ImplantationDtoList> ListImplantation(ImplantationDtoSearch search);

    ImplantationDto GetImplantation(UUID etablissementId, UUID implantationId);

    void ModifierImplantationContact(UUID etablissementId, UUID implementationId, ContactDto contactDto);

    //TODO void ImporterFichierCommunauteFrancaise(ImportFichierDto importFichierDto, StreamReader reader);

    //endregion
}
