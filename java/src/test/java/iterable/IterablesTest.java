package iterable;

import fp.Integers;
import junit.framework.TestCase;

import static iterable.Iterables.*;


public class IterablesTest extends TestCase {
    public static void testDrop() {
        assertEquals(list(3,4), toList(drop(2, list(1, 2, 3, 4))));
        assertEquals(list(), toList(drop(2, empty())));
    }

    public static void testDropWhile() {
        assertEquals(list(), toList(dropWhile(x -> x < 5, list(1,2))));
        assertEquals(list(6,7), toList(dropWhile(x -> x < 5, list(6,7))));
        assertEquals(list(5,6), toList(dropWhile(x -> x < 5, list(4,5,6))));
    }

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

    public static void testLead() {
        assertEquals(list(1), toList(lead(1, list())));
        assertEquals(list(1,2), toList(lead(1, list(2))));
    }

    public static void testFollow() {
        assertEquals(list(1), toList(follow(1, list())));
        assertEquals(list(1,2), toList(follow(2, list(1))));
    }

    public static void testEmpty() {
        assertEquals(list(), toList(empty()));
        try {
            empty().iterator().next();
            fail();
        } catch (Exception e) {/* ignored */}
    }
}