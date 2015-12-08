package be.domaindrivendesign.ecole.etablissement.jpa;

import be.domaindrivendesign.ecole.etablissement.data.jpa.JpaEtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.model.Adresse01;
import be.domaindrivendesign.ecole.etablissement.model.Contact01;
import be.domaindrivendesign.ecole.etablissement.model.Etablissement;
import be.domaindrivendesign.ecole.etablissement.model.Implantation;
import be.domaindrivendesign.ecole.etablissement.type.EcoleType;
import be.domaindrivendesign.ecole.etablissement.type.EnseignementReseauType;
import be.domaindrivendesign.ecole.etablissement.type.NiveauType;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
@EnableJpaRepositories
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class JpaEtablissementRepositoryTest {

    @Autowired
    private JpaEtablissementRepository jpaRepository;
    @Autowired
    private UnitOfWork unitOfWork;

    @Test
    @DatabaseSetup("/etablissements_smalset.xml")
    public void testList() {
        List<Etablissement> entity01s = jpaRepository.findAll();
        assertTrue(entity01s.size() > 0);
    }


    @Test
    public void insertTest() {
        ArrayList<Implantation> implantations = new ArrayList<>();
        Implantation implantation01A = Implantation.creer("reference a", "01 a", new Adresse01(), Collections.singletonList(NiveauType.Maternelle), new Contact01(), PeriodDateHeure.EMPTY);
        implantations.add(implantation01A);
        Etablissement etablissement = Etablissement.creer("1", "Test", EnseignementReseauType.LibreSubventionneCf, new Adresse01(), EcoleType.EtablissementScolaire, new Contact01(), implantations);
        jpaRepository.insert(etablissement);
        unitOfWork.commit();

        List<Etablissement> entity01s = jpaRepository.findAll();
        assertTrue(entity01s.size() > 0);
    }

    @Test
    public void testEmpty() {
        List<Etablissement> emptyList = jpaRepository.findAll();
        assertEquals(0, emptyList.size());
    }
}
