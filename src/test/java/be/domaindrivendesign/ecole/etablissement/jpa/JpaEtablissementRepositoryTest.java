package be.domaindrivendesign.ecole.etablissement.jpa;

import be.domaindrivendesign.ecole.RepositoryTestConfiguration;
import be.domaindrivendesign.ecole.etablissement.data.jpa.JpaEtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.domain.model.Etablissement;
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
@DatabaseSetup("/datasets/etablissement/etablissements_test.xml")
public class JpaEtablissementRepositoryTest {

    @Autowired
    private JpaEtablissementRepository jpaRepository;
    @Autowired
    private UnitOfWork unitOfWork;

    @Test
    public void testList() {
        List<Etablissement> etablissements = jpaRepository.list();
        Assert.assertEquals(2, etablissements.size());
        Etablissement etablissement = etablissements.stream().filter(e -> e.getNumeroReference().equals("446")).findFirst().get();
        assertEquals("171b3eb4-e175-c159-7f6a-08d2892a9523", etablissement.getId().toString());
        assertEquals("446", etablissement.getNumeroReference());
        assertEquals(1, etablissements.get(0).getImplantations().size());
    }

    @Test
    public void getById() {
        Etablissement etablissement = jpaRepository.getById(UUID.fromString("171b3eb4-e175-c159-7f6a-08d2892a9523"));
        assertNotNull(etablissement);

    }

    @Test
    public void testGetForNumeroDeReference() {
        Etablissement etablissement = jpaRepository.getEtablissementForNumeroDeReference("446");
        Assert.assertNotNull(etablissement);
        assertEquals("171b3eb4-e175-c159-7f6a-08d2892a9523", etablissement.getId().toString());
    }
}
