package be.domaindrivendesign.ecole.module.budget.data.interfaces;

import be.domaindrivendesign.ecole.module.budget.domain.model.BudgetAnnuel;
import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.data.interfaces.Repository;

public interface BudgetAnnuelRepository extends Repository<BudgetAnnuel> {
    BudgetAnnuel getBudgetAnnuelForAnneeScolaire(AnneeScolaire anneeScolaire);
}
