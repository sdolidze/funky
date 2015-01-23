package take2;

import static take2.List.cons;
import static take2.List.list;
import static take2.Pair.pair;

/**
 * Created by sandro on 1/10/15.
 */
public class Combinatorics {
    public static<T> List<List<T>> powerSet(List<T> xs) {
        return null;
    }

    public static<T> List<List<T>> permutations(List<T> xs) {
        return null;
    }

    public static<T> List<List<T>> choices(List<T> xs) {
        return null;
    }

    private static<T> List<T> concat(List<T> xs, List<T> ys) {
        return xs == null ? ys : cons(xs.head, concat(xs.tail, ys));
    }

    public static<A,B> List<Pair<A,B>> join(A y, List<B> xs) {
        return xs == null ? null : cons(pair(y, xs.head), join(y, xs.tail));
    }

    public static<A,B> List<Pair<A,B>> cartesianProduct(List<A> xs, List<B> ys) {
        // can be done with concat . map
        // make this even generic? take [[a]] -> [[a]] transpose it
        return xs == null ? null : concat(join(xs.head, ys), cartesianProduct(xs.tail, ys));
    }

    public static<A,B> List<Pair<A,B>> cartesianProductIter(List<A> xs, List<B> ys) {
        List<Pair<A,B>> acc = null;
        for (A x: xs) {
            for (B y: ys) {
                acc = cons(pair(x,y), acc);
            }
        }
        return acc;
    }

    public static int binomial(int n, int k) {
        if (k == 0 || n == k) {
            return 1;
        }
        return binomial(n-1, k-1) + binomial(n-1, k);
    }

    public static int binomialTailRec(int acc, int n, int k) {
        if (k == 0 || n == k) {
            return acc;
        }
        return binomialTailRec(acc * (n/k),n-1, k-1);
    }

    public static void main(String[] args) {
        System.out.println(cartesianProduct(list('a', 'b', 'c'), list(1,2,3)));
    }
}
