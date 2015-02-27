package iterable;

/**
 * Created by sandro on 2/26/15.
 */
public interface Enumerator<T> {
    public boolean hasNext();
    public T current();
    public void next();
}
