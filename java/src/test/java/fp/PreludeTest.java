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

    public void testLast() {
        assertEquals((Integer) 3, last(list(1,2,3)));
        assertEquals((Integer) 1, last(list(1)));
        try {
            last(null);
            fail();
        } catch (NullPointerException e) {/* ignored */}
    }

    public void testInit() {
        assertEquals(list(1,2), init(list(1,2,3)));
        assertEquals(null, init(list(1)));
        try {
            init(null);
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
        assertEquals((Integer) 2, subtract.apply(list(1, 2, 3)));
    }

    public void testFoldLeft() {
        Function<List<Integer>, Integer> product = xs -> foldLeft((x, y) -> x * y, 1, xs);
        Function<List<Integer>, Integer> divide = xs -> foldLeft((x, y) -> x / y, 6, xs);

        assertEquals((Integer) 24, product.apply(list(1,2,3,4)));
        assertEquals((Integer) 1, product.apply(null));

        // (((6/1)/2)/3) = 1
        assertEquals((Integer) 1, divide.apply(list(1,2,3)));
    }

    public void testFoldRight1() {
        Function<List<Integer>, Integer> max = xs -> foldRight1(Integers::max, xs);
        Function<List<Integer>, Integer> subtract = xs -> foldRight1(Integers::subtract, xs);
        assertEquals((Integer) 3, max.apply(list(1, 3, 2)));
        // (1-(2-3))
        assertEquals((Integer) 2, subtract.apply(list(1,2,3)));
    }

    public void testFoldLeft1() {
        Function<List<Integer>, Integer> min = xs -> foldLeft1(Integers::min, xs);
        Function<List<Integer>, Integer> subtract = xs -> foldLeft1(Integers::subtract, xs);
        assertEquals((Integer) 1, min.apply(list(1,2,3)));
        // ((1-2)-3)
        assertEquals((Integer) (-4), subtract.apply(list(1,2,3)));
    }
}