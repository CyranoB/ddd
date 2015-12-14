package be.domaindrivendesign.ecole.budget.data.interfaces;

import be.domaindrivendesign.ecole.budget.domain.model.BudgetAnnuel;
import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.data.interfaces.Repository;

public interface BudgetAnnuelRepository extends Repository<BudgetAnnuel> {
    BudgetAnnuel getBudgetAnnuelForAnneeScolaire(AnneeScolaire anneeScolaire);
}
