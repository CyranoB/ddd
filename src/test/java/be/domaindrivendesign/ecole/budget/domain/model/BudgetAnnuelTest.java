package be.domaindrivendesign.ecole.budget.domain.model;

import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.domain.control.DefaultDomainEventManager;
import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class BudgetAnnuelTest {

    @After
    public void cleanCallContext() {
        UnitOfWorkRule.getInstance().clear();
        DefaultDomainEventManager.getInstance().destroy();
    }

    //region Tests sur la méthode BudgetAnnuel.creer
    @Test
    public void testCreer() {
        // Arrange
        AnneeScolaire anneeScolaire = new AnneeScolaire(2015, 2016);

        // Act
        BudgetAnnuel budgetAnnuel = BudgetAnnuel.creer(anneeScolaire, BigDecimal.valueOf(10), BigDecimal.valueOf(5), 20, 15);

        // Assert
        Assert.assertNotNull(budgetAnnuel);
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertSame(anneeScolaire, budgetAnnuel.getAnneeScolaire());
        Assert.assertEquals(BigDecimal.valueOf(10), budgetAnnuel.getMontantAideFruitEtLegumeParEleve());
        Assert.assertEquals(BigDecimal.valueOf(5), budgetAnnuel.getMontantAideLaitParEleve());
        Assert.assertEquals(20, budgetAnnuel.getFruitEtLegumeNbrEleve());
        Assert.assertEquals(15, budgetAnnuel.getLaitNbrEleve());
    }

    @Test
    public void testCreer_AnneeScolaireNull() {
        // Arrange

        // Act
        BudgetAnnuel budgetAnnuel = BudgetAnnuel.creer(null, BigDecimal.valueOf(10), BigDecimal.valueOf(5), 20, 15);

        // Assert
        Assert.assertNotNull(budgetAnnuel);
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        RuleViolation violation = UnitOfWorkRule.getInstance().getViolations().get(0);
        Assert.assertEquals(RuleType.Mandatory.typeValue, violation.getRuleId());

        Assert.assertNull(budgetAnnuel.getAnneeScolaire());
        Assert.assertEquals(BigDecimal.valueOf(10), budgetAnnuel.getMontantAideFruitEtLegumeParEleve());
        Assert.assertEquals(BigDecimal.valueOf(5), budgetAnnuel.getMontantAideLaitParEleve());
        Assert.assertEquals(20, budgetAnnuel.getFruitEtLegumeNbrEleve());
        Assert.assertEquals(15, budgetAnnuel.getLaitNbrEleve());
    }


    @Test
    public void testCreer_MontantAideFruitEtLegumeparEleveNull() {
        // Arrange
        AnneeScolaire anneeScolaire = new AnneeScolaire(2015, 2016);

        // Act
        BudgetAnnuel budgetAnnuel = BudgetAnnuel.creer(anneeScolaire, null, BigDecimal.valueOf(5), 20, 15);

        // Assert
        Assert.assertNotNull(budgetAnnuel);
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        RuleViolation violation = UnitOfWorkRule.getInstance().getViolations().get(0);
        Assert.assertEquals(RuleType.GreaterOrEqualThanInvariant.typeValue, violation.getRuleId());

        Assert.assertSame(anneeScolaire, budgetAnnuel.getAnneeScolaire());
        Assert.assertNull(budgetAnnuel.getMontantAideFruitEtLegumeParEleve());
        Assert.assertEquals(BigDecimal.valueOf(5), budgetAnnuel.getMontantAideLaitParEleve());
        Assert.assertEquals(20, budgetAnnuel.getFruitEtLegumeNbrEleve());
        Assert.assertEquals(15, budgetAnnuel.getLaitNbrEleve());
    }

    @Test
    public void testCreer_NegativeMontantAideFruitEtLegumeparEleve() {
        // Arrange
        AnneeScolaire anneeScolaire = new AnneeScolaire(2015, 2016);

        // Act
        BudgetAnnuel budgetAnnuel = BudgetAnnuel.creer(anneeScolaire, BigDecimal.valueOf(-5.0), BigDecimal.valueOf(5), 20, 15);

        // Assert
        Assert.assertNotNull(budgetAnnuel);
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        RuleViolation violation = UnitOfWorkRule.getInstance().getViolations().get(0);
        Assert.assertEquals(RuleType.GreaterOrEqualThanInvariant.typeValue, violation.getRuleId());

        Assert.assertSame(anneeScolaire, budgetAnnuel.getAnneeScolaire());
        Assert.assertEquals(BigDecimal.valueOf(-5.0), budgetAnnuel.getMontantAideFruitEtLegumeParEleve());
        Assert.assertEquals(BigDecimal.valueOf(5), budgetAnnuel.getMontantAideLaitParEleve());
        Assert.assertEquals(20, budgetAnnuel.getFruitEtLegumeNbrEleve());
        Assert.assertEquals(15, budgetAnnuel.getLaitNbrEleve());
    }

    @Test
    public void testCreer_MontantAideLaitparEleveNull() {
        // Arrange
        AnneeScolaire anneeScolaire = new AnneeScolaire(2015, 2016);

        // Act
        BudgetAnnuel budgetAnnuel = BudgetAnnuel.creer(anneeScolaire, BigDecimal.valueOf(10), null, 20, 15);

        // Assert
        Assert.assertNotNull(budgetAnnuel);
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        RuleViolation violation = UnitOfWorkRule.getInstance().getViolations().get(0);
        Assert.assertEquals(RuleType.GreaterOrEqualThanInvariant.typeValue, violation.getRuleId());

        Assert.assertSame(anneeScolaire, budgetAnnuel.getAnneeScolaire());
        Assert.assertEquals(BigDecimal.valueOf(10), budgetAnnuel.getMontantAideFruitEtLegumeParEleve());
        Assert.assertNull(budgetAnnuel.getMontantAideLaitParEleve());
        Assert.assertEquals(20, budgetAnnuel.getFruitEtLegumeNbrEleve());
        Assert.assertEquals(15, budgetAnnuel.getLaitNbrEleve());
    }

    @Test
    public void testCreer_NegativeMontantAideLaitparEleve() {
        // Arrange
        AnneeScolaire anneeScolaire = new AnneeScolaire(2015, 2016);

        // Act
        BudgetAnnuel budgetAnnuel = BudgetAnnuel.creer(anneeScolaire, BigDecimal.valueOf(10), BigDecimal.valueOf(-2.0), 20, 15);

        // Assert
        Assert.assertNotNull(budgetAnnuel);
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        RuleViolation violation = UnitOfWorkRule.getInstance().getViolations().get(0);
        Assert.assertEquals(RuleType.GreaterOrEqualThanInvariant.typeValue, violation.getRuleId());

        Assert.assertSame(anneeScolaire, budgetAnnuel.getAnneeScolaire());
        Assert.assertEquals(BigDecimal.valueOf(10), budgetAnnuel.getMontantAideFruitEtLegumeParEleve());
        Assert.assertEquals(BigDecimal.valueOf(-2.0), budgetAnnuel.getMontantAideLaitParEleve());
        Assert.assertEquals(20, budgetAnnuel.getFruitEtLegumeNbrEleve());
        Assert.assertEquals(15, budgetAnnuel.getLaitNbrEleve());
    }

    //endregion

    //region Tests sur la méthode BudgetAnnuel.ModifierFruitEtLegumeNbrEleve
    @Test
    public void testModifierFruitEtLegumeNbrEleve() {
        // Arrange
        BudgetAnnuel budgetAnnuel = BudgetAnnuel.creer(new AnneeScolaire(2015, 2016), BigDecimal.valueOf(10), BigDecimal.valueOf(5), 20, 15);

        // Act
        budgetAnnuel.modifierFruitEtLegumeNbrEleve(25);

        // Assert
        Assert.assertEquals(25, budgetAnnuel.getFruitEtLegumeNbrEleve());
    }

    @Test
    public void testModifierFruitEtLegumeNbrEleve_NegativeParameter() {
        // Arrange
        BudgetAnnuel budgetAnnuel = BudgetAnnuel.creer(new AnneeScolaire(2015, 2016), BigDecimal.valueOf(10), BigDecimal.valueOf(5), 20, 15);

        // Act
        budgetAnnuel.modifierFruitEtLegumeNbrEleve(-5);

        // Assert
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        RuleViolation violation = UnitOfWorkRule.getInstance().getViolations().get(0);
        Assert.assertEquals(RuleType.GreaterOrEqualThanInvariant.typeValue, violation.getRuleId());

        Assert.assertEquals(-5, budgetAnnuel.getFruitEtLegumeNbrEleve());
    }
    //endregion

    //region Tests sur la méthode BudgetAnnuel.ModifierAideLaitNbrEleve
    @Test
    public void testModifierAideLaitNbrEleve() {
        // Arrange
        BudgetAnnuel budgetAnnuel = BudgetAnnuel.creer(new AnneeScolaire(2015, 2016), BigDecimal.valueOf(10), BigDecimal.valueOf(5), 20, 15);

        // Act
        budgetAnnuel.modifierAideLaitNbrEleve(7);

        // Assert
        Assert.assertEquals(7, budgetAnnuel.getLaitNbrEleve());
    }

    @Test
    public void testModifierAideLaitNbrEleve_NegativeParameter() {
        // Arrange
        BudgetAnnuel budgetAnnuel = BudgetAnnuel.creer(new AnneeScolaire(2015, 2016), BigDecimal.valueOf(10), BigDecimal.valueOf(5), 20, 15);

        // Act
        budgetAnnuel.modifierAideLaitNbrEleve(-5);

        // Assert
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        RuleViolation violation = UnitOfWorkRule.getInstance().getViolations().get(0);
        Assert.assertEquals(RuleType.GreaterOrEqualThanInvariant.typeValue, violation.getRuleId());

        Assert.assertEquals(-5, budgetAnnuel.getLaitNbrEleve());
    }
    //endregion
}
   
