package fp;

import static fp.List.cons;
import static fp.List.list;

/**
 * Created by sandro on 1/12/15.
 */
public class Append {
    public static<T> List<T> appendRec(T x, List<T> xs) {
        return xs == null ? list(x) : cons(xs.head, appendRec(x, xs.tail));
    }

    public static<T> List<T> reverse(List<T> oldList) {
        List<T> newList = null;
        for (T x: oldList) {
            newList = cons(x, newList);
        }
        return newList;
    }

    public static<T> List<T> append(T x, List<T> xs) {
        return reverse(cons(x, reverse(xs)));
    }

    public static<T> List<T> last(List<T> xs) {
        List<T> prev = null;

        while (xs != null) {
            prev = xs;
            xs = xs.tail;
        }

        return prev;
    }

   private static<T> List<T> lastRec(List<T> prev, List<T> cur) {
       return cur == null ? prev : lastRec(cur, cur.tail);
   }

   public static<T> List<T> lastRec(List<T> cur) {
       return lastRec(null, cur);
   }

   public static<T> void appendIterInPlace(T x, List<T> xs) {
       last(xs).tail = list(x);
   }

   public static<T> void join(List<T> xs, List<T> ys) {
       last(xs).tail = ys;
   }

   public static<T> List<T> init(List<T> xs) {
       return xs.tail == null ? null : cons(xs.head, init(xs.tail));
   }

    public static<T> List<T> init2(List<T> xs) {
        return reverse(reverse(xs).tail);
    }

    public static<T> List<T> secondLast(List<T> xs) {
        return xs.tail.tail == null ? xs : secondLast(xs.tail);
    }

    public static<T> List<T> secondLastIter(List<T> xs) {
        return null;
    }

    public static<T> List<T> nth(int n, List<T> xs) {
        return n == 0 ? xs : nth(n-1, xs.tail);
    }

    public static<T> List<T> reverseNth(int n, List<T> xs) {
        return null;
    }

    public static<T> void init3(List<T> xs) {
        reverseNth(3, xs).tail = null;
//        secondLast(xs).tail = null;
    }

    public static void main(String[] args) {
        List<Integer> xs = list(1,2,3);
        init3(xs);
        System.out.println(xs);
    }
}
