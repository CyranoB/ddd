package be.domaindrivendesign.ecole.budget.data.interfaces;

import be.domaindrivendesign.ecole.budget.domain.model.BudgetAnnuel;
import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;

public interface BudgetAnnuelRepository {
    BudgetAnnuel getBudgetAnnuelForAnneeScolaire(AnneeScolaire anneeScolaire);
}
