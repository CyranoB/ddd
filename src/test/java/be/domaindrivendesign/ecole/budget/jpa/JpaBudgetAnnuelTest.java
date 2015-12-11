package be.domaindrivendesign.ecole.budget.jpa;

import be.domaindrivendesign.ecole.RepositoryTestConfiguration;
import be.domaindrivendesign.ecole.budget.data.jpa.JpaBudgetAnnuelRepository;
import be.domaindrivendesign.ecole.budget.model.BudgetAnnuel;
import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
@EnableJpaRepositories
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/datasets/budgetannuel/budgetannuels.xml")
public class JpaBudgetAnnuelTest {
    @Autowired
    private JpaBudgetAnnuelRepository jpaRepository;
    @Autowired
    private UnitOfWork unitOfWork;

    @Test
    public void testList() {
        List<BudgetAnnuel> budgetAnnuels = jpaRepository.findAll();
        assertEquals(4, budgetAnnuels.size());
    }

    @Test
    public void testGetById() {
        BudgetAnnuel budgetAnnuel = jpaRepository.findById(UUID.fromString("41C919A9-E42C-443F-A6F6-FC0C210D806A"));
        assertNotNull(budgetAnnuel);
    }

    @Test
    public void testGetForAnneeScolaire() {
        BudgetAnnuel budget = jpaRepository.getBudgetAnnuelForAnneeScolaire(new AnneeScolaire(2011, 2012));
        assertEquals(2011, budget.getAnneeScolaire().getAnneeDebut());
        assertEquals(2012, budget.getAnneeScolaire().getAnneeFin());
        assertEquals(101, budget.getMontantAideFruitEtLegumeParEleve().intValue());
        assertEquals(51, budget.getMontantAideLaitParEleve().intValue());
        assertEquals(1001, budget.getFruitEtLegumeNbrEleve());
        assertEquals(501, budget.getLaitNbrEleve());
    }
}