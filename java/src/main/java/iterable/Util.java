package iterable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sandro on 2/20/15.
 */
public class Util {
    public static<T> List<T> fromIterable(Iterable<T> it) {
        List<T> xs = new ArrayList<>();
        for (T x: it) {
            xs.add(x);
        }
        return xs;
    }

    public static<T> List<T> list(T... xs) {
        // careful: result is immutable list
        return Arrays.asList(xs);
    }
}
