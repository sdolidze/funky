'use strict'

function *integers() {
    var num = 1;
    while (true) {
        yield num++
    }
}

function *range(lo, hi) {
    if (hi === undefined) {
        hi = lo
        lo = 0
    }
    for (let i = lo; i < hi; i++) {
        yield i
    }
}

function *take(n, xs) {
    for (let i = 0; i < n; i++) {
        let x = xs.next()
        if (x.done) {
            return
        }
        yield x.value
    }
}

function *drop(n, xs) {
    for (let i = 0; i < n; i++) {
        let x = xs.next()
        if (x.done) {
            return
        }
    }
    for (let x of xs) {
        yield x
    }
}

function *takeWhile(p, xs) {
    for (let x of xs) {
        if (p(x)) {
            yield x
        } else {
            return
        }
    }
}

function *dropWhile(p, xs) {
    for (let x of xs) {
        if (!p(x)) {
            break
        }
    }
    for (let x of xs) {
        yield x
    }
}

function *scanLeft(f, v, xs) {
    yield v
    var acc = v
    for (var x of xs) {
        acc = f(acc, x)
        yield acc
    }
}

function *lead(x, xs) {
    yield x
    for (let x of xs) {
        yield x
    }
}

function *follow(x, xs) {
    for (let x of xs) {
        yield x
    }
    yield x
}

function *replicate(x) {
    while (true) {
        yield x
    }
}

function *cycle(xs) {
    let arr = toArray(xs)
    while (true) {
        for (let x of arr) {
            yield x
        }
    }
}

function toArray(generator) {
    var arr = []
    for (var num of generator) {
        arr.push(num)
    }
    return arr
}

function *toIterator(arr) {
    for (var x of arr) {
        yield x
    }
}