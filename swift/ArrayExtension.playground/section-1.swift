import Cocoa

// ---------------------
// imitation of List a = Nil | Cons (List a) algebraic data type
// because underneath array is used instead of linked list, performance if aweful
// this just an FP excercise, so you should not this code in production

extension Array {
    var head: T {
        return self[0]
    }
    
    var tail: [T] {
        // this is terribly inefficient, it need O(n) time, instead of O(1)
        var ys = Array(self)
        ys.removeAtIndex(0)
        return ys
    }
}

func cons<T>(x: T, xs: [T]) -> [T] {
    var ys = Array(xs)
    ys.insert(x, atIndex: 0)
    return ys
}

// -------------------------

func foldRight<A,B>(f: (A, B) -> B, v: B, xs: [A]) -> B {
    return xs.isEmpty ? v : f(xs.head, foldRight(f, v, xs.tail))
}

func foldLeft<A,B>(f: (B, A) -> B, v: B, xs: [A]) -> B {
    return xs.isEmpty ? v : foldLeft(f, f(v, xs.head), xs.tail)
}

func map<A,B>(f: A -> B, xs: [A]) -> [B] {
    return foldRight({cons(f($0), $1)}, [], xs)
}

func sum(xs: [Int]) -> Int {
    return foldRight(+, 0, xs)
}


sum([1,2,3,4,5])
let foo = map({$0+1}, [1,2,3])
println(foo)
