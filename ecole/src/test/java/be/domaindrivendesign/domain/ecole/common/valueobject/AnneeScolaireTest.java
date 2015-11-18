package be.domaindrivendesign.domain.ecole.common.valueobject;

import be.domaindrivendesign.kernel.rule.error.RuleException;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by eddie on 13/11/15.
 */
public class AnneeScolaireTest {
    @Test
    public void testConstructor() throws Exception {
        AnneeScolaire annee = new AnneeScolaire(2014,2015);

        assertNotNull(annee);
        assertEquals(2014, annee.getAnneeDebut());
        assertEquals(2015, annee.getAnneeFin());
    }

    @Test
    public void testInvalidStartDate() throws Exception {
        AnneeScolaire annee = null;
        try {
            annee = new AnneeScolaire(1014, 2015);
            //fail();
        } catch (RuleException e) {
            assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), annee);
        }

    }

}