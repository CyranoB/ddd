package be.domaindrivendesign.ecole.etablissement.service;


import be.domaindrivendesign.ecole.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.etablissement.domain.model.*;
import be.domaindrivendesign.ecole.etablissement.domain.type.EcoleType;
import be.domaindrivendesign.ecole.etablissement.domain.type.EnseignementReseauType;
import be.domaindrivendesign.ecole.etablissement.domain.type.NiveauType;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = {})
@SpringApplicationConfiguration(classes = {EcoleDomainServiceConfiguration.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class EcoleDomainServiceTest {

    @Autowired(required = true)
    private EcoleDomainService ecoleDomainService;
    @Autowired
    private EtablissementRepository etablissementRepository;
    @Autowired
    private ImplantationAnneeScolaireRepository implantationAnneeScolaireRepository;

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testImporterEtablissementNouveau() {

        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse02(), Arrays.asList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
        Implantation implantation02 = Implantation.creer("reference I02", "denomination I02", new Adresse02(), Arrays.asList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer("reference", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new LinkedHashSet<>(Arrays.asList(implantation01, implantation02)));
        LocalDateTime dateApplication = LocalDateTime.now();

        // Import
        ecoleDomainService.importerEtablissement(etablissement, dateApplication);

        // Valider
        List<Etablissement> etablissements = etablissementRepository.list();
        Assert.assertEquals(1, etablissements.size());
        Assert.assertEquals(etablissement, etablissements.get(0));

    }

//    @Test
//    public void testImporterEtablissementExistant()
//    {
//        // Contexte
//        EtablissementRepositoryMock etablissementRepositoryMock = new EtablissementRepositoryMock();
//        ImplantationAnneeScolaireRepositoryMock implantationAnneeScolaireRepositoryMock = new ImplantationAnneeScolaireRepositoryMock();
//        EcoleDomainService ecoleDomainService = new EcoleDomainService(etablissementRepositoryMock, implantationAnneeScolaireRepositoryMock);
//
//        // Etbalissement original.
//        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse02(), new List<NiveauType>() { NiveauType.Prescolaire5Jour }, new Contact01(), PeriodDateHeure.Empty);
//        Etablissement etablissement = Etablissement.creer("reference", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
//                EcoleType.EtablissementScolaire, new Contact01(), new List<Implantation>() { implantation01 });
//        etablissementRepositoryMock.Insert(etablissement);
//
//        // Etablissement modifié
//        Implantation implantation01M = Implantation.creer("reference I01", "denomination m01", new Adresse02(), new List<NiveauType>() { NiveauType.Prescolaire5Jour }, new Contact02(), PeriodDateHeure.Empty);
//        Implantation implantation02M = Implantation.creer("reference I02", "denomination I02", new Adresse02(), new List<NiveauType>() { NiveauType.Prescolaire5Jour }, new Contact02(), PeriodDateHeure.Empty);
//        Etablissement etablissementm = Etablissement.creer("reference", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
//                EcoleType.EtablissementScolaire, new Contact02(), new List<Implantation>() { implantation01M, implantation02M });
//        etablissementRepositoryMock.Insert(etablissement);
//
//        LocalDateTime dateApplication = LocalDateTime.now();
//
//        // Import
//        ecoleDomainService.importerEtablissement(etablissementm, dateApplication);
//
//        // Valider
//        Assert.AreEqual(etablissementRepositoryMock.Objects[0].Contact, new Contact02());
//        Assert.AreEqual(etablissementRepositoryMock.Objects[0].Implantations.Count, 2);
//    }
//
//    @Test
//    public void TestImporterImplantationAnneeScolaireNouveau()
//    {
//        // Contexte
//        EtablissementRepositoryMock etablissementRepositoryMock = new EtablissementRepositoryMock();
//        ImplantationAnneeScolaireRepositoryMock implantationAnneeScolaireRepositoryMock = new ImplantationAnneeScolaireRepositoryMock();
//        EcoleDomainService ecoleDomainService = new EcoleDomainService(etablissementRepositoryMock, implantationAnneeScolaireRepositoryMock);
//        LocalDateTime dateApplication = LocalDateTime.now();
//        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaireRepositoryMock.GetImplantationAnneeScolaire01();
//
//        // Import
//        ecoleDomainService.ImporterImplantationAnneeScolaire(implantationAnneeScolaire, dateApplication);
//
//        // Valider
//        Assert.AreEqual(implantationAnneeScolaireRepositoryMock.Objects[0], implantationAnneeScolaire);
//
//    }
//
//    @Test
//    public void TestImporterImplantationAnneeScolaireExistant()
//    {
//        // Contexte
//        EtablissementRepositoryMock etablissementRepositoryMock = new EtablissementRepositoryMock();
//        ImplantationAnneeScolaireRepositoryMock implantationAnneeScolaireRepositoryMock = new ImplantationAnneeScolaireRepositoryMock();
//        EcoleDomainService ecoleDomainService = new EcoleDomainService(etablissementRepositoryMock, implantationAnneeScolaireRepositoryMock);
//        LocalDateTime dateApplication = LocalDateTime.now();
//        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaireRepositoryMock.GetImplantationAnneeScolaire01(100, 200);
//        implantationAnneeScolaireRepositoryMock.Insert(implantationAnneeScolaire);
//
//        // Import
//        ImplantationAnneeScolaire implantationAnneeScolaireM = ImplantationAnneeScolaireRepositoryMock.GetImplantationAnneeScolaire01(200, 300);
//        ecoleDomainService.ImporterImplantationAnneeScolaire(implantationAnneeScolaireM, dateApplication);
//
//        // Valider
//        Assert.AreEqual(implantationAnneeScolaireRepositoryMock.Objects[0].ClasseComptages.Count, 5);
//    }


}
