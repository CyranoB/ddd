package be.wallonie.ddd.kernel.rule.model;

import be.wallonie.ddd.kernel.common.entity.MyClass;
import be.wallonie.ddd.kernel.common.entity.MySubClass;

import static org.junit.Assert.assertTrue;

/**
 * Created by asmolabs on 14/11/15.
 */
public class RuleGuardTest {

    @org.junit.Test
    public void testEqualsInvariantOneLevel() throws Exception {
        MyClass myClass = new MyClass();
        myClass.setNumber(100);
        MySubClass mySubClass = new MySubClass();
        mySubClass.setCount(100);
        myClass.setMySubClass(mySubClass);

        boolean result = false;

        result = RuleGuard.equalsInvariant(myClass, () -> myClass.getNumber(), 100);

        assertTrue(result);
    }
}