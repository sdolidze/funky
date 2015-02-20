package fp;

/**
 * Created by sandro on 2/20/15.
 */
public class Integers {
    public static Integer sum(List<Integer> xs) {
        return Prelude.foldLeft((x, y) -> x + y, 0, xs);
    }

    public static Integer product(List<Integer> xs) {
        return Prelude.foldLeft((x, y) -> x * y, 1, xs);
    }

}
