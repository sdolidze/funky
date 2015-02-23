#include <stdio.h>
#include <stdarg.h>
#include <stdlib.h>
#include <assert.h>
#include <strings.h>

#define deref(x) (*((elem_t *) x))

// what if I need auxiliary parameters? exta `void *` for data?
typedef void *(*function)(void *);
typedef void *(*bifunction)(void *, void *);
typedef int (*predicate)(void *);
typedef int elem_t;

typedef struct list_t {
    elem_t *head;
    struct list_t *tail;
} list_t;

list_t *cons(elem_t *head, list_t *tail) {
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

elem_t *ref(elem_t elem) {
    elem_t *res = malloc(sizeof(int));
    *res = elem;
    return res;
}

list_t *list(int count, ...) {
    va_list ap;

    va_start(ap, count);

    list_t *xs = NULL;
    for (int i=0; i<count; i++) {
        elem_t x = va_arg(ap, elem_t);
        xs = cons(ref(x), xs);
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
        printf("%d ", *xs->head);
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

elem_t *head(list_t *xs) {
    assert(xs != NULL);
    return xs->head;
}

list_t *tail(list_t *xs) {
    assert(xs != NULL);
    return xs->tail;
}

elem_t *last(list_t *xs) {
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

void *reduce(bifunction f, void *v, list_t *xs) {
    void *acc = v;
    for (list_t *ys = xs; ys != NULL; ys = ys->tail) {
        acc = f(acc, ys->head);
    }
    return acc;
}

void *inc(void *x) {
    return ref(deref(x) + 1);
}

void *plus(void *x, void *y) {
    return ref(deref(x) + deref(y));
}

int main() {
    list_t *xs = list(3, 1, 2, 3);
    void *res = reduce(plus, ref(0), xs);
    printf("%d\n", deref(res));
    return 0;
}