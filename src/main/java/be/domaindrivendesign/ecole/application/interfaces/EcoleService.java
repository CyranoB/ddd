package be.domaindrivendesign.ecole.application.interfaces;

import be.domaindrivendesign.ecole.application.dto.budget.BudgetAnnuelDto;
import be.domaindrivendesign.kernel.application.interfaces.ApplicationService;

import java.util.List;

public interface EcoleService extends ApplicationService {

    //region BudgetAnnuel
    List<BudgetAnnuelDto> listBudgetAnnuel();

    BudgetAnnuelDto creerBudgetAnnuel(BudgetAnnuelDto budgetAnnuelDto);
    //endregion

}
