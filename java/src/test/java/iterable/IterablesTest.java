package iterable;

import fp.Integers;
import junit.framework.TestCase;

import static iterable.Iterables.*;


public class IterablesTest extends TestCase {
    public static void testMap() {
        assertEquals(list("1","2"), toList(map(Integers::toString, list(1, 2))));
        assertEquals(list(2,3), toList(map(Integers::inc, list(1, 2))));
    }

    public static void testExtend() {
        assertEquals(list(), toList(extend(list(), list())));
        assertEquals(list(3,4), toList(extend(list(), list(3,4))));
        assertEquals(list(1,2), toList(extend(list(1,2), list())));
        assertEquals(list(1,2,3,4), toList(extend(list(1,2), list(3,4))));
    }
}