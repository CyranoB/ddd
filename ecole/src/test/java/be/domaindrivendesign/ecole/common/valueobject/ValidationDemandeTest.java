package be.domaindrivendesign.ecole.common.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Period;

import static org.junit.Assert.*;

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
        assertNotNull(demande);
        assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        assertNull(demande.getAccepterLe());
        assertNull(demande.getEnValidationLe());
        assertNull(demande.getRaisonDeRejet());
        assertNull(demande.getRejeterLe());
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
        assertNotNull(demande);
        assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        assertEquals(validationLe, demande.getEnValidationLe());
        assertEquals(acceptéLe, demande.getAccepterLe());
        assertEquals(rejetéLe, demande.getRejeterLe());
        assertEquals(raisonRejet, demande.getRaisonDeRejet());
    }

    @Test
    public void testConstructor_NullParameters() {
        // Arrange

        // Act
        ValidationDemande demande = new ValidationDemande(null, null, null, null);

        // Assert
        assertNotNull(demande);
        assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        assertNull(demande.getAccepterLe());
        assertNull(demande.getEnValidationLe());
        assertNull(demande.getRaisonDeRejet());
        assertNull(demande.getRejeterLe());
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
        assertNotNull(demande);
        assertNotEquals(demande1, demande);
        assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(now, demande.getEnValidationLe());
        assertNull(demande.getAccepterLe());
        assertNull(demande.getRaisonDeRejet());
        assertNull(demande.getRejeterLe());
    }

    @Test
    public void testDemanderLaValidation_NullParameter() {
        // Arrange
        ValidationDemande demande1 = new ValidationDemande();

        // Act
        ValidationDemande demande = demande1.demanderLaValidation(null);

        // Assert
        assertNotNull(demande);
        assertNotSame(demande1, demande);
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());

        assertNull(demande.getEnValidationLe());
        assertNull(demande.getAccepterLe());
        assertNull(demande.getRaisonDeRejet());
        assertNull(demande.getRejeterLe());
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
        assertNotNull(demande);
        assertNotSame(demande1, demande);
        assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(demande.getEnValidationLe());
        assertEquals(now, demande.getAccepterLe());
        assertNull(demande.getRaisonDeRejet());
        assertNull(demande.getRejeterLe());
    }

    @Test
    public void testAccepter_NullParameter() {
        // Arrange
        ValidationDemande demande1 = new ValidationDemande();

        // Act
        ValidationDemande demande = demande1.accepter(null);

        // Assert
        assertNotNull(demande);
        assertNotSame(demande1, demande);
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());

        assertNull(demande.getEnValidationLe());
        assertNull(demande.getAccepterLe());
        assertNull(demande.getRaisonDeRejet());
        assertNull(demande.getRejeterLe());
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
        assertNotNull(demande);
        assertNotEquals(demande1, demande);
        assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(demande.getEnValidationLe());
        assertNull(demande.getAccepterLe());
        assertEquals(now, demande.getRejeterLe());
        assertEquals("Test", demande.getRaisonDeRejet());
    }

    @Test
    public void testRejeter_NullParameter() {
        // Arrange
        ValidationDemande demande1 = new ValidationDemande();

        // Act
        ValidationDemande demande = demande1.rejeter(null, null);

        // Assert
        assertNotNull(demande);
        assertNotSame(demande1, demande);
        assertEquals(2, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(1).getRuleId());

        assertNull(demande.getEnValidationLe());
        assertNull(demande.getAccepterLe());
        assertNull(demande.getRaisonDeRejet());
        assertNull(demande.getRejeterLe());
    }
    //endregion


}
