package be.domaindrivendesign.ecole.beneficiaire.jpa;


import be.domaindrivendesign.ecole.TestConfiguration;
import be.domaindrivendesign.ecole.beneficiaire.data.interfaces.AgrementRepository;
import be.domaindrivendesign.ecole.beneficiaire.domain.model.Agrement;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.After;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@EnableJpaRepositories
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/datasets/beneficiaire/agrements.xml")
public class AgrementRepositoryJpaTest {

    @Autowired
    private AgrementRepository repository;
    @Autowired
    private UnitOfWorkJpa unitOfWork;

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testList() {
        List<Agrement> agrements = repository.list();
        Assert.assertEquals(3, agrements.size());
        Optional<Agrement> agrement = agrements.stream().filter(b -> b.getId().equals(UUID.fromString("3f6337b6-25d9-415c-bfa6-d6697051cfa0"))).findFirst();
        assertTrue(agrement.isPresent());
    }

    @Test
    public void getById() {
        Agrement agrement = repository.getById(UUID.fromString("3f6337b6-15d9-415c-bfa6-d6697051cfa0"));
        assertNotNull(agrement);
        assertEquals("1111", agrement.getNumeroDgarne());
    }
}
