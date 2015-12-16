package be.domaindrivendesign.ecole.beneficiaire.jpa;


import be.domaindrivendesign.ecole.TestConfiguration;
import be.domaindrivendesign.ecole.beneficiaire.data.interfaces.BeneficiaireRepository;
import be.domaindrivendesign.ecole.beneficiaire.domain.model.Beneficiaire;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.shared.valueobject.Adresse;
import be.domaindrivendesign.shared.valueobject.NumeroEntreprise;
import be.domaindrivendesign.shared.valueobject.NumeroIdentificationRegistreNational;
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
@DatabaseSetup("/datasets/beneficiaire/beneficaires.xml")
public class JpaBeneficiaireRepositoryTest {

    @Autowired
    private BeneficiaireRepository repository;
    @Autowired
    private UnitOfWork unitOfWork;

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testList() {
        List<Beneficiaire> beneficiaires = repository.list();
        Assert.assertEquals(2, beneficiaires.size());
        Optional<Beneficiaire> beneficiaire = beneficiaires.stream().filter(b -> b.getId().equals(UUID.fromString("4ea00e16-4275-443b-9ead-e1c892e926ea"))).findFirst();
        assertTrue(beneficiaire.isPresent());
    }

    @Test
    public void getById() {
        Beneficiaire beneficiaire = repository.getById(UUID.fromString("4ea00e16-4275-443b-9ead-e1c892e926ea"));
        assertNotNull(beneficiaire);
        assertEquals("Zaza Enterprise", beneficiaire.getDenomination());
        assertEquals(new Adresse("Rue de Zaza 0", 1050, "Ixelles"), beneficiaire.getAdresse());
    }

    @Test
    public void getBeneficiaireFromNumeroEntreprise() {
        NumeroEntreprise num = new NumeroEntreprise("1", "122", "133", "144");
        Beneficiaire beneficiaire = repository.getBeneficiaireForNumeroEntreprise(num);
        assertNotNull(beneficiaire);
        assertEquals(UUID.fromString("4ea00e16-5275-443b-9ead-e1c892e926ea"), beneficiaire.getId());
    }

    @Test
    public void getBeneficiaireFromNumeroDeRegistreNational() {
        NumeroIdentificationRegistreNational nirn = new NumeroIdentificationRegistreNational("11", "12", "13", "144", "15");
        Beneficiaire beneficiaire = repository.getBeneficiaireForNumeroIdentificationRegistreNational(nirn);
        assertNotNull(beneficiaire);
        assertEquals(UUID.fromString("4ea00e16-5275-443b-9ead-e1c892e926ea"), beneficiaire.getId());
    }
}
