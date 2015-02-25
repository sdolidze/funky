package reflect;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by sandro on 2/24/15.
 */
public class Reflect {

    public static void main(String[] args) {
        Map<Class, Function<Object, Integer>> map = new HashMap<Class, Function<Object, Integer>>() {{
            put(String.class, null);
        }};
    }
}
