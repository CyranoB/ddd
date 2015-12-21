package be.domaindrivendesign.ecole.module.etablissement.data.mock;

import be.domaindrivendesign.ecole.module.common.type.ClasseType;
import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.*;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EcoleType;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EnseignementReseauType;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.NiveauType;
import be.domaindrivendesign.ecole.module.etablissement.service.EcoleDomainService;
import be.domaindrivendesign.ecole.module.etablissement.service.EcoleDomainServiceConfiguration;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class ImplantationAnneeScolaireRepositoryMock {
    public static String INPLANTATION_REF = "implantation reference 1";

    public static ImplantationAnneeScolaire getImplantationAnneeScolaire01(int nbrElevesClasse01, int nbrElevesClasse02) {
        return ImplantationAnneeScolaire.creer(
                INPLANTATION_REF,
                new AnneeScolaire(2014, 2015),
                new LinkedHashSet<>(Arrays.asList(getClasseComptage01(nbrElevesClasse01), getClasseComptage02(nbrElevesClasse02))));
    }

    public static ClasseComptage getClasseComptage01(int nbrEleves) {
        return ClasseComptage.creer(ClasseType.Maternelle, nbrEleves);
    }

    public static ClasseComptage getClasseComptage02(int nbrEleves) {
        return ClasseComptage.creer(ClasseType.Primaire, nbrEleves);
    }


    @RunWith(SpringJUnit4ClassRunner.class)
    @ComponentScan(basePackages = {})
    @SpringApplicationConfiguration(classes = {EcoleDomainServiceConfiguration.class})
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
            DirtiesContextTestExecutionListener.class,
            TransactionalTestExecutionListener.class,
            DbUnitTestExecutionListener.class})
    public static class EcoleDomainServiceTest {

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
            Etablissement etablissement = Etablissement.creer("reference nouveau", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                    EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Arrays.asList(implantation01, implantation02)));

            // Import
            ecoleDomainService.importerEtablissement(etablissement, LocalDateTime.now());

            // Valider
            Etablissement e = etablissementRepository.getEtablissementForNumeroDeReference("reference nouveau");
            Assert.assertEquals(etablissement.getId(), e.getId());
            Assert.assertEquals(etablissement, e);

        }

        @Test
        public void testImporterEtablissementExistant() {
            // Etbalissement original.
            Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse02(), Arrays.asList(NiveauType.Prescolaire5Jour), new Contact01(), PeriodDateHeure.EMPTY);
            Etablissement etablissement = Etablissement.creer("reference modif", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                    EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Arrays.asList(implantation01)));
            ecoleDomainService.importerEtablissement(etablissement, LocalDateTime.now());

            // Etablissement modifié
            Implantation implantation01M = Implantation.creer("reference I01", "denomination m01", new Adresse02(), Arrays.asList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
            Implantation implantation02M = Implantation.creer("reference I02", "denomination I02", new Adresse02(), Arrays.asList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
            Etablissement etablissementm = Etablissement.creer("reference modif", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                    EcoleType.EtablissementScolaire, new Contact02(), new ArrayList<>(Arrays.asList(implantation01M, implantation02M)));

            // Import
            ecoleDomainService.importerEtablissement(etablissementm, LocalDateTime.now());

            // Valider
            Etablissement e = etablissementRepository.getEtablissementForNumeroDeReference("reference modif");
            Assert.assertEquals(new Contact02(), e.getContact());
            Assert.assertEquals(2, e.getImplantations().size());
        }

        @Test
        public void testImporterImplantationAnneeScolaireNouveau() {
            ImplantationAnneeScolaire implantationAnneeScolaire = getImplantationAnneeScolaire01(100, 200);

            // Import
            ecoleDomainService.importerImplantationAnneeScolaire(implantationAnneeScolaire, LocalDateTime.now());

            // Valider
            ImplantationAnneeScolaire i = implantationAnneeScolaireRepository.getImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference(new AnneeScolaire(2014, 2015), INPLANTATION_REF);
            Assert.assertEquals(implantationAnneeScolaire, i);

        }

        @Test
        public void testImporterImplantationAnneeScolaireExistant() {
            ImplantationAnneeScolaire implantationAnneeScolaire = getImplantationAnneeScolaire01(100, 200);
            ecoleDomainService.importerImplantationAnneeScolaire(implantationAnneeScolaire, LocalDateTime.now());

            // Change
            ImplantationAnneeScolaire implantationAnneeScolaireM = getImplantationAnneeScolaire01(200, 300);
            ecoleDomainService.importerImplantationAnneeScolaire(implantationAnneeScolaireM, LocalDateTime.now());

            // Valider
            ImplantationAnneeScolaire i = implantationAnneeScolaireRepository.getImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference(new AnneeScolaire(2014, 2015), INPLANTATION_REF);
            Assert.assertEquals(5, i.getClasseComptages().size());
        }


    }
}
