package be.domaindrivendesign.ecole.module.etablissement.data.jpa;

import be.domaindrivendesign.ecole.TestDtoConfiguration;
import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDtoList;
import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDtoSearch;
import be.domaindrivendesign.ecole.application.dto.etablissement.ImplantationDtoList;
import be.domaindrivendesign.ecole.application.dto.etablissement.ImplantationDtoSearch;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepositoryDto;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EcoleType;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EnseignementReseauType;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDtoConfiguration.class)
@EnableJpaRepositories
@EnableTransactionManagement
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/datasets/etablissement/etablissements_testdto.xml")
public class EtablissementRepositoryDtoJpaTest {

    @Autowired
    private EtablissementRepositoryDto etablissementRepositoryDto;
    @Autowired
    private UnitOfWorkJpa unitOfWork;

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }


    @Test
    public void testListEtablissementNullDto() {
        List<EtablissementDtoList> etablissements = etablissementRepositoryDto.listEtablissement(null);
        assertEquals(5, etablissements.size());
    }

    @Test
    public void TestListEtablissementSkipTake() {
        EtablissementDtoSearch searchDto = new EtablissementDtoSearch();
        searchDto.skip = 3;
        searchDto.take = 2;
        List<EtablissementDtoList> etablissements = etablissementRepositoryDto.listEtablissement(searchDto);
        assertEquals(2, etablissements.size());
    }

    @Test
    public void testListEtablissementById() {
        EtablissementDtoSearch searchDto = new EtablissementDtoSearch();
        searchDto.id = UUID.fromString("9536d73b-9a4a-cf5c-7f6a-08d2892a9522");
        List<EtablissementDtoList> etablissements = etablissementRepositoryDto.listEtablissement(searchDto);

        assertNotNull(etablissements);
        assertEquals(1, etablissements.size());

        EtablissementDtoList etablissement = etablissements.get(0);
        assertNotNull(etablissement);
        assertEquals(UUID.fromString("9536d73b-9a4a-cf5c-7f6a-08d2892a9522"), etablissement.id);
        assertEquals("446", etablissement.numeroReference);
        assertEquals("Ecole fondamentale des Schtroumpfs", etablissement.denomination);
        assertEquals(1180, etablissement.codePostal);
        assertEquals(EnseignementReseauType.OrganiseParLaCommunauteFrancaise, etablissement.enseignementReseau);
        assertEquals(EcoleType.EtablissementScolaire, etablissement.ecole);
    }

    @Test
    public void testListEtablissementByCodePostal() {
        EtablissementDtoSearch searchDto = new EtablissementDtoSearch();
        searchDto.codePostal = 1180;
        List<EtablissementDtoList> etablissements = etablissementRepositoryDto.listEtablissement(searchDto);

        Assert.assertEquals(3, etablissements.size());
    }

    @Test
    public void testListEtablissementsByNumeroReference() {
        EtablissementDtoSearch search = new EtablissementDtoSearch();
        search.numeroReference = "446";
        List<EtablissementDtoList> etablissements = etablissementRepositoryDto.listEtablissement(search);

        assertEquals(1, etablissements.size());
        EtablissementDtoList e = etablissements.get(0);
        assertEquals(UUID.fromString("9536d73b-9a4a-cf5c-7f6a-08d2892a9522"), e.id);
        assertEquals("446", e.numeroReference);
        assertEquals("Ecole fondamentale des Schtroumpfs", e.denomination);
        assertEquals("Rue Edith Cavell 29", e.adr);
        assertEquals(1180, e.codePostal);
        assertEquals("BRUXELLES", e.localite);
    }

    @Test
    public void testListImplantationById() {
        ImplantationDtoSearch searchDto = new ImplantationDtoSearch();
        searchDto.id = UUID.fromString("00fdbb9a-c157-cea7-7f6a-08d2892a9523");
        List<ImplantationDtoList> implantations = etablissementRepositoryDto.listImplantation(searchDto);

        Assert.assertNotNull(implantations);
        ImplantationDtoList implantation = implantations.get(0);
        Assert.assertNotNull(implantation);
        Assert.assertEquals(UUID.fromString("00fdbb9a-c157-cea7-7f6a-08d2892a9523"), implantation.id);
        Assert.assertEquals("Ecole communale fondamentale de Calevoet", implantation.denomination);
        Assert.assertEquals(1180, implantation.adresseCodePostal);
    }

    @Test
    public void testListByEtablissementId() {
        ImplantationDtoSearch searchDto = new ImplantationDtoSearch();
        searchDto.etablissementId = UUID.fromString("171b3eb4-e175-c159-7f6a-08d2892a9523");
        List<ImplantationDtoList> implantations = etablissementRepositoryDto.listImplantation(searchDto);

        Assert.assertNotNull(implantations);
        Assert.assertEquals(2, implantations.size());
        Assert.assertEquals(1, implantations.stream().filter(i -> i.getId().equals(UUID.fromString("00fdbb9a-c157-cea7-7f6a-08d2892a9523"))).count());
        Assert.assertEquals(2, implantations.stream().filter(i -> i.getAdresseCodePostal() == 1180).count());
    }
}
