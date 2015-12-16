package be.domaindrivendesign.ecole.module.common.valueobject;

import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

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

        Assert.assertNotNull(annee);
        Assert.assertEquals(2014, annee.getAnneeDebut());
        Assert.assertEquals(2015, annee.getAnneeFin());
    }

    @Test
    public void testConstrutor_BadAnneeDebut() {
        AnneeScolaire annee = new AnneeScolaire(1014, 2015);

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), annee);
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals("1014", UnitOfWorkRule.getInstance().getViolations().get(0).getValues().get(0));
        Assert.assertArrayEquals(Collections.singletonList("be.domaindrivendesign.ecole.module.common.valueobject|AnneeScolaire.getAnneeDebut").toArray(), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray());
    }

    @Test
    public void testConstrutor_BadAnneeFin() {
        AnneeScolaire annee = new AnneeScolaire(2014, 3015);

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), annee);
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals("3015", UnitOfWorkRule.getInstance().getViolations().get(0).getValues().get(0));
        Assert.assertArrayEquals(Collections.singletonList("be.domaindrivendesign.ecole.module.common.valueobject|AnneeScolaire.getAnneeFin").toArray(), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray());
    }


    @Test
    public void testConstrutor_BadAnneeDebutFin() {
        AnneeScolaire anneeScolaire = new AnneeScolaire(1980, 2115);

        Assert.assertNotNull(anneeScolaire);
        Assert.assertEquals(2, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals(RuleType.Between.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertArrayEquals(new String[]{"1980", "2000", "2100"}, UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray());
        Assert.assertEquals(RuleType.Between.typeValue, UnitOfWorkRule.getInstance().getViolations().get(1).getRuleId());
        Assert.assertArrayEquals(new String[]{"2115", "2000", "2100"}, UnitOfWorkRule.getInstance().getViolations().get(1).getValues().toArray());
        Assert.assertEquals(1980, anneeScolaire.getAnneeDebut());
        Assert.assertEquals(2115, anneeScolaire.getAnneeFin());
    }

    @Test
    public void testConstrutor_AnneeDebutSmallerThanAnneFin() {
        AnneeScolaire anneeScolaire = new AnneeScolaire(2016, 2015);

        Assert.assertNotNull(anneeScolaire);
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals(RuleType.SmallerOrEqualThan.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertArrayEquals(new String[]{"2016", "2015"}, UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray());
        Assert.assertEquals(2016, anneeScolaire.getAnneeDebut());
        Assert.assertEquals(2015, anneeScolaire.getAnneeFin());
    }

}