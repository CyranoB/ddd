package be.wallonie.ddd.kernel.rule.model;

import be.wallonie.ddd.kernel.rule.entity.RuleObjectA;
import be.wallonie.ddd.kernel.rule.entity.RuleObjectB;
import be.wallonie.ddd.kernel.rule.interfaces.RuleViolation;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by asmolabs on 14/11/15.
 */
public class RuleGuardTest {

    @Test
    public void testEqualsInvariantOneLevelTrue() throws Exception {
        RuleObjectA ruleObjectA = new RuleObjectA();

        ruleObjectA.setNumber(100);

        boolean result;

        result = RuleGuard.equalsInvariant(ruleObjectA, ruleObjectA::getNumber, 100);

        Assert.assertTrue(result);
    }

    @Test
    public void testEqualsInvariantOneLevelFalse() throws Exception {
        RuleObjectA ruleObjectA = new RuleObjectA();

        ruleObjectA.setNumber(100);

        RuleObjectB ruleObjectB = new RuleObjectB();

        ruleObjectB.setCount(100);

        ruleObjectA.setRuleObjectB(ruleObjectB);

        boolean result = false;

        result = RuleGuard.equalsInvariant(ruleObjectA, () -> ruleObjectA.getNumber(), 10);

        final List<RuleViolation> violations = UnitOfWorkRule.getDefault().getViolations();

        Assert.assertFalse(result);

        Assert.assertTrue(violations.size() == 1);
        Assert.assertTrue(violations.get(0).getPropertyPaths().get(0).equals("be.wallonie.ddd.kernel.rule.entity.RuleObjectA|getNumber"));
    }
}