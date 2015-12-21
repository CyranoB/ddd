package be.domaindrivendesign.ecole.application;

import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDto;
import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDtoList;
import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDtoSearch;
import be.domaindrivendesign.ecole.application.interfaces.EcoleService;
import be.domaindrivendesign.ecole.module.beneficiaire.data.interfaces.AgrementRepository;
import be.domaindrivendesign.ecole.module.beneficiaire.data.mock.AgrementRepositoryMock;
import be.domaindrivendesign.ecole.module.beneficiaire.domain.model.Agrement;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.module.etablissement.data.mock.EtablissementRepositoryMock;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.module.laitecole.data.interfaces.EtablissementParticipantRepository;
import be.domaindrivendesign.ecole.module.laitecole.domain.model.EtablissementParticipant;
import be.domaindrivendesign.ecole.module.laitecole.mock.EtablissementParticipantRepositoryMock;
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

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EcoleServiceConfiguration.class)
@EnableJpaRepositories
@EnableTransactionManagement
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})

public class EcoleServiceImplTest {

    @Autowired
    EtablissementRepository etablissementRepositoryMock;

    @Autowired
    EtablissementParticipantRepository etablissementParticipantRepositoryMock;

    @Autowired
    AgrementRepository agrementRepositoryMock;

    @Autowired
    EcoleService ecoleService;

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testListEtablissement() {
        // Contexte
        Etablissement etablissement = EtablissementRepositoryMock.getEtablissement01();
        etablissementRepositoryMock.insert(etablissement);

        // List
        List<EtablissementDtoList> etablissementListDtOs = ecoleService.listEtablissement(new EtablissementDtoSearch());

        // Valider
        // Assert.assertEquals(1, etablissementListDtOs.size());
        //Assert.assertEquals(etablissement.getId(), etablissementListDtOs.get(0).getId());
    }

    @Test
    public void testGetEtablissement() {

        // Contexte
        Etablissement etablissement = EtablissementRepositoryMock.getEtablissement01();
        etablissementRepositoryMock.insert(etablissement);

        EtablissementParticipant etablissementParticipant = EtablissementParticipantRepositoryMock.getEtablissementParticipant01(EtablissementRepositoryMock.EtablissementNumeroRefence01, "NumeroDgarne01");
        etablissementParticipantRepositoryMock.insert(etablissementParticipant);

        Agrement agrement = AgrementRepositoryMock.getAgrement01(UUID.randomUUID(), "NumeroDgarne01");
        agrementRepositoryMock.insert(agrement);

        // List
        EtablissementDto etablissementDto = ecoleService.getEtablissement(etablissement.getId());

        // TODO Valider
        //Assert.assertEquals(etablissementDto.id, etablissement.getId());
        //Assert.assertEquals(etablissementDto.implantations.size(), 1);
        //Assert.assertEquals(etablissementDto.implantations.get(0).id, etablissement.getImplantations().get(0).getId());
        //Assert.assertEquals(etablissementDto.agrements.size(), 1);
        //Assert.assertEquals(etablissementDto.agrements.get(0).id, agrement.getId());
    }
}
