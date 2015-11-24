package be.domaindrivendesign.domain.ecole.common.valueobject;

import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by eddie on 13/11/15.
 */
public class AnneeScolaireTest {

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testConstructor() throws Exception {
        AnneeScolaire annee = new AnneeScolaire(2014, 2015);

        assertNotNull(annee);
        assertEquals(2014, annee.getAnneeDebut());
        assertEquals(2015, annee.getAnneeFin());
    }

    @Test
    public void testConstrutor_BadAnneeDebut() {
        AnneeScolaire annee = new AnneeScolaire(1014, 2015);

        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), annee);
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals("1014", UnitOfWorkRule.getInstance().getViolations().get(0).getValues().get(0));
        assertArrayEquals(Collections.singletonList("be.domaindrivendesign.ecole.common.valueobject|AnneeScolaire.getAnneeDebut").toArray(), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray());
    }

    @Test
    public void testConstrutor_BadAnneeFin() {
        AnneeScolaire annee = new AnneeScolaire(2014, 3015);

        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), annee);
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals("3015", UnitOfWorkRule.getInstance().getViolations().get(0).getValues().get(0));
        assertArrayEquals(Collections.singletonList("be.domaindrivendesign.ecole.common.valueobject|AnneeScolaire.getAnneeFin").toArray(), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray());
    }


    @Test
    public void testConstrutor_BadAnneeDebutFin() {
        AnneeScolaire anneeScolaire = new AnneeScolaire(1980, 2115);

        assertNotNull(anneeScolaire);
        assertEquals(2, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(RuleType.Between.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertArrayEquals(new String[] { "1980", "2000", "2100" }, UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray());
        assertEquals(RuleType.Between.typeValue, UnitOfWorkRule.getInstance().getViolations().get(1).getRuleId());
        assertArrayEquals(new String[] { "2115", "2000", "2100" }, UnitOfWorkRule.getInstance().getViolations().get(1).getValues().toArray());
        assertEquals(1980, anneeScolaire.getAnneeDebut());
        assertEquals(2115, anneeScolaire.getAnneeFin());
    }

    @Test
    public void testConstrutor_AnneeDebutSmallerThanAnneFin() {
        AnneeScolaire anneeScolaire = new AnneeScolaire(2016, 2015);

        assertNotNull(anneeScolaire);
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(RuleType.SmallerOrEqualThan.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertArrayEquals(new String[] { "2016", "2015" }, UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray());
        assertEquals(2016, anneeScolaire.getAnneeDebut());
        assertEquals(2015, anneeScolaire.getAnneeFin());
    }

}