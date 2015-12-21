package be.domaindrivendesign.ecole.module.laitecole.jpa;

import be.domaindrivendesign.ecole.TestDtoConfiguration;
import be.domaindrivendesign.ecole.module.laitecole.data.interfaces.EtablissementParticipantRepositoryDto;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.After;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDtoConfiguration.class)
@EnableJpaRepositories
@EnableTransactionManagement
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
//TODO dataset
// @DatabaseSetup("/datasets/laitecole/etablissements_test.xml")
public class EtablissementParticipantDtoRepositoryJpaTest {

    @Autowired
    private EtablissementParticipantRepositoryDto etablissementParticipantRepositoryDto;
    @Autowired
    private UnitOfWorkJpa unitOfWork;

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    //TODO Faire tests
    @Test
    public void testListNumeroDGARNEFor() {
    }

}
