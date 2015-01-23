package oop;

import java.util.ArrayList;

/**
 * Created by sandro on 1/15/15.
 */

public abstract class Abstract {
    public int bla() {
        return 5;
    }

    public abstract int getInt();
}

class Whatever {
    public Abstract whatever() {
        return new Abstract() {
            public int getInt() {
                return 5;
            }
        };
    }
}
