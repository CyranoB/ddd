package be.domaindrivendesign.ecole.etablissement.jpa;

import be.domaindrivendesign.ecole.common.type.ClasseType;
import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.etablissement.data.jpa.JpaImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.etablissement.model.ClasseComptage;
import be.domaindrivendesign.ecole.etablissement.model.ImplantationAnneeScolaire;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
@EnableJpaRepositories
public class JpaImplantationAnneeScolaireRepositoryTest {

    @Autowired
    private JpaImplantationAnneeScolaireRepository jpaRepository;
    @Autowired
    private UnitOfWork unitOfWork;

    @Test
    public void insertTest() {

        ClasseComptage classeComptage01 = ClasseComptage.creer(ClasseType.Maternelle, 10);
        ClasseComptage classeComptage02 = ClasseComptage.creer(ClasseType.PremierePrimaire, 14);
        Arrays.asList(classeComptage01, classeComptage02);
        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaire.creer("1", new AnneeScolaire(2014, 2015),
                new ArrayList<>(Arrays.asList(classeComptage01, classeComptage02)));

        jpaRepository.insert(implantationAnneeScolaire);
        unitOfWork.commit();

        List<ImplantationAnneeScolaire> entity01s = jpaRepository.findAll();
        assertTrue(entity01s.size() > 0);
    }

    @Test
    public void testEmpty() {
        List<ImplantationAnneeScolaire> emptyList = jpaRepository.findAll();
        assertEquals(0, emptyList.size());
    }
}
