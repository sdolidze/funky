package fp;

import iterable.Merge;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BinaryOperator;

import static fp.List.list;
import static fp.List.listIter;

public class MergeSortedIteratorsTest extends TestCase {

    public void testListIter() {
        assertEquals(list(1,2,3), listIter(list(1,2,3)));
        assertEquals(null, listIter(new ArrayList<>()));
    }

    public void testMerge() throws Exception {
        BinaryOperator<Iterator<Integer>> merge = Merge::new;
//        assertIterablesEquals(list(1, 2, 3, 4, 5), merge.apply(list(1, 2, 3), list(4, 5)));
//        assertIterablesEquals(list(4, 5), merge.apply(empty(), list(4, 5)));
//        assertIterablesEquals(list(1, 2), merge.apply(list(1, 2), empty()));
//        assertIterablesEquals(list(1, 2, 3, 4, 5), merge.apply(list(1, 4, 5), list(2, 3)));
    }

    private<T> void assertIterablesEquals(Iterable<T> left, Iterable<T> right) {
        assertEquals(listIter(left), listIter(right));
    }

    private static<T> Iterable<T> empty() {
        return new ArrayList<T>();
    }
}