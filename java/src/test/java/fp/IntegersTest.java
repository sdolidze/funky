package fp;

import junit.framework.TestCase;

import static fp.Integers.*;
import static fp.List.list;

public class IntegersTest extends TestCase {
    public void testRange() {
        assertEquals(list(0,1,2), range(3));
        assertEquals(null, range(0));
        assertEquals(list(2,3,4), range(2,5));
    }

    public void testFactors() {
        assertEquals(list(0), list(0));
        assertEquals(list(1), factors(1));
        assertEquals(list(1,2,4), factors(4));
        assertEquals(list(1,3), factors(3));
    }

    public void testIsPrime() {
        try {
            isPrime(-1);
            fail();
        } catch (Exception e) {/* ignored */}
        assertFalse(isPrime(0));
        assertFalse(isPrime(1));
        assertTrue(isPrime(2));
        assertFalse(isPrime(4));
        assertTrue(isPrime(17));
        assertFalse(isPrime(20));
    }
}