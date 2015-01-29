package fp;

import java.util.function.Consumer;

/**
 * Created by sandro on 1/10/15.
 */
public class FactFib {
    public static int factRec(int n) {
        return n == 0 ? 1 : n * factRec(n-1);
    }

    private static int factTailRec(int acc, int n) {
        return n == 0 ? acc : factTailRec(acc*n, n-1);
    }

    public static void factTailRecCont(int acc, int n, Consumer<Integer> cont) {
        if (n == 0) {
            cont.accept(acc);
        } else {
            factTailRecCont(acc * n, n - 1, cont);
        }
    }

    public static int factTailRec(int n) {
        return factTailRec(1, n);
    }

    public static int factIter(int n) {
        int acc = 1;
        for (; n>0; n--) {
            acc = acc*n;
        }
        return acc;
    }

    public static int fibRec(int n) {
        return n <= 1 ? n : fibRec(n-1) + fibRec(n-2);
    }

    private static int fibTailRec(int cur, int nxt, int n) {
        return n == 0 ? cur : fibTailRec(nxt, cur+nxt, n-1);
    }

    private static void fibTailRecCont(int cur, int nxt, int n, Consumer<Integer> cont) {
        if (n == 0) {
            cont.accept(cur);
        } else {
            fibTailRecCont(nxt, cur + nxt, n - 1, cont);
        }
    }

    public static int fibTailRec(int n) {
        return fibTailRec(0, 1, n);
    }

    public static int fibIter(int n) {
        int cur = 0;
        int nxt = 1;
        for (; n>0; n--) {
            int tmp = cur;
            cur = nxt;
            nxt = tmp+nxt;
        }
        return cur;
    }

    public static void main(String[] args) {
        // who knew callback hell was also possible in java :)
        factTailRecCont(1, 5, fact -> {
            fibTailRecCont(0, 1, 5, fib -> {
                System.out.printf("fact 5 is - %d", fact);
                System.out.println();
                System.out.printf("fib  5 is - %d", fib);
            });
        });
    }
}
