package be.domaindrivendesign.domain.ecole.common.valueobject;

import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;

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
        //AnneeScolaire annee = AnneeScolaire.Create(2014,2015);

        AnneeScolaire annee = new AnneeScolaire(2014, 2015);

        assertNotNull(annee);
        assertEquals(2014, annee.getAnneeDebut());
        assertEquals(2015, annee.getAnneeFin());
    }

    @Test
    public void testInvalidStartDate() throws Exception {
        AnneeScolaire annee = null;
        annee = new AnneeScolaire(1014, 2015);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), annee);
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals("1014", UnitOfWorkRule.getInstance().getViolations().get(0).getValues().get(0));
        assertArrayEquals(Arrays.asList("be.domaindrivendesign.domain.ecole.common.valueobject|AnneeScolaire.getAnneeDebut").toArray(), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray());
    }

}