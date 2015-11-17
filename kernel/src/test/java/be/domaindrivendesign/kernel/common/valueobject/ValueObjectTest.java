package be.domaindrivendesign.kernel.common.valueobject;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by eddie on 17/11/15.
 */
public class ValueObjectTest {
    @Test
    public void testValueObjectEquality() throws Exception {
        MyValueObject01 v1a = new MyValueObject01(1,4);
        MyValueObject01 v1b = new MyValueObject01(1,4);
        MyValueObject01 v1c = new MyValueObject01(1,5);
        MyValueObject02 v2 = new MyValueObject02(1,4);

        // Equals to itself
        assertTrue(v1a.equals(v1a));

        // Same type, same values
        assertTrue(v1a.equals(v1b));

        // Same type, different values
        assertTrue(!v1a.equals(v1c));

        // Different type, same values
        assertTrue(!v1a.equals(v2));

        // null obect
        assertTrue(!v1a.equals(null));
    }

    protected class MyValueObject01 extends ValueObjectImpl<MyValueObject01> {

        public MyValueObject01(int value01, int value02) {
            setValue01(value01);
            setValue02(value02);
        }

        public int getValue01() {
            return value01;
        }

        protected void setValue01(int value01) {
            this.value01 = value01;
        }

        public int getValue02() {
            return value02;
        }

        protected void setValue02(int value02) {
            this.value02 = value02;
        }

        private int value01;
        private int value02;
    }

    protected class MyValueObject02 extends ValueObjectImpl<MyValueObject01> {

        public MyValueObject02(int value01, int value02) {
            setValue01(value01);
            setValue02(value02);
        }

        public int getValue01() {
            return value01;
        }

        protected void setValue01(int value01) {
            this.value01 = value01;
        }

        public int getValue02() {
            return value02;
        }

        protected void setValue02(int value02) {
            this.value02 = value02;
        }

        private int value01;
        private int value02;
    }
}
