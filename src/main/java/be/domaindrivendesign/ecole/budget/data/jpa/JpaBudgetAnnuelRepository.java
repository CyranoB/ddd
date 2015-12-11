package be.domaindrivendesign.ecole.budget.data.jpa;

import be.domaindrivendesign.ecole.budget.data.interfaces.BudgetAnnuelRepository;
import be.domaindrivendesign.ecole.budget.domain.model.BudgetAnnuel;
import be.domaindrivendesign.ecole.budget.domain.model.QBudgetAnnuel;
import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.UUID;

public class JpaBudgetAnnuelRepository extends RepositoryJpa<BudgetAnnuel, UUID> implements BudgetAnnuelRepository {
    @Override
    public BudgetAnnuel getBudgetAnnuelForAnneeScolaire(AnneeScolaire anneeScolaire) {
        JPAQuery<BudgetAnnuel> query = new JPAQuery<>(getJpaUnitOfWork().getEntityManager());
        QBudgetAnnuel budgetsAnnuel = QBudgetAnnuel.budgetAnnuel;
        return query.from(budgetsAnnuel).where(budgetsAnnuel.anneeScolaire.eq(anneeScolaire)).fetchFirst();
    }
}
