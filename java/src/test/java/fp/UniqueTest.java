package fp;

import junit.framework.TestCase;

import java.util.function.BiFunction;
import java.util.function.Function;

import static fp.List.list;

public class UniqueTest extends TestCase {

    public void testContains() throws Exception {
        assertTrue(Unique.contains(1, list(1,2)));
        assertTrue(Unique.contains(2, list(1,2)));
        assertFalse(Unique.contains(3, list(1, 2)));
    }

    public void testUnique() throws Exception {
        assertEquals(list(1, 2, 3), Unique.unique(list(1,1,2,2,3,3)));
    }

    public void testUniqueInPlace() {
        assertEquals(list(1,2,3), Unique.uniqueInPlace(list(1, 2, 2, 3, 3, 3, 1, 2, 3)));
    }

    public void testUniqueIterInPlace() {
        Function<List<Integer>, List<Integer>> unique = Unique::uniqueTailRecInPlace;
        assertEquals(null       , unique.apply(null));
        assertEquals(list(1)    , unique.apply(list(1)));
        assertEquals(list(1)    , unique.apply(list(1,1)));
        assertEquals(list(1,2)  , unique.apply(list(1,1,2,2,1,2)));
        assertEquals(list(1,2)  , unique.apply(list(1,2,1))); // test element order [1,2] if first [2,1] if last
        assertEquals(list(1,2,3), unique.apply(list(1,2,3)));
    }

    public void testRemove() {
        BiFunction<Integer, List<Integer>, List<Integer>> remove = Unique::removeRecInPlace;
        assertEquals(null     , remove.apply(1, null));
        assertEquals(null     , remove.apply(1, list(1)));
        assertEquals(list(2)  , remove.apply(1, list(1,2)));
        assertEquals(null     , remove.apply(1, list(1,1,1)));
        assertEquals(list(1,2), remove.apply(3, list(1,2,3)));
        assertEquals(list(2,3), remove.apply(1, list(1,2,3)));
        assertEquals(list(1,3), remove.apply(2, list(1,2,3)));
    }
}