package fp;

import junit.framework.TestCase;

import java.util.function.Function;

import static fp.List.list;
import static fp.Prelude.*;

public class PreludeTest extends TestCase {
    public void testHead() {
        assertEquals((Integer) 1, head(list(1,2,3)));
        assertEquals((Integer) 1, head(list(1)));
        try {
            head(null);
            fail();
        } catch (NullPointerException e) {/* ignored */}
    }

    public void testTail() {
        assertEquals(list(2,3), tail(list(1, 2, 3)));
        assertEquals(null, tail(list(1)));
        try {
            tail(null);
            fail();
        } catch (NullPointerException e) {/* ignored */}
    }

    public void testReverse() {
        assertEquals(list(3,2,1), reverse(list(1,2,3)));
        assertEquals(list(1,2,1), reverse(list(1,2,1)));
        assertEquals(list(1), reverse(list(1)));
        assertEquals(null, reverse(null));
    }

    public void testFoldRight() {
        Function<List<Integer>, Integer> sum = xs -> foldRight((x, y) -> x + y, 0, xs);
        Function<List<Integer>, Integer> subtract = xs -> foldRight((x, y) -> x - y, 0, xs);

        assertEquals((Integer) 10, sum.apply(list(1,2,3,4)));
        assertEquals((Integer) 0, sum.apply(null));

        // (1-(2-(3-0))) = 2
        assertEquals((Integer) 2, subtract.apply(list(1,2,3)));
    }
}