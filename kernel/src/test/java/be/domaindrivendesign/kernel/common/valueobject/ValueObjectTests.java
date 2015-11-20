package be.domaindrivendesign.kernel.common.valueobject;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by eddie on 17/11/15.
 */

public class ValueObjectTests {
    @SuppressWarnings({"EqualsBetweenInconvertibleTypes", "EqualsWithItself", "ObjectEqualsNull"})
    @Test
    public void testValueObjectEquality() throws Exception {
        MyValueObject01 v1a = new MyValueObject01(1, 4);
        MyValueObject01 v1b = new MyValueObject01(1, 4);
        MyValueObject01 v1c = new MyValueObject01(1, 5);
        MyStringValueObject v2a = new MyStringValueObject("1", "4");
        MyStringValueObject v2b = new MyStringValueObject("1", "4");

        // Equals to itself
        assertTrue(v1a.equals(v1a));
        assertTrue(v2a.equals(v2a));

        // Same type, same values
        assertTrue(v1a.equals(v1b));
        assertTrue(v2a.equals(v2b));

        // Same type, different values
        assertFalse(v1a.equals(v1c));

        // Different type, same values
        assertFalse(v1a.equals(v2a));

        // null object
        //noinspection ObjectEqualsNull
        assertFalse(v1a.equals(null));
    }

    @Test
    public void testValueObjectEntirePropertiesAreNull() throws Exception {

        MyValueObject01 vo1NotNull = new MyValueObject01(1, 2);
        MyStringValueObject notNull = new MyStringValueObject("3", "4");
        MyStringValueObject partiallyNull1 = new MyStringValueObject(null, "4");
        MyStringValueObject partiallyNull2 = new MyStringValueObject("1", null);
        MyMixedValueObject partiallyNull3 = new MyMixedValueObject(1, null);
        MyMixedValueObject partiallyNull4 = new MyMixedValueObject(null, "toto");
        MyStringValueObject allNull = new MyStringValueObject(null, null);
        MyMixedValueObject mixedAllNull = new MyMixedValueObject(null, null);

        // Not all values are null
        assertFalse(vo1NotNull.areEntirePropertyNull());
        assertFalse(notNull.areEntirePropertyNull());
        assertFalse(partiallyNull1.areEntirePropertyNull());
        assertFalse(partiallyNull2.areEntirePropertyNull());
        assertFalse(partiallyNull3.areEntirePropertyNull());
        assertFalse(partiallyNull4.areEntirePropertyNull());

        // All values are null
        assertTrue(allNull.areEntirePropertyNull());
        assertTrue(mixedAllNull.areEntirePropertyNull());
    }

    @SuppressWarnings("unused")
    final class MyValueObject01 extends ValueObject {

        private final int value01;
        private final int value02;

        @SuppressWarnings("SameParameterValue")
        public MyValueObject01(int value01, int value02) {
            this.value01 = value01;
            this.value02 = value02;
        }
    }

    @SuppressWarnings("unused")
    final class MyStringValueObject extends ValueObject {

        private final String value1;
        private final String value2;

        public MyStringValueObject(String value1, String value2) {
            this.value1 = value1;
            this.value2 = value2;
        }
    }

    @SuppressWarnings("unused")
    final class MyMixedValueObject extends ValueObject {

        private final Integer value1;
        private final String value2;

        public MyMixedValueObject(Integer value1, String value2) {
            this.value1 = value1;
            this.value2 = value2;
        }
    }
}
