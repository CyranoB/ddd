package be.domaindrivendesign.ecole.application.model;


import be.domaindrivendesign.ecole.application.dto.budget.BudgetAnnuelDto;
import be.domaindrivendesign.ecole.application.dto.etablissement.*;
import be.domaindrivendesign.ecole.application.interfaces.EcoleService;
import be.domaindrivendesign.ecole.module.budget.data.interfaces.BudgetAnnuelRepository;
import be.domaindrivendesign.ecole.module.budget.domain.model.BudgetAnnuel;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepositoryDto;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.module.etablissement.service.EcoleDomainService;
import be.domaindrivendesign.ecole.module.laitecole.data.interfaces.EtablissementParticipantRepositoryDto;
import be.domaindrivendesign.kernel.application.interfaces.ApplicationServiceImpl;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.shared.dto.ContactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EcoleServiceImpl extends ApplicationServiceImpl implements EcoleService {

    @Autowired
    private BudgetAnnuelRepository budgetAnnuelRepository;
    @Autowired(required = true)
    private EcoleDomainService ecoleDomainService;
    @Autowired
    private EtablissementRepository etablissementRepository;
    @Autowired
    private EtablissementRepositoryDto etablissementRepositoryDto;
    @Autowired
    private ImplantationAnneeScolaireRepository implantationAnneeScolaireRepository;

    @Autowired
    private EtablissementParticipantRepositoryDto etablissementParticipantRepositoryDto;


    @Override
    public List<BudgetAnnuelDto> listBudgetAnnuel() {
        List<BudgetAnnuel> budgetAnnuels = budgetAnnuelRepository.list();
        return budgetAnnuels.stream().map(BudgetAnnuelDto.aggregateToDto).collect(Collectors.toList());
    }

    @Override
    public BudgetAnnuelDto creerBudgetAnnuel(BudgetAnnuelDto budgetAnnuelDto) {
        // Double
        BudgetAnnuel budgetAnnuel = BudgetAnnuelDto.convertir(budgetAnnuelDto);
        UnitOfWorkRule.getInstance().raiseExceptionInCaseOfError();
        //TODO Laurent RuleGuard
        // RuleGuard.mandatoryClass<BudgetAnnuel>(budgetAnnuel, RuleSeverityType.BlockingError);

        BudgetAnnuel budgetAnnuelExistant = budgetAnnuelRepository.getBudgetAnnuelForAnneeScolaire(budgetAnnuel.getAnneeScolaire());
        //TODO TODO Laurent
        // RuleGuard.unique(budgetAnnuel, budgetAnnuel::getAnneeScolaire, budgetAnnuelExistant, budgetAnnuel.getAnneeScolaire().toString(), RuleSeverityType.BlockingError);

        // Convertir
        UnitOfWorkRule.getInstance().raiseExceptionInCaseOfError();
        budgetAnnuelRepository.insert(budgetAnnuel);

        return BudgetAnnuelDto.convertir(budgetAnnuel);
    }

    @Override
    public List<EtablissementDtoList> listEtablissement(EtablissementDtoSearch search) {
        return etablissementRepositoryDto.listEtablissement(search);
    }

    @Override
    public EtablissementDto getEtablissement(UUID etablissementId) {
        Etablissement etablissement = etablissementRepository.getById(etablissementId);
        if (etablissement == null)
            return null;

        //TODO etablissementParticipantRepositoryDto
        List<String> numeroDgarnEs = etablissementParticipantRepositoryDto.listNumeroDGARNEFor(etablissementId);
        //TODO agrementRepositoryDto
        // List<AgrementDtoList> agrementDtoLists = _agrementRepositoryDto.ListAgrement(new AgrementDtoSearch() { NumeroDgarnes = numeroDgarnEs });

        return null;
    }

    @Override
    public void modifierEtablissementContact(UUID etablissementId, ContactDto contactDto) {

    }

    @Override
    public List<ImplantationDtoList> listImplantation(ImplantationDtoSearch search) {
        return null;
    }

    @Override
    public ImplantationDto getImplantation(UUID etablissementId, UUID implantationId) {
        return null;
    }

    @Override
    public void modifierImplantationContact(UUID etablissementId, UUID implementationId, ContactDto contactDto) {

    }
}
