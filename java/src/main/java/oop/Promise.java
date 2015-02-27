package oop;

import java.util.List;
import java.util.function.Function;

/**
 * Created by sandro on 2/27/15.
 */
public class Promise<A> {
    public static<A> Promise<A> resolve(A value) {
        return null;
    }

    public static<A> Promise<A> reject(Exception value) {
        return null;
    }

    public static<A> Promise<List<A>> all(List<Promise<A>> promises) {
        return null;
    }

    public<B> Promise<B> then(Function<A,Promise<B>> f) {
        return null;
    }

    public <B> Promise<B> catch_(Function<Exception,Promise<B>> f) {
        return null;
    }

    public <B> Promise<B> finally_(Function<A,Promise<B>> f) {
        return null;
    }

    public static void main(String[] args) {
        Promise
                .resolve("sandro")
                .then(x -> {
                    System.out.println("this will happen asynchronously");
                    return Promise.resolve(String.format("_(%s)_", x));
                })
                .then(x -> {
                    throw new RuntimeException("things got bad");
                })
                .catch_(x -> {
                    System.out.println("if error occurs, I will be called");
                    return null;
                })
                .finally_(x -> {
                    System.out.println("this is final step");
                    return null;
                });

    }
}
