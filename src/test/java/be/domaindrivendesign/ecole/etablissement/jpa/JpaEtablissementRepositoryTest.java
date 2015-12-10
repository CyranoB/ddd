package be.domaindrivendesign.ecole.etablissement.jpa;

import be.domaindrivendesign.ecole.etablissement.data.jpa.JpaEtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.model.Etablissement;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
@EnableJpaRepositories
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/etablissement/etablissements_1180.xml")
public class JpaEtablissementRepositoryTest {

    @Autowired
    private JpaEtablissementRepository jpaRepository;
    @Autowired
    private UnitOfWork unitOfWork;

    @Test
    public void testList() {
        List<Etablissement> etablissements = jpaRepository.findAll();
        Assert.assertEquals(29, etablissements.size());
        Etablissement etablissement = etablissements.stream().filter(e -> e.getNumeroReference().equals("446")).findFirst().get();
        assertEquals("171B3EB4-E175-C159-7F6A-08D2892A9523", etablissement.getId().toString().toUpperCase());
        assertEquals("446", etablissement.getNumeroReference());
        // TODO: fix Etablissement -> Implantations
        //assertEquals(1, etablissements.get(0).getImplantations().size());
    }

    @Test
    public void testGetForNumeroDeReference() {
        Etablissement etablissement = jpaRepository.getEtablissementForNumeroDeReference("446");
        Assert.assertNotNull(etablissement);
        assertEquals("171B3EB4-E175-C159-7F6A-08D2892A9523", etablissement.getId().toString().toUpperCase());
    }
}
