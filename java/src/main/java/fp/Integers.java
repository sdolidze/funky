package fp;

import static fp.List.cons;
import static fp.List.list;
import static fp.Prelude.*;

/**
 * Created by sandro on 2/20/15.
 */
public class Integers {
    public static Integer min(Integer x, Integer y) {
        if (x < y) {
            return x;
        } else {
            return y;
        }
    }

    public static Integer max(Integer x, Integer y) {
        if (x > y) {
            return x;
        } else {
            return y;
        }
    }

    public static Integer add(Integer x, Integer y) {
        return x + y;
    }

    public static Integer subtract(Integer x, Integer y) {
        return x - y;
    }

    /*
     * left association => sum([1,2,3]) = (((0+1)+2)+3)
     */
    private static int sumLeft(int acc, List<Integer> xs) {
        return xs == null ? acc : sumLeft(acc + xs.head, xs.tail);
    }

    public static Integer sum(List<Integer> xs) {
        return foldLeft((x, y) -> x + y, 0, xs);
    }

    /*
     * right association => sum([1,2,3]) = (1+(2+(3+0)))
     * addition of integers is associative,
     * therefor left fold and right yield the same result
     * (division does not)
     */
    private static int sumRight(List<Integer> xs) {
        return xs == null ? 0 : xs.head + sumRight(xs.tail);
    }

    private static int sumFoldRight(List<Integer> xs) {
        return foldRight((x, y) -> x + y, 0, xs);
    }

    public static Integer product(List<Integer> xs) {
        return foldLeft((x, y) -> x * y, 1, xs);
    }

    private static List<Integer> rangeRec(Integer to, List<Integer> acc) {
        if (to == 0) {
            return acc;
        } else {
            return rangeRec(to - 1, cons(to - 1, acc));
        }
    }

    public static List<Integer> range(Integer from, Integer to) {
        List<Integer> xs = null;
        for (int i = to - 1; i >= from; i --) {
            xs = cons(i, xs);
        }
        return xs;
    }

    public static List<Integer> range(Integer to) {
        return range(0, to);
    }

    public static List<Integer> factors(Integer x) {
        return filter(y -> x % y == 0, range(1, x + 1));

    }

    public static boolean isPrime(Integer x) {
        if (x < 0) {
            throw new RuntimeException("negative number is not allowed");
        }
        return Prelude.equals(list(1, x), factors(x));
    }
}
