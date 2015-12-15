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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by asmolabs on 14/11/15.
 */
public class RuleGuardTest implements Serializable {

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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertTrue(RuleGuard.equal(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(2);
        Assert.assertFalse(RuleGuard.equal(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02(), RuleSeverityType.Warning));

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
        Assert.assertTrue(RuleGuard.equal(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setAttribute02(2);
        Assert.assertFalse(RuleGuard.equal(ruleObject01, () -> ruleObject01.getAttribute01(), () -> ruleObject02.getAttribute02()));

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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
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
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.DomainNumber.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2", "3").toArray());
    }

    @Test
    public void testIsFormat() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setDouble01(1.1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.isFormat(ruleObject, () -> ruleObject.getDouble01(), 1, 1, false, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        ruleObject.setDouble01(100.0);

        // Violation
        Assert.assertFalse(RuleGuard.isFormat(ruleObject, () -> ruleObject.getDouble01(), 2, 2, false, RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getDouble01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.IsFormatDecimal.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("100.0", "2", "2").toArray());
    }

    @Test
    public void testIsFormatWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setDouble01(1.1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.isFormat(ruleObject, () -> ruleObject.getDouble01(), 1, 1, false));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        ruleObject.setDouble01(100.0);

        // Violation
        Assert.assertFalse(RuleGuard.isFormat(ruleObject, () -> ruleObject.getDouble01(), 2, 2, false));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getDouble01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.IsFormatDecimal.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("100.0", "2", "2").toArray());
    }

    @Test
    public void testIsFormatWithoutSeverityAndNullAllowed() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setDouble01(1.1);

        // No violation Smaller
        Assert.assertTrue(RuleGuard.isFormat(ruleObject, () -> ruleObject.getDouble01(), 1, 1));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        ruleObject.setDouble01(100.0);

        // Violation
        Assert.assertFalse(RuleGuard.isFormat(ruleObject, () -> ruleObject.getDouble01(), 2, 2));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getDouble01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.IsFormatDecimal.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("100.0", "2", "2").toArray());
    }

    @Test
    public void testBefore() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2015, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.before(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setLocalDateTime02(LocalDateTime.of(2013, 1, 1, 0, 0));
        Assert.assertFalse(RuleGuard.before(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getLocalDateTime02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Before.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2013-01-01T00:00").toArray());
    }

    @Test
    public void testBeforeWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2015, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.before(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setLocalDateTime02(LocalDateTime.of(2013, 1, 1, 0, 0));
        Assert.assertFalse(RuleGuard.before(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getLocalDateTime02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Before.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2013-01-01T00:00").toArray());
    }

    @Test
    public void testBeforeInvariant() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.beforeInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2015, 1, 1, 0, 0), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.beforeInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2013, 1, 1, 0, 0), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.BeforeInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2013-01-01T00:00").toArray());
    }

    @Test
    public void testBeforeInvariantWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.beforeInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2015, 1, 1, 0, 0)));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.beforeInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2013, 1, 1, 0, 0)));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.BeforeInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2013-01-01T00:00").toArray());
    }

    @Test
    public void testBeforeOrEqual() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2015, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.beforeOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2014, 1, 1, 0, 0));

        Assert.assertTrue(RuleGuard.beforeOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setLocalDateTime02(LocalDateTime.of(2013, 1, 1, 0, 0));
        Assert.assertFalse(RuleGuard.beforeOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getLocalDateTime02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.BeforeOrEqual.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2013-01-01T00:00").toArray());
    }

    @Test
    public void testBeforeOrEqualsWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2015, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.beforeOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2014, 1, 1, 0, 0));

        Assert.assertTrue(RuleGuard.beforeOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setLocalDateTime02(LocalDateTime.of(2013, 1, 1, 0, 0));
        Assert.assertFalse(RuleGuard.beforeOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getLocalDateTime02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.BeforeOrEqual.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2013-01-01T00:00").toArray());
    }

    @Test
    public void testBeforeOrEqualInvariant() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.beforeOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2015, 1, 1, 0, 0), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        Assert.assertTrue(RuleGuard.beforeOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2014, 1, 1, 0, 0), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.beforeOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2013, 1, 1, 0, 0), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.BeforeOrEqualInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2013-01-01T00:00").toArray());
    }

    @Test
    public void testBeforeOrEqualInvariantWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.beforeOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2015, 1, 1, 0, 0)));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        Assert.assertTrue(RuleGuard.beforeOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2014, 1, 1, 0, 0)));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.beforeOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2013, 1, 1, 0, 0)));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.BeforeOrEqualInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2013-01-01T00:00").toArray());
    }

    @Test
    public void testAfter() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2015, 1, 1, 0, 0));

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2014, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.after(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setLocalDateTime02(LocalDateTime.of(2016, 1, 1, 0, 0));
        Assert.assertFalse(RuleGuard.after(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getLocalDateTime02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.After.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2015-01-01T00:00", "2016-01-01T00:00").toArray());
    }

    @Test
    public void testAfterWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2015, 1, 1, 0, 0));

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2014, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.after(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setLocalDateTime02(LocalDateTime.of(2016, 1, 1, 0, 0));
        Assert.assertFalse(RuleGuard.after(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getLocalDateTime02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.After.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2015-01-01T00:00", "2016-01-01T00:00").toArray());
    }

    @Test
    public void testAfterInvariant() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.afterInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2013, 1, 1, 0, 0), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.afterInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2015, 1, 1, 0, 0), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.AfterInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2015-01-01T00:00").toArray());
    }

    @Test
    public void testAfterInvariantWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.afterInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2013, 1, 1, 0, 0)));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.afterInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2015, 1, 1, 0, 0)));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.AfterInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2015-01-01T00:00").toArray());
    }

    @Test
    public void testAfterOrEqual() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2013, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.afterOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2014, 1, 1, 0, 0));

        Assert.assertTrue(RuleGuard.afterOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setLocalDateTime02(LocalDateTime.of(2015, 1, 1, 0, 0));
        Assert.assertFalse(RuleGuard.afterOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getLocalDateTime02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.AfterOrEqual.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2015-01-01T00:00").toArray());
    }

    @Test
    public void testAfterOrEqualsWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2013, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.afterOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2014, 1, 1, 0, 0));

        Assert.assertTrue(RuleGuard.afterOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setLocalDateTime02(LocalDateTime.of(2015, 1, 1, 0, 0));
        Assert.assertFalse(RuleGuard.afterOrEqual(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getLocalDateTime02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.AfterOrEqual.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2015-01-01T00:00").toArray());
    }

    @Test
    public void testAfterOrEqualInvariant() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.afterOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2013, 1, 1, 0, 0), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        Assert.assertTrue(RuleGuard.afterOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2014, 1, 1, 0, 0), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.afterOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2015, 1, 1, 0, 0), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.AfterOrEqualInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2015-01-01T00:00").toArray());
    }

    @Test
    public void testAfterOrEqualInvariantWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2014, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.afterOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2013, 1, 1, 0, 0)));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        Assert.assertTrue(RuleGuard.afterOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2014, 1, 1, 0, 0)));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.afterOrEqualInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2015, 1, 1, 0, 0)));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.AfterOrEqualInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2014-01-01T00:00", "2015-01-01T00:00").toArray());
    }

    @Test
    public void testEqualsTemporal() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2015, 1, 1, 0, 0));

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2015, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.equal(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setLocalDateTime02(LocalDateTime.of(2016, 1, 1, 0, 0));
        Assert.assertFalse(RuleGuard.equal(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getLocalDateTime02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsTemporal.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2015-01-01T00:00", "2016-01-01T00:00").toArray());
    }

    @Test
    public void testEqualsTemporalWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();
        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2015, 1, 1, 0, 0));

        ruleObject02.setLocalDateTime02(LocalDateTime.of(2015, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.equal(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setLocalDateTime02(LocalDateTime.of(2016, 1, 1, 0, 0));
        Assert.assertFalse(RuleGuard.equal(ruleObject01, () -> ruleObject01.getLocalDateTime01(), () -> ruleObject02.getLocalDateTime02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getLocalDateTime02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsTemporal.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2015-01-01T00:00", "2016-01-01T00:00").toArray());
    }

    @Test
    public void testEqualsInvariantTemporal() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2015, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.equalsInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2015, 1, 1, 0, 0), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.equalsInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2016, 1, 1, 0, 0), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsTemporalInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2015-01-01T00:00", "2016-01-01T00:00").toArray());
    }

    @Test
    public void testEqualsInvarantTemporalWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setLocalDateTime01(LocalDateTime.of(2015, 1, 1, 0, 0));

        // No violation
        Assert.assertTrue(RuleGuard.equalsInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2015, 1, 1, 0, 0)));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.equalsInvariant(ruleObject01, () -> ruleObject01.getLocalDateTime01(), LocalDateTime.of(2016, 1, 1, 0, 0)));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsTemporalInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2015-01-01T00:00", "2016-01-01T00:00").toArray());
    }

    @Test
    public void testDomainTemporal() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setLocalDateTime01(LocalDateTime.of(2015, 1, 1, 0, 0));

        // No violation Smaller
        Assert.assertTrue(RuleGuard.domain(ruleObject, () -> ruleObject.getLocalDateTime01(), new ArrayList<>(Arrays.asList(LocalDateTime.of(2015, 1, 1, 0, 0), LocalDateTime.of(2015, 2, 1, 0, 0))), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.domain(ruleObject, () -> ruleObject.getLocalDateTime01(), new ArrayList<>(Arrays.asList(LocalDateTime.of(2015, 2, 1, 0, 0), LocalDateTime.of(2015, 3, 1, 0, 0))), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.DomainTemporal.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2015-01-01T00:00", "2015-02-01T00:00", "2015-03-01T00:00").toArray());
    }

    @Test
    public void testDomainTemporalWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setLocalDateTime01(LocalDateTime.of(2015, 1, 1, 0, 0));

        // No violation Smaller
        Assert.assertTrue(RuleGuard.domain(ruleObject, () -> ruleObject.getLocalDateTime01(), new ArrayList<>(Arrays.asList(LocalDateTime.of(2015, 1, 1, 0, 0), LocalDateTime.of(2015, 2, 1, 0, 0)))));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        Assert.assertFalse(RuleGuard.domain(ruleObject, () -> ruleObject.getLocalDateTime01(), new ArrayList<>(Arrays.asList(LocalDateTime.of(2015, 2, 1, 0, 0), LocalDateTime.of(2015, 3, 1, 0, 0)))));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLocalDateTime01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.DomainTemporal.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("2015-01-01T00:00", "2015-02-01T00:00", "2015-03-01T00:00").toArray());
    }

    @Test
    public void testNullOrEmpty() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("");

        // No violation
        Assert.assertTrue(RuleGuard.nullOrEmpty(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        ruleObject.setString01(null);

        // No violation
        Assert.assertTrue(RuleGuard.nullOrEmpty(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject.setString01("Test");

        Assert.assertFalse(RuleGuard.nullOrEmpty(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.NullOrEmpty.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("Test").toArray());
    }

    @Test
    public void testNullOrEmptyWitoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("");

        // No violation
        Assert.assertTrue(RuleGuard.nullOrEmpty(ruleObject, () -> ruleObject.getString01()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        ruleObject.setString01(null);

        // No violation
        Assert.assertTrue(RuleGuard.nullOrEmpty(ruleObject, () -> ruleObject.getString01()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject.setString01("Test");

        Assert.assertFalse(RuleGuard.nullOrEmpty(ruleObject, () -> ruleObject.getString01()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.NullOrEmpty.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("Test").toArray());
    }

    @Test
    public void testEqualsString() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setString01("Test");

        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject02.setString02("Test");

        // No violation Smaller
        Assert.assertTrue(RuleGuard.equal(ruleObject01, () -> ruleObject01.getString01(), () -> ruleObject02.getString02(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setString02("test");

        Assert.assertFalse(RuleGuard.equal(ruleObject01, () -> ruleObject01.getString01(), () -> ruleObject02.getString02(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getString02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsString.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("Test", "test").toArray());
    }

    @Test
    public void testEqualsStringWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setString01("Test");

        RuleObject02 ruleObject02 = new RuleObject02();

        ruleObject02.setString02("Test");

        // No violation Smaller
        Assert.assertTrue(RuleGuard.equal(ruleObject01, () -> ruleObject01.getString01(), () -> ruleObject02.getString02()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        // Violation
        ruleObject02.setString02("test");

        Assert.assertFalse(RuleGuard.equal(ruleObject01, () -> ruleObject01.getString01(), () -> ruleObject02.getString02()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Arrays.asList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01", "be.domaindrivendesign.kernel.rule.entity|RuleObject02.getString02").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsString.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("Test", "test").toArray());
    }

    @Test
    public void testEqualsInvariantString() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setString01("Test");

        // No violation
        Assert.assertTrue(RuleGuard.equalsInvariant(ruleObject01, () -> ruleObject01.getString01(), "Test", RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        Assert.assertFalse(RuleGuard.equalsInvariant(ruleObject01, () -> ruleObject01.getString01(), "test", RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsStringInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("Test", "test").toArray());
    }

    @Test
    public void testEqualsStringInvariantWithoutSeverity() {
        RuleObject01 ruleObject01 = new RuleObject01();

        ruleObject01.setString01("Test");

        // No violation
        Assert.assertTrue(RuleGuard.equalsInvariant(ruleObject01, () -> ruleObject01.getString01(), "Test"));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());

        Assert.assertFalse(RuleGuard.equalsInvariant(ruleObject01, () -> ruleObject01.getString01(), "test"));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject01);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EqualsStringInvariant.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("Test", "test").toArray());
    }

    @Test
    public void testDigitOnly() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("11.1");

        // No violation
        Assert.assertTrue(RuleGuard.digitOnly(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        ruleObject.setString01("11.1€");

        Assert.assertFalse(RuleGuard.digitOnly(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.DigitOnly.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("11.1€").toArray());
    }

    @Test
    public void testDigitOnlyWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("11.1");

        // No violation
        Assert.assertTrue(RuleGuard.digitOnly(ruleObject, () -> ruleObject.getString01()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        ruleObject.setString01("11.1€");

        Assert.assertFalse(RuleGuard.digitOnly(ruleObject, () -> ruleObject.getString01()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.DigitOnly.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("11.1€").toArray());
    }

    @Test
    public void testLength() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("11.1");

        // No violation
        Assert.assertTrue(RuleGuard.length(ruleObject, () -> ruleObject.getString01(), 4, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation

        Assert.assertFalse(RuleGuard.length(ruleObject, () -> ruleObject.getString01(), 5, RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Length.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("11.1", "5").toArray());
    }

    @Test
    public void testLengthWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("11.1");

        // No violation
        Assert.assertTrue(RuleGuard.length(ruleObject, () -> ruleObject.getString01(), 4));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation

        Assert.assertFalse(RuleGuard.length(ruleObject, () -> ruleObject.getString01(), 5));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Length.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("11.1", "5").toArray());
    }

    @Test
    public void testDomainString() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("1");

        // No violation
        Assert.assertTrue(RuleGuard.domain(ruleObject, () -> ruleObject.getString01(), Arrays.asList("1", "2"), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation

        Assert.assertFalse(RuleGuard.domain(ruleObject, () -> ruleObject.getString01(), Arrays.asList("2", "3"), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.DomainString.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2", "3").toArray());
    }

    @Test
    public void testDomainStringWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("1");

        // No violation
        Assert.assertTrue(RuleGuard.domain(ruleObject, () -> ruleObject.getString01(), Arrays.asList("1", "2")));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation

        Assert.assertFalse(RuleGuard.domain(ruleObject, () -> ruleObject.getString01(), Arrays.asList("2", "3")));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.DomainString.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "2", "3").toArray());
    }

    @Test
    public void testMandatory() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("Test");

        // No violation
        Assert.assertTrue(RuleGuard.mandatory(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        ruleObject.setString01(null);

        Assert.assertFalse(RuleGuard.mandatory(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Mandatory.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.emptyList().toArray());
    }

    @Test
    public void testMandatoryWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("Test");

        // No violation
        Assert.assertTrue(RuleGuard.mandatory(ruleObject, () -> ruleObject.getString01()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        ruleObject.setString01(null);

        Assert.assertFalse(RuleGuard.mandatory(ruleObject, () -> ruleObject.getString01()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Mandatory.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.emptyList().toArray());
    }

    @Test
    public void testNbrOfElements() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setLists01(Arrays.asList("1", "2", "3"));

        // No violation
        Assert.assertTrue(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getLists01(), 1, 5, false, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        Assert.assertFalse(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getLists01(), 1, 2, false, RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLists01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.NbrOfElementsInList.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("3", "1", "2").toArray());
    }

    @Test
    public void testNbrOfElementsWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setLists01(Arrays.asList("1", "2", "3"));

        // No violation
        Assert.assertTrue(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getLists01(), 1, 5, false));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        Assert.assertFalse(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getLists01(), 1, 2, false));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLists01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.NbrOfElementsInList.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("3", "1", "2").toArray());
    }

    @Test
    public void testNbrOfElementsWithoutSeverityAndNullAllowed() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setLists01(Arrays.asList("1", "2", "3"));

        // No violation
        Assert.assertTrue(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getLists01(), 1, 5));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        Assert.assertFalse(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getLists01(), 1, 2));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getLists01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.NbrOfElementsInList.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("3", "1", "2").toArray());
    }

    @Test
    public void testNbrOfElementsMap() {
        RuleObject01 ruleObject = new RuleObject01();

        Map<String, String> map = new HashMap<String, String>() {
            {
                put("key01", "1");
                put("key02", "2");
                put("key03", "3");
            }
        };

        ruleObject.setMaps01(map);

        // No violation
        Assert.assertTrue(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getMaps01(), 1, 5, false, RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        Assert.assertFalse(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getMaps01(), 1, 2, false, RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getMaps01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.NbrOfElementsInList.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("3", "1", "2").toArray());
    }

    @Test
    public void testNbrOfElementsMapWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        Map<String, String> map = new HashMap<String, String>() {
            {
                put("key01", "1");
                put("key02", "2");
                put("key03", "3");
            }
        };

        ruleObject.setMaps01(map);

        // No violation
        Assert.assertTrue(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getMaps01(), 1, 5, false));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        Assert.assertFalse(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getMaps01(), 1, 2, false));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getMaps01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.NbrOfElementsInList.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("3", "1", "2").toArray());
    }

    @Test
    public void testNbrOfElementsMapWithoutSeverityAndNullAlowed() {
        RuleObject01 ruleObject = new RuleObject01();

        Map<String, String> map = new HashMap<String, String>() {
            {
                put("key01", "1");
                put("key02", "2");
                put("key03", "3");
            }
        };

        ruleObject.setMaps01(map);

        // No violation
        Assert.assertTrue(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getMaps01(), 1, 5));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        Assert.assertFalse(RuleGuard.nbrOfElements(ruleObject, () -> ruleObject.getMaps01(), 1, 2));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getMaps01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.NbrOfElementsInList.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("3", "1", "2").toArray());
    }

    @Test
    public void testNotInList() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation
        Assert.assertTrue(RuleGuard.notInList(ruleObject, () -> ruleObject.getAttribute01(), Arrays.asList(2, 3, 4), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        Assert.assertFalse(RuleGuard.notInList(ruleObject, () -> ruleObject.getAttribute01(), Arrays.asList(1, 2, 3), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.NotInList.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "1", "2", "3").toArray());
    }

    @Test
    public void testNotInListWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setAttribute01(1);

        // No violation
        Assert.assertTrue(RuleGuard.notInList(ruleObject, () -> ruleObject.getAttribute01(), Arrays.asList(2, 3, 4)));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        Assert.assertFalse(RuleGuard.notInList(ruleObject, () -> ruleObject.getAttribute01(), Arrays.asList(1, 2, 3)));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getAttribute01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.NotInList.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Arrays.asList("1", "1", "2", "3").toArray());
    }

    @Test
    public void testEmail() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("test@spw.wallonie.be");

        // No violation
        Assert.assertTrue(RuleGuard.email(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        ruleObject.setString01("test@spw.wallonie");
        Assert.assertFalse(RuleGuard.email(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EMail.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("test@spw.wallonie").toArray());
    }

    @Test
    public void testEmailWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("test@spw.wallonie.be");

        // No violation
        Assert.assertTrue(RuleGuard.email(ruleObject, () -> ruleObject.getString01()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        ruleObject.setString01("test@spw.wallonie");
        Assert.assertFalse(RuleGuard.email(ruleObject, () -> ruleObject.getString01()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.EMail.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("test@spw.wallonie").toArray());
    }

    @Test
    public void testPhone() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("0475010101");

        // No violation
        Assert.assertTrue(RuleGuard.phone(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        ruleObject.setString01("01111");
        Assert.assertFalse(RuleGuard.phone(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.PhoneNbr.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("01111").toArray());
    }

    @Test
    public void testPhoneWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("0475010101");

        // No violation
        Assert.assertTrue(RuleGuard.phone(ruleObject, () -> ruleObject.getString01()));
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());


        // Violation
        ruleObject.setString01("01111");
        Assert.assertFalse(RuleGuard.phone(ruleObject, () -> ruleObject.getString01()));

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.PhoneNbr.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("01111").toArray());
    }

    @Test
    public void testRaiseUniqueViolation() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("0475010101");

        RuleGuard.raiseUniqueViolation(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning);

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Unique.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("0475010101").toArray());

    }

    @Test
    public void testRaiseUniqueViolationWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("0475010101");

        RuleGuard.raiseUniqueViolation(ruleObject, () -> ruleObject.getString01());

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Unique.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("0475010101").toArray());

    }

    @Test
    public void testRaiseImmutableViolation() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("0475010101");

        RuleGuard.raiseImmutableViolation(ruleObject, () -> ruleObject.getString01(), RuleSeverityType.Warning);

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Immutable.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Warning);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("0475010101").toArray());

    }

    @Test
    public void testRaiseImmutableViolationWithoutSeverity() {
        RuleObject01 ruleObject = new RuleObject01();

        ruleObject.setString01("0475010101");

        RuleGuard.raiseImmutableViolation(ruleObject, () -> ruleObject.getString01());

        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), ruleObject);
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths().toArray(), Collections.singletonList("be.domaindrivendesign.kernel.rule.entity|RuleObject01.getString01").toArray());
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Immutable.typeValue);
        Assert.assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        Assert.assertArrayEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues().toArray(), Collections.singletonList("0475010101").toArray());

    }

}