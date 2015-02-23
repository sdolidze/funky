#include <stdio.h>
#include <stdarg.h>
#include <stdlib.h>
#include <assert.h>
#include <strings.h>

// what if I need auxiliary parameters? exta `void *` for data?
typedef void *(*bifunction)(void *, void *);
typedef int (*predicate)(void *);
typedef int elem_t;

typedef struct list_t {
    elem_t head;
    struct list_t *tail;
} list_t;

list_t *cons(elem_t head, list_t *tail) {
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

list_t *list(int count, ...) {
    va_list ap;

    va_start(ap, count);

    list_t *xs = NULL;
    for (int i=0; i<count; i++) {
        elem_t x = va_arg(ap, elem_t);
        xs = cons(x, xs);
    }

    va_end(ap);

    return reverse(xs);
}

int main() {
    list_t *xs = list(3, 1, 2, 3);
    return 0;
}