package fp;

/**
 * Created by sandro on 2/21/15.
 */
public class OrganizeMeLater {
    public static<A> boolean containsList(List<A> xs, List<A> ys) {
        // todo: this is probably wrong: test containsList([1,2], [1,1,2])
        // thanks for the idea to @safareli
        // damn, I really don't know how to use English prepositions :)
        if (xs == null) {
            return true;
        } else if (ys == null) {
            return false;
        } else {
            return xs.head.equals(ys.head) ? containsList(xs.tail, ys.tail) : containsList(xs, ys.tail);
        }
    }

    public static<A> boolean isPrefix(List<A> xs, List<A> ys) {
        // hmm, is this write? write unit tests to be sure
        if (xs == null) {
            return true;
        } else if (ys == null) {
            return false;
        } else {
            return xs.head.equals(ys.head) && isPrefix(xs.tail, ys.tail);
        }
    }
}
