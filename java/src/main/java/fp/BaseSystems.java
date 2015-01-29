package fp;

import static fp.Prelude.append;
import static fp.Prelude.foldLeft;

public class BaseSystems {
    public static List<Integer> int2dec(int n) {
        if (n == 0) {
            return null;
        }
        return append(n % 10, int2dec(n / 10));
    }

    public static Integer dec2int(List<Integer> xs) {
        return foldLeft((a, x) -> 10 * a + x, 0, xs);
    }
}
