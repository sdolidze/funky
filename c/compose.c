#include <stdio.h>
#include <stdarg.h>
#include <stdlib.h>
#include <assert.h>
#include <strings.h>

/* interesting approach for future:
 * --------------------------------
 * each function should have arg_t input, which contains list
 * of its arguments, and also enclosing context :)
 * how can I specify constaints: my funciton takes only two arguments?
 * or should it be a runtime thing?
 */

#define deref(ref, type) (*((type *) ref))
#define ref(value, type) ({ type *res = malloc(sizeof(type)); *res = value; res; })

#define refInt(num) (  ref(num, int))
#define derefInt(num) (deref(num, int))

typedef void *(*function)(void *);
typedef void *(*bifunction)(void *, void *);

typedef struct function_t {
    bifunction f;
    void *x; // this looks like a function context :)
} function_t;

typedef struct compose_t {
    function_t *f;
    function_t *g;
} compose_t;

function_t *function_create(bifunction f, void *context) {
    function_t *fun = malloc(sizeof(function_t));
    fun->f = f;
    fun->x = context;
    return fun;
}

compose_t *compose_create(function_t *f, function_t *g) {
    compose_t *comp = malloc(sizeof(compose_t));
    comp->f = f;
    comp->g = g;
    return comp;
}

void *apply(function_t *f, void *x) {
    return f->f(x, f->x);
}

void *apply_compose(void *x, void *data) {
    compose_t *comp = (compose_t *) data;
    return apply(comp->f, apply(comp->g, x));
}

function_t *compose(function_t *f, function_t *g) {
    compose_t *comp = compose_create(f, g);
    return function_create(apply_compose, comp);
}

void *apply_wrap(void *x, void *data) {
    function f = (function) data;
    return f(x); 
}

function_t *wrap(function f) {
    return function_create(apply_wrap, f);
}

void *inc(void *x) {
    return refInt(derefInt(x) + 1);
}

void *square(void *x) {
    return refInt(derefInt(x) * derefInt(x));
}

int main() {
    function_t *incrementAndSquare = compose(wrap(square), wrap(inc));
    int num = derefInt(apply(incrementAndSquare, refInt(5)));
    printf("%d\n", num); // prints 36
    return 0;
}
