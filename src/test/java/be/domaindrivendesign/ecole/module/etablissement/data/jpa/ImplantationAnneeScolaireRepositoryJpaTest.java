package be.domaindrivendesign.ecole.module.etablissement.data.jpa;

import be.domaindrivendesign.ecole.TestConfiguration;
import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.ImplantationAnneeScolaire;
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
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@EnableJpaRepositories
@DatabaseSetup("/datasets/etablissement/implantation_annee_scolaires.xml")
public class ImplantationAnneeScolaireRepositoryJpaTest {

    @Autowired
    private ImplantationAnneeScolaireRepository repository;
    @Autowired
    private UnitOfWorkJpa unitOfWork;

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testList() {
        List<ImplantationAnneeScolaire> anneeScolaires = repository.list();
        List<?> ids = anneeScolaires.stream().map(ImplantationAnneeScolaire::getId).collect(Collectors.toList());

        Assert.assertEquals(3, anneeScolaires.size());
        Assert.assertTrue(ids.contains(UUID.fromString("889BC410-D4D9-4DBD-A0E0-D714EC40967C")));
    }

    @Test
    public void testGetById() {
        ImplantationAnneeScolaire result = repository.getById(UUID.fromString("CD64664B-CE45-4355-973D-0DAF2369D2DC"));

        Assert.assertNotNull(result);
        assertEquals("CD64664B-CE45-4355-973D-0DAF2369D2DC", result.getId().toString().toUpperCase());
        assertEquals(new AnneeScolaire(2013, 2014), result.getAnneeScolaire());
        assertEquals("101", result.getImplantationNumeroReference());
    }

    @Test
    public void testForImplantationNumeroReference() {
        List<ImplantationAnneeScolaire> result = repository.listImplantationAnneeScolaireForImplantationNumeroReference("101");

        Assert.assertEquals(3, result.size());
        List<AnneeScolaire> annees = result.stream().map(ImplantationAnneeScolaire::getAnneeScolaire).collect(Collectors.toList());

        Assert.assertNotNull(annees.contains(new AnneeScolaire(2013, 2014)));
        Assert.assertNotNull(annees.contains(new AnneeScolaire(2012, 2013)));
        Assert.assertNotNull(annees.contains(new AnneeScolaire(2011, 2012)));
    }

    @Test
    public void testImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference() {
        ImplantationAnneeScolaire result =
                repository.getImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference(
                        new AnneeScolaire(2011, 2012), "101");

        Assert.assertNotNull(result);
        assertEquals("119735F7-224E-4FF2-9F41-FD81643E6518", result.getId().toString().toUpperCase());
        assertEquals(new AnneeScolaire(2011, 2012), result.getAnneeScolaire());
        assertEquals("101", result.getImplantationNumeroReference());
    }
}