#include <stdio.h>
#include <stdarg.h>
#include <stdlib.h>
#include <assert.h>
#include <strings.h>

// I should think of unit testing strategy

#define deref(ref, type) (*((type *) ref))
// beware that this is a compiler extension, so it's not very portable
// but then again, I don't really care :)
#define ref(value, type) ({ type *res = malloc(sizeof(type)); *res = value; res; })
// how can do this in multiple lines?


#define refInt(num) (  ref(num, int))
#define derefInt(num) (deref(num, int))

// it looks very much like Java Genrics, I'm using type erasure :D
// what if I need to inline data? use char[] and macros for size?
// what is I want different sizes in different projects
// use bool for predicat?
typedef void *(*function)(void *);
typedef void *(*bifunction)(void *, void *);
typedef int (*predicate)(void *);

typedef struct list_t {
    void *head;
    struct list_t *tail;
} list_t;

list_t *cons(void *head, list_t *tail) {
    list_t *xs = malloc(sizeof(list_t));
    xs->head = head;
    xs->tail = tail;
    return xs;
}

list_t *reverse(list_t *oldList) {
    list_t *newList = NULL;

    while (oldList != NULL) {
        // remove element from old list
        list_t *temp = oldList;
        oldList = oldList->tail;

        // insert element in new list
        temp->tail = newList;
        newList = temp;
    }

    return newList;
}

// can I make this method generic? how can I give type argument?
// maybe use macro?
list_t *list(int count, ...) {
    va_list ap;

    va_start(ap, count);

    list_t *xs = NULL;
    for (int i=0; i<count; i++) {
        int x = va_arg(ap, int);
        xs = cons(ref(x, int), xs);
    }

    va_end(ap);

    return reverse(xs);
}

int length(list_t *xs) {
    int len = 0;
    for (; xs!=NULL; xs=xs->tail) {
        len++;
    }
    return len;
}

void print(list_t *xs) {
    // it prints exta ` ` in the end
    for (; xs != NULL; xs=xs->tail) {
        printf("%d ", deref(xs->head, int));
    }
    printf("\n");
}

list_t *copy(list_t *oldList) {
    list_t sentinel;
    list_t *newList = &sentinel;

    while (oldList != NULL) {
        newList->tail = cons(oldList->head, NULL);
        newList = newList->tail;
        oldList = oldList->tail;
    }

    return sentinel.tail;
}

void *head(list_t *xs) {
    assert(xs != NULL);
    return xs->head;
}

list_t *tail(list_t *xs) {
    assert(xs != NULL);
    return xs->tail;
}

void *last(list_t *xs) {
    assert(xs != NULL);
    return (xs->tail == NULL) ? xs->head : last(xs->tail);
}

list_t *secondLast(list_t *xs) {
    assert(length(xs) >= 2);

    list_t *secondLast = xs;
    list_t *last = xs->tail;

    xs = xs->tail->tail;

    while (xs != NULL) {
        secondLast = last;
        last = xs;
        xs = xs->tail;
    }

    return secondLast;
}

list_t *_last(list_t *xs) {
    assert(xs != NULL);

    list_t *last = NULL;

    while (xs != NULL) {
        last = xs;
        xs = xs->tail;
    }

    return last;
}

// in-place
list_t *init(list_t *xs) {
    assert(xs != NULL);
    secondLast(xs)->tail = NULL;
    return xs;
}

list_t *map(function f, list_t *xs) {
    for (list_t *ys = xs; ys != NULL; ys = ys->tail) {
        ys->head = f(ys->head);
    }
    return xs;
}

void forEach(function f, list_t *xs) {
    for (list_t *ys = xs; ys != NULL; ys = ys->tail) {
        f(ys->head);
    }
}

void *reduce(bifunction f, void *v, list_t *xs) {
    void *acc = v;
    for (list_t *ys = xs; ys != NULL; ys = ys->tail) {
        acc = f(acc, ys->head);
    }
    return acc;
}

void *inc(void *x) {
    return refInt(derefInt(x) + 1);
}

void *plus(void *x, void *y) {
    return refInt(derefInt(x) + derefInt(y));
}

void *toString(void *x) {
    void *buffer = malloc(32);
    sprintf(buffer, "%d", derefInt(x));
    return buffer;
}

void *printLine(void *x) {
    printf("%s\n", (char *)x);
    return NULL;
}

int main() {
    list_t *xs = list(3, 1, 2, 3);
    list_t *ys = map(toString, xs);
    forEach(printLine, ys);
    return 0;
}