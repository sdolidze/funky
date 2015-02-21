package fp;

import junit.framework.TestCase;

import java.util.function.Function;

import static fp.Folding.*;
import static fp.List.list;

public class FoldingTest extends TestCase {
    public void testScanRightRec() {
        Function<List<Integer>, List<Integer>> sum = xs -> scanRightRec(Integers::add, 0, xs);
        Function<List<Integer>, List<Integer>> subtract = xs -> scanRightRec(Integers::subtract, 0, xs);
        assertEquals(list(6,5,3,0), sum.apply(list(1,2,3)));
        assertEquals(list(0), sum.apply(null));
        assertEquals(list(2,-1,3,0), subtract.apply(list(1,2,3)));
    }

    public void testScanLeftRec() {
        Function<List<Integer>, List<Integer>> sum = xs -> scanLeftRec(Integers::add, 0, xs);
        Function<List<Integer>, List<Integer>> subtract = xs -> scanLeftRec(Integers::subtract, 0, xs);
        assertEquals(list(0,1,3,6), sum.apply(list(1,2,3)));
        assertEquals(list(0), sum.apply(null));
        assertEquals(list(0,-1,-3,-6), subtract.apply(list(1,2,3)));
    }
}