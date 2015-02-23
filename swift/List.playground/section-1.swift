import Foundation

/*
 * I really hope support for recursive enums will be added in the near future
 * ---
 * enum List<T> {
 *     case Empty
 *     case Cons(head: T, tail: List<T>)
 * }
 */

// is may be wise to change this to protocl (if possible)
class List<A>: Printable {
    var description: String {
        return "will be overriden"
    }
}

class Empty<A>: List<A> {
    override var description: String {
        return "[]"
    }
}

class Cons<A>: List<A> {
    let head: A
    let tail: List<A>
    
    init(head: A, tail: List<A>) {
        self.head = head
        self.tail = tail
    }
    
    override var description: String {
        return "\(head):\(tail.description)"
    }
}

func list<A>(xs: A...) -> List<A> {
    var cur: List<A> = Empty()
    
    for x in xs.reverse() {
        cur = Cons<A>(head: x, tail: cur)
    }
    
    return cur
}

func head<A>(list: List<A>) -> A {
    // can I do this in a more elegant way?
    // maybe use switch case?
    if let xs = list as? Cons {
        return xs.head
    } else {
        fatalError("emty list")
    }
}

func tail<A>(list: List<A>) -> List<A> {
    if let xs = list as? Cons {
        return xs.tail
    } else {
        fatalError("empty list")
    }
}
