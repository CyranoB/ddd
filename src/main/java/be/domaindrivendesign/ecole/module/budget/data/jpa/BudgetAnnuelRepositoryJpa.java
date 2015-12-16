package be.domaindrivendesign.ecole.module.budget.data.jpa;

import be.domaindrivendesign.ecole.module.budget.data.interfaces.BudgetAnnuelRepository;
import be.domaindrivendesign.ecole.module.budget.domain.model.BudgetAnnuel;
import be.domaindrivendesign.ecole.module.budget.domain.model.QBudgetAnnuel;
import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import com.querydsl.jpa.impl.JPAQuery;

public class BudgetAnnuelRepositoryJpa extends RepositoryJpa<BudgetAnnuel> implements BudgetAnnuelRepository {

    @Override
    public BudgetAnnuel getBudgetAnnuelForAnneeScolaire(AnneeScolaire anneeScolaire) {
        JPAQuery<BudgetAnnuel> query = new JPAQuery<>(getUnitOfWorkJpa().getEntityManager());
        QBudgetAnnuel budgetsAnnuel = QBudgetAnnuel.budgetAnnuel;
        return query.from(budgetsAnnuel).where(budgetsAnnuel.anneeScolaire.eq(anneeScolaire)).fetchFirst();
    }
}
