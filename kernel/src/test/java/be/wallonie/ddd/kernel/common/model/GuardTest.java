package be.wallonie.ddd.kernel.common.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by asmolabs on 15/11/15.
 */
public class GuardTest {

    @Test
    public void testEqualsInt() throws Exception {
        Assert.assertFalse(Guard.equals(1, -1));
        Assert.assertTrue(Guard.equals(1, 1));
        Assert.assertFalse(Guard.equals(null, 1));
        Assert.assertFalse(Guard.equals(1, null));
    }

    @Test
    public void testSmallerThanInt() throws Exception {
        Assert.assertTrue(Guard.smallerThan(-1, 1));
        Assert.assertFalse(Guard.smallerThan(1, -1));
        Assert.assertFalse(Guard.smallerThan(null, 1));
        Assert.assertFalse(Guard.smallerThan(1, null));
    }

    @Test
    public void testGreaterThanInt() throws Exception {
        Assert.assertFalse(Guard.greaterThan(-1, 1));
        Assert.assertTrue(Guard.greaterThan(1, -1));
        Assert.assertFalse(Guard.greaterThan(null, 1));
        Assert.assertFalse(Guard.greaterThan(1, null));
    }

    @Test
    public void testSmallerOrEqualThanInt() throws Exception {
        Assert.assertTrue(Guard.smallerOrEqualThan(-1, 1));
        Assert.assertFalse(Guard.smallerOrEqualThan(1, -1));
        Assert.assertTrue(Guard.smallerOrEqualThan(1, 1));
        Assert.assertFalse(Guard.smallerOrEqualThan(null, 1));
        Assert.assertFalse(Guard.smallerOrEqualThan(1, null));
    }

    @Test
    public void testGreaterOrEqualThanInt() throws Exception {
        Assert.assertFalse(Guard.greaterOrEqualThan(-1, 1));
        Assert.assertTrue(Guard.greaterOrEqualThan(1, -1));
        Assert.assertTrue(Guard.greaterOrEqualThan(1, 1));
        Assert.assertFalse(Guard.greaterOrEqualThan(null, 1));
        Assert.assertFalse(Guard.greaterOrEqualThan(1, null));
    }

    @Test
    public void testBetweenNumber() throws Exception {
        Assert.assertTrue(Guard.betweenNumber(10, 9, 11));
        Assert.assertTrue(Guard.betweenNumber(10, 10, 10));
        Assert.assertFalse(Guard.betweenNumber(10, 11, 12));
    }

    @Test
    public void testDomain() throws Exception {
        Assert.assertTrue(Guard.domain(1, Arrays.asList(1)));
        Assert.assertTrue(Guard.domain(1, Arrays.asList(1, 2, 3)));
        Assert.assertFalse(Guard.domain(null, Arrays.asList(1, 2, 3)));
        Assert.assertFalse(Guard.domain(1, Arrays.asList()));
        Assert.assertFalse(Guard.domain(10, Arrays.asList(1, 2, 3)));
        Assert.assertFalse(Guard.domain(null, Arrays.asList(1, 2, 3)));
    }

    @Test
    public void testEqualsDouble() throws Exception {
        Assert.assertFalse(Guard.equals(1.00, -1.01));
        Assert.assertTrue(Guard.equals(1.10, 1.10));
        Assert.assertFalse(Guard.equals(null, 1.10));
        Assert.assertFalse(Guard.equals(1.10, null));
    }

    @Test
    public void testSmallerThanDouble() throws Exception {
        Assert.assertTrue(Guard.smallerThan(-1.10, 1.10));
        Assert.assertFalse(Guard.smallerThan(1.10, -1.10));
        Assert.assertFalse(Guard.smallerThan(null, 1.10));
        Assert.assertFalse(Guard.smallerThan(1.10, null));
    }

    @Test
    public void testGreaterThanDouble() throws Exception {
        Assert.assertFalse(Guard.greaterThan(-1.10, 1.10));
        Assert.assertTrue(Guard.greaterThan(1.10, -1.10));
        Assert.assertFalse(Guard.greaterThan(null, 1.10));
        Assert.assertFalse(Guard.greaterThan(1.10, null));
    }

    @Test
    public void testSmallerOrEqualThanDouble() throws Exception {
        Assert.assertTrue(Guard.smallerOrEqualThan(-1.10, 1.10));
        Assert.assertFalse(Guard.smallerOrEqualThan(1.10, -1.10));
        Assert.assertTrue(Guard.smallerOrEqualThan(1.10, 1.10));
        Assert.assertFalse(Guard.smallerOrEqualThan(null, 1.10));
        Assert.assertFalse(Guard.smallerOrEqualThan(1.10, null));
    }

    @Test
    public void testGreaterOrEqualThanDouble() throws Exception {
        Assert.assertFalse(Guard.greaterOrEqualThan(-1.10, 1.10));
        Assert.assertTrue(Guard.greaterOrEqualThan(1.10, -1.10));
        Assert.assertTrue(Guard.greaterOrEqualThan(1.10, 1.10));
        Assert.assertFalse(Guard.greaterOrEqualThan(null, 1.10));
        Assert.assertFalse(Guard.greaterOrEqualThan(1.10, null));
    }

    @Test
    public void testBetweenNumberDouble() throws Exception {
        Assert.assertTrue(Guard.betweenNumber(10.1, 9.1, 11.1));
        Assert.assertTrue(Guard.betweenNumber(10.1, 10.1, 10.1));
        Assert.assertFalse(Guard.betweenNumber(10.1, 11.1, 12.1));
    }

    @Test
    public void testDomainDouble() throws Exception {
        Assert.assertTrue(Guard.domain(1.1, Arrays.asList(1.1)));
        Assert.assertTrue(Guard.domain(1.1, Arrays.asList(1.1, 2.2, 3.3)));
        Assert.assertFalse(Guard.domain(null, Arrays.asList(1.1, 2.2, 3.3)));
        Assert.assertFalse(Guard.domain(1.1, Arrays.asList()));
        Assert.assertFalse(Guard.domain(10.10, Arrays.asList(1.1, 2.2, 3.3)));
        Assert.assertFalse(Guard.domain(null, Arrays.asList(1.1, 2.2, 3.3)));
    }

    @Test
    public void testIsFormat() throws Exception {
        Assert.assertTrue(Guard.isFormat(null, 1, 1, true));
        Assert.assertFalse(Guard.isFormat(null, 1, 1, false));
    }

    @Test
    public void testIsFormat1() throws Exception {
        Assert.assertTrue(Guard.isFormat(1.1, 1, 1));
        Assert.assertFalse(Guard.isFormat(1.1, 1, 0));
        Assert.assertFalse(Guard.isFormat(1.1, 0, 1));

        Assert.assertTrue(Guard.isFormat(99.99, 2, 2));
        Assert.assertTrue(Guard.isFormat(9999.999, 4, 3));

        Assert.assertFalse(Guard.isFormat(100.00, 2, 2));
        Assert.assertFalse(Guard.isFormat(10000.111, 4, 3));
        Assert.assertFalse(Guard.isFormat(99.999, 2, 2));
        Assert.assertFalse(Guard.isFormat(9999.9999, 4, 3));
    }

    @Test
    public void testBefore() throws Exception {
        Assert.assertTrue(Guard.before(new Date(2010, 01, 01), new Date(2011, 01, 01)));
        Assert.assertFalse(Guard.before(new Date(2010, 01, 01), new Date(2010, 01, 01)));
        Assert.assertFalse(Guard.before(new Date(2011, 01, 01), new Date(2010, 01, 01)));
        Assert.assertFalse(Guard.before(new Date(2011, 01, 01), null));
        Assert.assertFalse(Guard.before(null, new Date(2010, 01, 01)));
    }

    @Test
    public void testAfter() throws Exception {
        Assert.assertFalse(Guard.after(new Date(2010, 01, 01), new Date(2011, 01, 01)));
        Assert.assertFalse(Guard.after(new Date(2010, 01, 01), new Date(2010, 01, 01)));
        Assert.assertTrue(Guard.after(new Date(2011, 01, 01), new Date(2010, 01, 01)));
        Assert.assertFalse(Guard.after(new Date(2011, 01, 01), null));
        Assert.assertFalse(Guard.after(null, new Date(2010, 01, 01)));
    }

    @Test
    public void testBeforeOrEqual() throws Exception {
        Assert.assertTrue(Guard.beforeOrEqual(new Date(2010, 01, 01), new Date(2011, 01, 01)));
        Assert.assertTrue(Guard.beforeOrEqual(new Date(2010, 01, 01), new Date(2010, 01, 01)));
        Assert.assertFalse(Guard.beforeOrEqual(new Date(2011, 01, 01), new Date(2010, 01, 01)));
        Assert.assertFalse(Guard.beforeOrEqual(new Date(2011, 01, 01), null));
        Assert.assertFalse(Guard.beforeOrEqual(null, new Date(2010, 01, 01)));
    }

    @Test
    public void testAfterOrEqual() throws Exception {
        Assert.assertFalse(Guard.afterOrEqual(new Date(2010, 01, 01), new Date(2011, 01, 01)));
        Assert.assertTrue(Guard.afterOrEqual(new Date(2010, 01, 01), new Date(2010, 01, 01)));
        Assert.assertTrue(Guard.afterOrEqual(new Date(2011, 01, 01), new Date(2010, 01, 01)));
        Assert.assertFalse(Guard.afterOrEqual(new Date(2011, 01, 01), null));
        Assert.assertFalse(Guard.afterOrEqual(null, new Date(2010, 01, 01)));
    }

    @Test
    public void testEqualsDate() throws Exception {
        Assert.assertFalse(Guard.equals(new Date(2010, 01, 01), new Date(2011, 01, 01)));
        Assert.assertTrue(Guard.equals(new Date(2010, 01, 01), new Date(2010, 01, 01)));
        Assert.assertFalse(Guard.equals(new Date(2011, 01, 01), new Date(2010, 01, 01)));
        Assert.assertFalse(Guard.equals(new Date(2011, 01, 01), null));
        Assert.assertFalse(Guard.equals(null, new Date(2010, 01, 01)));
    }

    @Test
    public void testNullOrEmpty() throws Exception {
        Assert.assertTrue(Guard.nullOrEmpty(null));
        Assert.assertTrue(Guard.nullOrEmpty(""));
        Assert.assertFalse(Guard.nullOrEmpty("not null"));
    }

    @Test
    public void testEqualsString() throws Exception {

    }

    @Test
    public void testDigitOnly() throws Exception {
        Assert.assertTrue(Guard.digitOnly("0123456789"));
        Assert.assertFalse(Guard.digitOnly("1 2"));
        Assert.assertFalse(Guard.digitOnly("A1"));
        Assert.assertFalse(Guard.digitOnly("A1"));
        Assert.assertFalse(Guard.digitOnly("1$"));
    }

    @Test
    public void testBetweenNumberStringInt() throws Exception {
        Assert.assertTrue(Guard.betweenNumberString("10", 9, 11));
        Assert.assertTrue(Guard.betweenNumberString("10", 10, 10));
        Assert.assertFalse(Guard.betweenNumberString("10", 11, 12));
        Assert.assertFalse(Guard.betweenNumberString("A", 11, 12));
        Assert.assertFalse(Guard.betweenNumberString(null, 11, 12));
    }

    @Test
    public void testBetweenNumberStringDouble() throws Exception {
        Assert.assertTrue(Guard.betweenNumberString("10.1", 9.9, 11.1));
        Assert.assertTrue(Guard.betweenNumberString("10.1", 10.1, 10.1));
        Assert.assertFalse(Guard.betweenNumberString("10.1", 11.0, 12.0));
        Assert.assertFalse(Guard.betweenNumberString("A", 11, 12));
        Assert.assertFalse(Guard.betweenNumberString(null, 11, 12));
    }

    @Test
    public void testLength() throws Exception {
        Assert.assertTrue(Guard.length("0123456789", 10));
        Assert.assertFalse(Guard.length("0123456789", 9));
        Assert.assertTrue(Guard.length(" 0123456789 ", 10));
        Assert.assertFalse(Guard.length(" 0123 456789 ", 10));
    }

    @Test
    public void testDomainString() throws Exception {
        Assert.assertTrue(Guard.domain("1", Arrays.asList("1")));
        Assert.assertTrue(Guard.domain("1.1", Arrays.asList("1.1", "2.2", "3.3")));
        Assert.assertFalse(Guard.domain(null, Arrays.asList("1.1", "2.2", "3.3")));
        Assert.assertFalse(Guard.domain("1.1", Arrays.asList()));
        Assert.assertFalse(Guard.domain("10.10", Arrays.asList("1.1", "2.2", "3.3")));
        Assert.assertFalse(Guard.domain(null, Arrays.asList("1.1", "2.2", "3.3")));
    }

    @Test
    public void testPhoneNumber() throws Exception {
        Assert.assertTrue(Guard.phoneNumber("0495786754"));
        Assert.assertTrue(Guard.phoneNumber("071788967"));
        Assert.assertTrue(Guard.phoneNumber("+32495786754"));
        Assert.assertFalse(Guard.phoneNumber("ae"));
    }

    @Test
    public void testEmail() throws Exception {
        Assert.assertFalse(Guard.email("a@y.z"));
        Assert.assertTrue(Guard.email("patrick.degeynst@spw.be"));
        Assert.assertTrue(Guard.email("x@spw.be"));
        Assert.assertFalse(Guard.email("patrick.degeynst@spw"));
        Assert.assertFalse(Guard.email("@spw.be"));
        Assert.assertFalse(Guard.email("patrick.degeynst@"));
        Assert.assertFalse(Guard.email("@"));
        Assert.assertFalse(Guard.email("anyValue"));
    }

    @Test
    public void testNbrOfElements() throws Exception {
        Assert.assertTrue(Guard.nbrOfElements(Arrays.asList("1", "2", "3"), 3, 3, false));
        Assert.assertTrue(Guard.nbrOfElements(Arrays.asList("1", "2", "3"), 2, 4, false));
        Assert.assertFalse(Guard.nbrOfElements(Arrays.asList("1", "2", "3"), 1, 2, false));
        Assert.assertFalse(Guard.nbrOfElements(Arrays.asList("1", "2", "3"), 4, 10, false));
        Assert.assertFalse(Guard.nbrOfElements(Arrays.asList("1", 2, "3"), 4, 10, false));
        Assert.assertFalse(Guard.nbrOfElements(Arrays.asList(1, 2, 3), 4, 10, false));

        Assert.assertTrue(Guard.nbrOfElements(Arrays.asList(null, null, null), 3, 3, true));
    }

    @Test
    public void testContains() throws Exception {

    }
}