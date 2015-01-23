package fp;

import junit.framework.TestCase;
import take2.Prelude;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

import static fp.List.empty;
import static fp.List.list;
import static fp.Lists.*;
import static fp.Pair.*;

public class ListsTest extends TestCase {
    public void testIsSame() {
        assertTrue(isSame(list(1,2), list(1,2)));
        assertFalse(isSame(list(1, 2), list(1, 2, 3)));
        assertTrue(isSame(list(), list()));
        assertFalse(isSame(list(), list(1)));
    }

    public void testConcat() {
        assertEquals(list(1,2,3), Lists.concat(list(1,2), list(3)));
    }

    public void testLength() {
        assertEquals(3, (int) length(list(1,2,3)));
        assertEquals(0, (int) length(empty()));
    }

    public void testMerge() {
        assertEquals(list(1,2,3), merge(list(1,2), list(3)));
        assertEquals(list(1,1,1,2,3,5), merge(list(1,1,2), list(1,3,5)));
    }

    public void testTake() {
        assertEquals(list(2,3), take(2, list(2,3,4)));
        assertEquals(list(2,3,4), take(3, list(2,3,4)));
        assertEquals(empty(), take(3, empty()));
    }

    public void testDrop() {
        assertEquals(list(2,3), drop(1, list(1, 2, 3)));
        assertEquals(empty(), drop(3, list(1,2,3)));
        assertEquals(empty(), drop(3, empty()));
    }

    public void testHalve() {
        assertEquals(pair(list(1, 2), list(3, 4)), halve(list(1,2,3,4)));
        assertEquals(pair(list(1, 2), list(3, 4, 5)), halve(list(1,2,3,4,5)));
        assertEquals(pair(empty(), list(1)), halve(list(1)));
        assertEquals(pair(empty(), empty()), halve(empty()));
    }

    public void testMsort() {
        assertEquals(list(1,2,3), msort(list(1,2,3)));
        assertEquals(list(1,2,3), msort(list(3,2,1)));
        assertEquals(list(1,2,3), msort(list(1,3,2)));
    }

    public void testQsort() {
        assertEquals(list(1,2,3), qsort(list(1, 2, 3)));
        assertEquals(list(1,2,3), qsort(list(3, 2, 1)));
        assertEquals(list(1,2,3), qsort(list(1, 3, 2)));
    }

    public void testFilter() {
        assertEquals(empty(), filter(x -> x > 5, list(1,2,3)));
        assertEquals(list(1,2,3), filter(x -> x < 5, list(1,2,3)));
        assertEquals(list(1,3), filter(x -> x != 2, list(1,2,3)));
    }

    public void testMap() {
        assertEquals(list(2,3,4), map(x -> x+1, list(1, 2, 3)));
        assertEquals(list("1","2","3"), map(Object::toString, list(1,2,3)));
    }

    public void testReverse() {
        assertEquals(list(3,2,1), reverse(list(1,2,3)));
        assertEquals(empty(), empty());
        assertEquals(list(1,2,1), reverse(list(1,2,1)));
    }

    public void testCurry() {
        BinaryOperator<Integer> plus = (x,y) -> x+y;
        Function<Integer, Function<Integer, Integer>> curriedPlus = curry(plus);
        assertEquals(5, (int) curriedPlus.apply(2).apply(3));
        assertEquals(2.0, uncurry(curry((Double x, Double y) -> x / y)).apply(4.0, 2.0));
    }

    public void testReducers() {
        assertEquals(10, (int) sum(list(1,2,3,4)));
        assertEquals(24, (int) product(list(2,3,4)));
    }

    public void testTakeWhile() {
        assertEquals(list(1,2,3), takeWhile(x -> x <= 3, list(1,2,3,4,5)));
        assertEquals(list(1,2,3), takeWhile(x -> x <= 3, list(1,2,3)));
        assertEquals(empty(), takeWhile(x -> x > 10, list(1,2,3)));
    }

    public void testDropWhile() {
        assertEquals(list(4,5), dropWhile(x -> x <= 3, list(1,2,3,4,5)));
        assertEquals(empty(), dropWhile(x -> x <= 3, list(1,2,3)));
        assertEquals(list(1,2,3), dropWhile(x -> x > 10, list(1,2,3)));
    }

    public void testBaseConversions() {
        assertEquals(list(1,2,3), int2dec(123));
        assertEquals(123, (int) dec2int(list(1,2,3)));
    }

    public void testPartition() {
        assertEquals(list(list(1), list(2), list(3)), partition(1, list(1,2,3)));
        assertEquals(list(list(1,2), list(3)), partition(2, list(1,2,3)));
        assertEquals(list(list(1,2,3)), partition(3, list(1,2,3)));
    }

    public void testFlatten() {
        assertEquals(list(1,2,3), flatten(list(list(1), list(2), list(3))));
        assertEquals(list(1,1,2,2,3,3), flatten(list(list(1,1), list(2,2), list(3,3))));
    }

    public void testZip() {
        assertEquals(list(pair(1,4), pair(2,5), pair(3,6)), zip(list(1, 2, 3), list(4, 5, 6)));
    }

    public void testPairs() {
        assertEquals(list(pair(1,2), pair(2,3)), pairs(list(1,2,3)));
        assertEquals(list(pair(1, 2)), pairs(list(1,2)));
        assertEquals(empty(), pairs(list(1)));
    }

    public void testAll() {
        Predicate<Integer> isEven = x -> x % 2 == 0;
        assertTrue(all(isEven, list(2, 4, 6)));
        assertFalse(all(isEven, list(1, 4, 6)));
    }

    public void testIsSorted() {
        assertTrue(isSorted(list(1, 2, 3)));
        assertFalse(isSorted(list(2, 1, 3)));
    }

    public void testLess() {
        assertTrue(lt(1, 2));
        assertFalse(lt(1, 1));
        assertFalse(lt(2, 1));
    }

    public void testPartitionBy() {
        assertEquals(pair(list(1,3), list(2,4)), partitionBy(x -> x % 2 != 0, list(1,2,3,4)));
        assertEquals(pair(list(1,2,3), empty()), partitionBy(x -> x  < 5, list(1,2,3)));
        assertEquals(pair(empty(), list(1,2,3)), partitionBy(x -> x  > 5, list(1,2,3)));
    }

    public void testCompose() {
        Function<Integer, Integer> f = compose(x -> x*2, x -> x+1);
        Function<Integer, Integer> g = compose(x -> x-1, x -> x+1);
        Function<Integer, Integer> h = compose(x -> x, f);
        assertEquals(4, (int) f.apply(1));
        assertEquals(1, (int) g.apply(1));
        assertEquals(5, (int) g.apply(5));
    }

    public void testBla() {
        System.out.println(Prelude.extend(take2.List.list(1,2,3), take2.List.list(4,5,6)));
    }
}