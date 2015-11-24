package be.domaindrivendesign.kernel.rule.model;

import be.domaindrivendesign.kernel.rule.entity.RuleObject01;
import be.domaindrivendesign.kernel.rule.entity.RuleObject02;
import be.domaindrivendesign.kernel.rule.entity.RuleObjectA;
import be.domaindrivendesign.kernel.rule.entity.RuleObjectB;
import be.domaindrivendesign.kernel.rule.error.RuleException;
import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by asmolabs on 14/11/15.
 */
public class RuleGuardTest {

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testEqualsInvariantOneLevelTrue() throws Exception {
        RuleObjectA ruleObjectA = new RuleObjectA();

        ruleObjectA.setNumber(100);

        boolean result = RuleGuard.equalsInvariant(ruleObjectA, ruleObjectA::getNumber, 100);

        Assert.assertTrue(result);
    }

    @Test
    public void testEqualsInvariantOneLevelFalse() throws Exception {
        System.out.println("testEqualsInvariantOneLevelFalseThread" + Thread.currentThread().getId());
        RuleObjectA ruleObjectA = new RuleObjectA();

        ruleObjectA.setNumber(100);

        boolean result = RuleGuard.equalsInvariant(ruleObjectA, () -> ruleObjectA.getNumber(), 10);

        final List<RuleViolation> violations = UnitOfWorkRule.getInstance().getViolations();

        Assert.assertFalse(result);

        Assert.assertTrue(violations.size() == 1);

        Assert.assertTrue(violations.get(0).getPropertyPaths().get(0).equals("be.domaindrivendesign.kernel.rule.entity|RuleObjectA.getNumber"));
    }

    @Test
    public void testEqualsInvariantTwoLevelTrue() throws Exception {
        RuleObjectA ruleObjectA = new RuleObjectA();

        ruleObjectA.setNumber(100);

        RuleObjectB ruleObjectB = new RuleObjectB();

        ruleObjectB.setCount(100);

        ruleObjectA.setRuleObjectB(ruleObjectB);

        boolean result = RuleGuard.equalsInvariant(ruleObjectA, () -> ruleObjectA.getRuleObjectB().getCount(), 100);

        Assert.assertTrue(result);
    }

    @Test
    public void testEqualsInvariantTwoLevelFalse() throws Exception {
        System.out.println("testEqualsInvariantTwoLevelFalseThread" + Thread.currentThread().getId());
        RuleObjectA ruleObjectA = new RuleObjectA();

        ruleObjectA.setNumber(100);

        RuleObjectB ruleObjectB = new RuleObjectB();

        ruleObjectB.setCount(100);

        ruleObjectA.setRuleObjectB(ruleObjectB);

        boolean result = RuleGuard.equalsInvariant(ruleObjectA, () -> ruleObjectA.getRuleObjectB().getCount(), 10);

        final List<RuleViolation> violations = UnitOfWorkRule.getInstance().getViolations();

        Assert.assertFalse(result);

        Assert.assertTrue(violations.size() == 1);
        Assert.assertTrue(violations.get(0).getPropertyPaths().get(0).equals("be.domaindrivendesign.kernel.rule.entity|RuleObjectA.getRuleObjectB.getCount"));
    }

    @Test
    public void testRuleSeverityTypeWarning() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // Violation
        Assert.assertFalse(RuleGuard.equalsInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2, RuleSeverityType.Warning));
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
    }

    @Test
    public void testRuleSeverityTypeError() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // Violation
        Assert.assertFalse(RuleGuard.equalsInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2, RuleSeverityType.Error));
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
    }

    @Test
    public void testRuleSeverityTypeBlockingError() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);
        // Violation
        try {
            Assert.assertFalse(RuleGuard.equalsInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2, RuleSeverityType.BlockingError));
            // Exception raised -> following line never reached
            Assert.fail();
        } catch (RuleException exception) {
            // Even if exception is raised, the blokcking error is contained within the UnitOfWork violations
            Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.BlockingError);
        }
    }

    @Test
    public void testEqualsNumberInvariant() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation
        Assert.assertTrue(RuleGuard.equalsInvariant(ruleObject, () -> ruleObject.getAttribute01(), 1, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.equalsInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2, RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsNumberInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2").toArray());
    }

    @Test
    public void testEqualsNumberInvariantWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation
        Assert.assertTrue(RuleGuard.equalsInvariant(ruleObject, () -> ruleObject.getAttribute01(), 1));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.equalsInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsNumberInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2").toArray());
    }

    @Test
    public void testSmallerThanInvariant() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation
        Assert.assertTrue(RuleGuard.smallerThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.smallerThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 1, RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.SmallerThanInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "1").toArray());
    }

    @Test
    public void testSmallerThanInvariantWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation
        Assert.assertTrue(RuleGuard.smallerThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.smallerThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 1));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.SmallerThanInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "1").toArray());
    }

    @Test
    public void testSmallerOrEqualThanInvariant() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.smallerOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // No violation Equal
        Assert.assertTrue(RuleGuard.smallerOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 1, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.smallerOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 0, RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.SmallerOrEqualThanInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "0").toArray());
    }

    @Test
    public void testSmallerOrEqualThanInvariantWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.smallerOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // No violation Equal
        Assert.assertTrue(RuleGuard.smallerOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 1));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.smallerOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 0));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.SmallerOrEqualThanInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "0").toArray());
    }

    @Test
    public void testGreaterThanInvariant() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.greaterThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 0, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.greaterThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2, RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.GreaterThanInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2").toArray());
    }

    @Test
    public void testGreaterThanInvariantWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.greaterThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 0));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.greaterThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 2));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.GreaterThanInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2").toArray());
    }

    @Test
    public void testGreaterOrEqualThanInvariant() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.greaterOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 0, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // No violation Equal
        Assert.assertTrue(RuleGuard.greaterOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 1, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.greaterOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 3, RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.GreaterOrEqualThanInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "3").toArray());
    }

    @Test
    public void testGreaterOrEqualThanInvariantWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.greaterOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 0));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // No violation Equal
        Assert.assertTrue(RuleGuard.greaterOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 1));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.greaterOrEqualThanInvariant(ruleObject, () -> ruleObject.getAttribute01(), 3));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.GreaterOrEqualThanInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "3").toArray());
    }


    @Test
    public void testEquals() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setAttribute01(1);

        ruleObject02.setAttribute02(1);

        // No violation
        Assert.assertTrue(RuleGuard.equals(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(2);
        Assert.assertFalse(RuleGuard.equals(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getAttribute02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsNumber.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2").toArray());
    }

    @Test
    public void testEqualsWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setAttribute01(1);

        ruleObject02.setAttribute02(1);

        // No violation
        Assert.assertTrue(RuleGuard.equals(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(2);
        Assert.assertFalse(RuleGuard.equals(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getAttribute02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsNumber.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2").toArray());
    }

    @Test
    public void testSmallerThan() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setAttribute01(1);

        ruleObject02.setAttribute02(2);

        // No violation
        Assert.assertTrue(RuleGuard.smallerThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(0);
        Assert.assertFalse(RuleGuard.smallerThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getAttribute02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.SmallerThan.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "0").toArray());
    }

    @Test
    public void testSmallerThanWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setAttribute01(1);

        ruleObject02.setAttribute02(2);

        // No violation
        Assert.assertTrue(RuleGuard.smallerThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(0);
        Assert.assertFalse(RuleGuard.smallerThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getAttribute02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.SmallerThan.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "0").toArray());
    }

    @Test
    public void testGreaterThan() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setAttribute01(1);

        ruleObject02.setAttribute02(0);

        // No violation
        Assert.assertTrue(RuleGuard.greaterThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(2);
        Assert.assertFalse(RuleGuard.greaterThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getAttribute02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.GreaterThan.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2").toArray());
    }

    @Test
    public void testGreaterThanWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setAttribute01(1);

        ruleObject02.setAttribute02(0);

        // No violation
        Assert.assertTrue(RuleGuard.greaterThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(2);
        Assert.assertFalse(RuleGuard.greaterThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getAttribute02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.GreaterThan.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2").toArray());
    }

    @Test
    public void testBetween() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.between(ruleObject, () -> ruleObject.getAttribute01(), 0, 2, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.between(ruleObject, () -> ruleObject.getAttribute01(), 2, 3, RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Between.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2", "3").toArray());
    }

    @Test
    public void testBetweenWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.between(ruleObject, () -> ruleObject.getAttribute01(), 0, 2));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.between(ruleObject, () -> ruleObject.getAttribute01(), 2, 3));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Between.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2", "3").toArray());
    }

    @Test
    public void testSmallerOrEqualThan() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setAttribute01(1);
        ruleObject02.setAttribute02(2);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.smallerOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // No violation Equal
        ruleObject02.setAttribute02(1);

        Assert.assertTrue(RuleGuard.smallerOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(0);
        Assert.assertFalse(RuleGuard.smallerOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getAttribute02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.SmallerOrEqualThan.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "0").toArray());
    }

    @Test
    public void testSmallerOrEqualThanWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setAttribute01(1);
        ruleObject02.setAttribute02(2);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.smallerOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // No violation Equal
        ruleObject02.setAttribute02(1);

        Assert.assertTrue(RuleGuard.smallerOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(0);
        Assert.assertFalse(RuleGuard.smallerOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getAttribute02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.SmallerOrEqualThan.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "0").toArray());
    }



    @Test
    public void testGreaterOrEqualThan() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setAttribute01(1);
        ruleObject02.setAttribute02(0);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.greaterOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // No violation Equal
        ruleObject02.setAttribute02(1);

        Assert.assertTrue(RuleGuard.greaterOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(3);
        Assert.assertFalse(RuleGuard.greaterOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getAttribute02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.GreaterOrEqualThan.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "3").toArray());
    }

    @Test
    public void testGreaterOrEqualThanWithoutSevertity() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setAttribute01(1);
        ruleObject02.setAttribute02(0);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.greaterOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // No violation Equal
        ruleObject02.setAttribute02(1);

        Assert.assertTrue(RuleGuard.greaterOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(3);
        Assert.assertFalse(RuleGuard.greaterOrEqualThan(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getAttribute02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.GreaterOrEqualThan.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "3").toArray());
    }

    @Test
    public void testDomain() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.domain(ruleObject, () -> ruleObject.getAttribute01(), Arrays.asList(1, 2), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.domain(ruleObject, () -> ruleObject.getAttribute01(), Arrays.asList(2, 3), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.DomainNumber.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2", "3").toArray());
    }

    @Test
    public void testDomainWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.domain(ruleObject, () -> ruleObject.getAttribute01(), Arrays.asList(1, 2)));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.domain(ruleObject, () -> ruleObject.getAttribute01(), Arrays.asList(2, 3)));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.DomainNumber.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2", "3").toArray());
    }
}