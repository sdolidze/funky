package fp;

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
        return Prelude.foldLeft((x, y) -> x + y, 0, xs);
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
        return Prelude.foldRight((x, y) -> x + y, 0, xs);
    }

    public static Integer product(List<Integer> xs) {
        return Prelude.foldLeft((x, y) -> x * y, 1, xs);
    }
}
