package be.wallonie.ddd.domain.ecole.common.valueobject;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

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
}