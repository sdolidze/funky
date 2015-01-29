package fp;

import junit.framework.TestCase;

import java.util.function.BinaryOperator;

import static fp.List.list;

public class MergeTest extends TestCase {

    public void testMerge() throws Exception {
        BinaryOperator<List<Integer>> merge = Merge::mergeTailRec;
        assertEquals(null, merge.apply(null, null));
        assertEquals(list(1,2,3), merge.apply(list(1, 2), list(3)));
        assertEquals(list(1,2,3), merge.apply(list(1,2,3), null));
        assertEquals(list(1,2,3), merge.apply(null, list(1,2,3)));
        assertEquals(list(1,2,3,4,5), merge.apply(list(1,3,4), list(2,5)));
    }
}