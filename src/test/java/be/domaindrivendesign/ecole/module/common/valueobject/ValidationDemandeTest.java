package be.domaindrivendesign.ecole.module.common.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Period;

/**
 * Created by eddie on 27/11/15.
 */
public class ValidationDemandeTest extends ValueObject {

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }


    //region Tests sur les constructeurs
    @Test
    public void testDefaultConstructor() {
        // Arrange

        // Act
        ValidationDemande demande = new ValidationDemande();

        // Assert
        Assert.assertNotNull(demande);
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        Assert.assertNull(demande.getAccepterLe());
        Assert.assertNull(demande.getEnValidationLe());
        Assert.assertNull(demande.getRaisonDeRejet());
        Assert.assertNull(demande.getRejeterLe());
    }

    @Test
    public void testConstructor() {
        // Arrange
        LocalDateTime validationLe = LocalDateTime.now().minus(Period.ofDays(-5));
        LocalDateTime acceptéLe = LocalDateTime.now().minus(Period.ofDays(-3));
        LocalDateTime rejetéLe = LocalDateTime.now().minus(Period.ofDays(-2));
        String raisonRejet = "Test";

        // Act
        ValidationDemande demande = new ValidationDemande(validationLe, acceptéLe, rejetéLe, raisonRejet);

        // Assert
        Assert.assertNotNull(demande);
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        Assert.assertEquals(validationLe, demande.getEnValidationLe());
        Assert.assertEquals(acceptéLe, demande.getAccepterLe());
        Assert.assertEquals(rejetéLe, demande.getRejeterLe());
        Assert.assertEquals(raisonRejet, demande.getRaisonDeRejet());
    }

    @Test
    public void testConstructor_NullParameters() {
        // Arrange

        // Act
        ValidationDemande demande = new ValidationDemande(null, null, null, null);

        // Assert
        Assert.assertNotNull(demande);
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        Assert.assertNull(demande.getAccepterLe());
        Assert.assertNull(demande.getEnValidationLe());
        Assert.assertNull(demande.getRaisonDeRejet());
        Assert.assertNull(demande.getRejeterLe());
    }
    //endregion

    //region Tests sur la méthode ValidationDemande.DemanderLaValidation
    @Test
    public void TestDemanderLaValidation() {
        // Arrange
        ValidationDemande demande1 = new ValidationDemande();
        LocalDateTime now = LocalDateTime.now();


        // Act
        ValidationDemande demande = demande1.demanderLaValidation(now);

        // Assert
        Assert.assertNotNull(demande);
        Assert.assertNotEquals(demande1, demande);
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals(now, demande.getEnValidationLe());
        Assert.assertNull(demande.getAccepterLe());
        Assert.assertNull(demande.getRaisonDeRejet());
        Assert.assertNull(demande.getRejeterLe());
    }

    @Test
    public void testDemanderLaValidation_NullParameter() {
        // Arrange
        ValidationDemande demande1 = new ValidationDemande();

        // Act
        ValidationDemande demande = demande1.demanderLaValidation(null);

        // Assert
        Assert.assertNotNull(demande);
        Assert.assertNotSame(demande1, demande);
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());

        Assert.assertNull(demande.getEnValidationLe());
        Assert.assertNull(demande.getAccepterLe());
        Assert.assertNull(demande.getRaisonDeRejet());
        Assert.assertNull(demande.getRejeterLe());
    }
    //endregion

    //region Tests sur la méthode ValidationDemande.Accepter
    @Test
    public void testAccepter() {
        // Arrange
        ValidationDemande demande1 = new ValidationDemande();
        LocalDateTime now = LocalDateTime.now();

        // Act
        ValidationDemande demande = demande1.accepter(now);

        // Assert
        Assert.assertNotNull(demande);
        Assert.assertNotSame(demande1, demande);
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(demande.getEnValidationLe());
        Assert.assertEquals(now, demande.getAccepterLe());
        Assert.assertNull(demande.getRaisonDeRejet());
        Assert.assertNull(demande.getRejeterLe());
    }

    @Test
    public void testAccepter_NullParameter() {
        // Arrange
        ValidationDemande demande1 = new ValidationDemande();

        // Act
        ValidationDemande demande = demande1.accepter(null);

        // Assert
        Assert.assertNotNull(demande);
        Assert.assertNotSame(demande1, demande);
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());

        Assert.assertNull(demande.getEnValidationLe());
        Assert.assertNull(demande.getAccepterLe());
        Assert.assertNull(demande.getRaisonDeRejet());
        Assert.assertNull(demande.getRejeterLe());
    }
    //endregion

    //region Tests sur la méthode ValidationDemande.Rejeter
    @Test
    public void testRejeter() {
        // Arrange
        ValidationDemande demande1 = new ValidationDemande();
        LocalDateTime now = LocalDateTime.now();

        // Act
        ValidationDemande demande = demande1.rejeter(now, "Test");

        // Assert
        Assert.assertNotNull(demande);
        Assert.assertNotEquals(demande1, demande);
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(demande.getEnValidationLe());
        Assert.assertNull(demande.getAccepterLe());
        Assert.assertEquals(now, demande.getRejeterLe());
        Assert.assertEquals("Test", demande.getRaisonDeRejet());
    }

    @Test
    public void testRejeter_NullParameter() {
        // Arrange
        ValidationDemande demande1 = new ValidationDemande();

        // Act
        ValidationDemande demande = demande1.rejeter(null, null);

        // Assert
        Assert.assertNotNull(demande);
        Assert.assertNotSame(demande1, demande);
        Assert.assertEquals(2, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(1).getRuleId());

        Assert.assertNull(demande.getEnValidationLe());
        Assert.assertNull(demande.getAccepterLe());
        Assert.assertNull(demande.getRaisonDeRejet());
        Assert.assertNull(demande.getRejeterLe());
    }
    //endregion


}
