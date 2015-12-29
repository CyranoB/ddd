package be.domaindrivendesign.ecole.module.etablissement.data.jpa;

import be.domaindrivendesign.ecole.TestConfiguration;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Adresse01;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Contact01;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Implantation;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EcoleType;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EnseignementReseauType;
import be.domaindrivendesign.kernel.application.error.KernelApplicationException;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.After;
import org.junit.Before;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@EnableJpaRepositories
@EnableTransactionManagement
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/datasets/etablissement/etablissements_test.xml")
public class EtablissementRepositoryJpaTest {

    @Autowired
    private EtablissementRepository repository;
    @Autowired
    private UnitOfWorkJpa unitOfWork;

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testList() {
        List<Etablissement> etablissements = repository.list();
        assertEquals(2, etablissements.size());
        Etablissement etablissement = etablissements.stream().filter(e -> e.getNumeroReference().equals("446")).findFirst().get();
        assertEquals("171b3eb4-e175-c159-7f6a-08d2892a9523", etablissement.getId().toString());
        assertEquals("446", etablissement.getNumeroReference());
        assertEquals(1, etablissements.get(0).getImplantations().size());
    }

    @Test
    public void getById() {
        Etablissement etablissement = repository.getById(UUID.fromString("171b3eb4-e175-c159-7f6a-08d2892a9523"));
        assertNotNull(etablissement);

    }

    @Test
    public void testGetForNumeroDeReference() {
        Etablissement etablissement = repository.getEtablissementForNumeroDeReference("446");
        assertNotNull(etablissement);
        assertEquals("171b3eb4-e175-c159-7f6a-08d2892a9523", etablissement.getId().toString());
    }

    @Test(expected = KernelApplicationException.class)
    public void testInsertInvlaidEtablissement() {
        Implantation impl = Implantation.creer("9999", "Test Impl", new Adresse01(),
                Collections.EMPTY_LIST, new Contact01(),
                new PeriodDateHeure(LocalDateTime.MIN, LocalDateTime.MAX));
        Etablissement e = Etablissement.creer("999", "Test à Toto", EnseignementReseauType.OfficielCommunal, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), Arrays.asList(impl));

        repository.insert(e);
        unitOfWork.commit();
    }
}
