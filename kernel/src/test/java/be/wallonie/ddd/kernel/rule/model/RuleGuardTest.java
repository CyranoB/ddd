package be.wallonie.ddd.kernel.rule.model;

import be.wallonie.ddd.kernel.common.entity.MyClass;
import be.wallonie.ddd.kernel.common.entity.MySubClass;
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
        MyClass myClass = new MyClass();

        myClass.setNumber(100);

        boolean result;

        result = RuleGuard.equalsInvariant(myClass, myClass::getNumber, 100);

        Assert.assertTrue(result);
    }

    @Test
    public void testEqualsInvariantOneLevelFalse() throws Exception {
        MyClass myClass = new MyClass();

        myClass.setNumber(100);

        MySubClass mySubClass = new MySubClass();

        mySubClass.setCount(100);

        myClass.setMySubClass(mySubClass);

        boolean result = false;

        result = RuleGuard.equalsInvariant(myClass, () -> myClass.getNumber(), 10);

        final List<RuleViolation> violations = UnitOfWorkRule.getDefault().getViolations();

        Assert.assertFalse(result);

        Assert.assertTrue(violations.size() == 1);
        Assert.assertTrue(violations.get(0).getPropertyPaths().get(0).equals("be.wallonie.ddd.kernel.common.entity.MyClass|getNumber"));
    }
}