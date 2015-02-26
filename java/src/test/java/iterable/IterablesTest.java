package iterable;

import fp.Integers;
import junit.framework.TestCase;

import static iterable.Iterables.*;


public class IterablesTest extends TestCase {
    public static void testMap() {
        assertEquals(list("1","2"), toList(map(Integers::toString, list(1,2))));
        assertEquals(list(2,3), toList(map(Integers::inc, list(1, 2))));
    }
}