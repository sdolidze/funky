#include <stdio.h>
#include <stdarg.h>
#include <stdlib.h>
#include <assert.h>
#include <strings.h>

// this code needs refactoring, I should split into multiple files
// how the hell can I make this generic? use void* [and size]? use char[] and define size?
// use macros?
// define macro to easily define lists: list(1,2,3) -> 1:2:3:[]

typedef void *(*bifunction)(void *, void *);
typedef int elem_t;
typedef int (*predicate)(int);

typedef struct List {
    elem_t head;
    struct List *tail;
} List;

List *cons(elem_t head, List *tail) {
    List *xs = malloc(sizeof(List));
    xs->head = head;
    xs->tail = tail;
    return xs;
}

// ------
List *append(elem_t x, List *xs) {
    if (xs == NULL) {
        return cons(x, NULL);
    } else {
        return cons(xs->head, append(x, xs->tail));
    }
}

// from http://www.teamten.com/lawrence/writings/reverse_a_linked_list.html
// API is very deceptive: looks like a pure function, but it modifies oldList so it is unsafe to use it afterwords
// if it returned nothing, it would make more sense. Anyway, pure functions are much more fun :)
List *reverseInPlace(List *oldList) {
    List *newList = NULL;

    while (oldList != NULL) {
        // remove element from old list
        List *temp = oldList;
        oldList = oldList->tail;

        // insert element in new list
        temp->tail = newList;
        newList = temp;
    }

    return newList;
}

List *reverseInPlaceTailRec(List *newList, List *oldList) {
    if (oldList == NULL) {
        return newList;
    }
    List *temp = oldList->tail;
    oldList->tail = newList;
    return reverseInPlaceTailRec(oldList, temp);
}

void reverseInPlaceRec(List **headRef) {
    List *first;
    List *rest;

    if (*headRef == NULL) {
        return;
    }

    first = *headRef;
    rest = first->tail;

    if (rest == NULL) {
        return;
    }

    reverseInPlaceRec(&rest);

    first->tail->tail = first;
    first->tail = NULL;

    *headRef = rest;
}

List *reverseRec(List *xs) {
    if (xs == NULL) {
        return NULL;
    } else {
        return append(xs->head, reverseRec(xs->tail));
    }
}

List *reverseTailRec(List *newList, List *oldList) {
    return oldList == NULL ? newList : reverseTailRec(cons(oldList->head, newList), oldList->tail);
}

List *reverseTailRecWrapper(List *xs) {
    return reverseTailRec(NULL, xs);
}

List *reverseInPlaceComplex(List *head) {
    List *prev = NULL;

    while (head != NULL) {
        // Keep next node since we trash
        // the next pointer.
        List *next = head->tail;

        // Switch the next pointer
        // to point backwards.
        head->tail = prev;

        // Move both pointers forward.
        prev = head;
        head = next;
    }

    return prev;
}

//List *reverse(List *oldList) {
//    List *newList = NULL;
//    for (; oldList !=NULL; oldList = oldList->tail) {
//        newList = cons(oldList->head, newList);
//    }
//    return newList;
//}

//---
typedef struct FristLast {
    List *first;
    List *last;
} FirstLast;

List *copyNode(List* node) {
    List *res = malloc(sizeof node);
    res->head = node->head;
    res->tail = NULL;
    return res;
}

FirstLast rev(List *old) {
    FirstLast cur;
    if (old->tail == NULL) {
        cur.first = copyNode(old);
        cur.last = cur.first;
        return cur;
    }
    cur = rev(old->tail);
    List *copy = copyNode(old);
    cur.last->tail = copy;
    cur.last = copy;
    return cur;
}

List *reverse(List *old) {
    if (old == NULL) {
        return NULL;
    }
    return rev(old).first;
}

/// ---

void *reduce(bifunction f, void *v, List *xs) {
    void *acc = v;
    for (; xs!=NULL; xs=xs->tail) {
        acc = f(acc, &xs->head);
    }
    return acc;
}

void *reduce1(void *f(void *, void *), List *xs) {
    return reduce(f, &xs->head, xs->tail);
}

void *reduceRec(void *f(void *, int), void *v, List *xs) {
    return xs == NULL ? v : reduceRec(f, f(v, xs->head), xs->tail);
}

List *list(int count, ...) {
    va_list ap;

    va_start(ap, count);

    List *xs = NULL;
    for (int i=0; i<count; i++) {
        elem_t x = va_arg(ap, elem_t);
        xs = cons(x, xs);
    }

    va_end(ap);

    return reverse(xs);
}

int length(List *xs) {
    int len = 0;
    for (; xs!=NULL; xs=xs->tail) {
        len++;
    }
    return len;
}

int lengthRec(List *xs) {
    // is this tail recursion? last operation is plus, not lengthRec
    if (xs == NULL) {
        return 0;
    } else {
        return 1 + lengthRec(xs->tail);
    }
}

void print(List *xs) {
    for (; xs != NULL; xs=xs->tail) {
        printf("%d ", xs->head);
    }
    printf("\n");
}

List *copy(List *oldList) {
    // empty
    if (oldList == NULL) {
        return NULL;
    }

    // head
    List *newList = cons(oldList->head, NULL);
    List *res = newList;
    oldList = oldList->tail;

    // tail
    while (oldList != NULL) {
        newList->tail = cons(oldList->head, NULL);
        newList = newList->tail;
        oldList = oldList->tail;
    }

    return res;
}

List *copyRec(const List *oldList) {
    return oldList == NULL ? NULL : cons(oldList->head, copyRec(oldList->tail));
}

void copyTailRecAux(List *newList, List *oldList) {
    if (oldList != NULL) {
        newList->tail = cons(oldList->head, NULL);
        copyTailRecAux(newList->tail, oldList->tail);
    }
}

List *copyTailRec(List *oldList) {
    List newList;
    newList.tail = NULL;
    copyTailRecAux(&newList, oldList);
    return newList.tail;
}

List *copyIter(List *oldList) {
    List res, *newList;
    newList = &res;
    newList->tail = NULL;

    while (oldList != NULL) {
        newList->tail = cons(oldList->head, NULL);
        newList = newList->tail;
        oldList = oldList->tail;
    }

    return res.tail;
}

List *init(List *xs) {
    assert(xs != NULL);
    return xs->tail == NULL ? NULL : cons(xs->head, init(xs->tail));
}

List *last(List *xs) {
    assert(xs != NULL);
    return (xs->tail == NULL) ? xs : last(xs->tail);
}

List *lastIter(List *xs) {
    assert(xs != NULL);
    List *cur = NULL;
    for (; xs!=NULL; xs=xs->tail) {
        cur = xs;
    }
    return cur;
}

int nth(int n, List *xs) {
    assert(n >= 0 && n < length(xs));
    for (int i=0; i<n; i++) {
        xs = xs->tail;
    }
    return xs->head;
}

int nthRec(int n , List *xs) {
    return n == 0 ? xs->head : nthRec(n-1, xs->tail);
}

List *appendIter(elem_t x, List *xs) {
    if (xs == NULL) {
        return NULL;
    }
    List *ys = copy(xs);
    last(ys)->tail = cons(x, NULL);
    return ys;
}

List *appendIter2(elem_t x, List *xs) {
    return reverse(cons(x, reverse(xs)));
}

List *map(int f(int), List *xs) {
    if (xs == NULL) {
        return NULL;
    } else {
        return cons(f(xs->head), map(f, xs->tail));
    }
}

List *mapIter1(int f(int), List *xs) {
    List *ys = NULL;
    for (; xs!=NULL; xs=xs->tail) {
        ys = cons(f(xs->head), ys);
    }
    return reverse(ys);
}

List *mapIter2(int f(int), List *xs) {
    List *head = copy(xs);
    for (List *ys=head; ys!=NULL; ys=ys->tail) {
        ys->head = f(ys->head);
    }
    return head;
}

List *mapIter3(int f(int), List *xs) {
    // copy + map
    return NULL;
}

List *filter(int f(int), List *xs) {
    if (xs == NULL) {
        return NULL;
    } else {
        return f(xs->head) ? cons(xs->head, filter(f, xs->tail)) : filter(f, xs->tail);
    }
}

List *concat(List *xs, List *ys) {
    return xs == NULL ? ys : cons(xs->head, concat(xs->tail, ys));
}

List *concatIter(List *xs, List *ys) {
    xs = reverse(xs);
    for (; xs!=NULL; xs=xs->tail) {
        ys = cons(xs->head, ys);
    }
    return ys;
}

List *concatIter2(List *xs, List *ys) {
    if (xs == NULL) {
        return NULL;
    }
    List *zs = copy(xs);
    last(zs)->tail = ys;
    return zs;
}

List *id(List *xs) {
    return xs;
}

// tortoise and hare algorithm
int middle(List *xs) {
    assert(xs != NULL);
    List *slow = xs;
    List *fast = xs;

    while (fast->tail != NULL && fast->tail->tail != NULL) {
        slow = slow->tail;
        fast = fast->tail->tail;
    }

    return slow->head;
}

void concatImpure(List *xs, List *ys) {
    assert(xs != NULL);
    last(xs)->tail = ys;
}

int inc(int a) {
    return a + 1;
}

int square(int a) {
    return a * a;
}

int isEven(int x) {
    return x % 2 == 0;
}

void countSort(int len, int *xs, int maxElem) {
    // ∀x ∈ xs x ≥ 0 ∧ x ≤ maxElem
    int bytes = sizeof(int)*(maxElem+1);
    int *counter = malloc(bytes);
    memset(counter, 0, bytes);

    for (int i=0; i<len; i++) {
        counter[xs[i]]++;
    }

    int pos = 0;
    for (int i=0; i<=maxElem; i++) {
        for (int j=0; j<counter[i]; j++) {
            xs[pos++] = i;
        }
    }
}

void printArr(int len, int *xs) {
    for (int i=0; i<len; i++) {
        printf("%d ", xs[i]);
    }
    printf("\n");
}

void printInt(int n) {
    printf("%d ", n);
}

int max(int len, int *arr) {
    assert(len >= 1);
    int max = arr[0];
    for (int i=1; i<len; i++) {
        if (arr[i] > max) {
            max = arr[i];
        }
    }
    return max;
}

List *mergeSortedLists(List *xs, List *ys) {
    if (xs == NULL) {
        return ys;
    }
    if (ys == NULL) {
        return xs;
    }
    if (xs->head < ys->head) {
        return cons(xs->head, mergeSortedLists(xs->tail, ys));
    }
    return cons(ys->head, mergeSortedLists(xs, ys->tail));
}

List *take(int n, List *xs) {
    assert(0);
}

List *drop(int n, List *xs) {
    assert(0);
}

List *takeWhile(predicate p, List *xs) {
    assert(0);
}

List *dropWhile(predicate p, List *xs) {
    assert(0);
}

// treeee

typedef struct Tree {
    int elem;
    struct Tree *left;
    struct Tree *right;
} Tree;


Tree *tree() {
    static Tree mid;
    static Tree lft;
    static Tree rgt;

    mid.elem = 2;
    mid.left = &lft;
    mid.right = &rgt;

    lft.elem = 1;
    lft.left = NULL;
    lft.right = NULL;

    rgt.elem = 3;
    rgt.left = NULL;
    rgt.right = NULL;

    return &mid;
}

int isLeaf(Tree *t) {
    return t->left == NULL && t->right == NULL;
}

List *flattenTree(Tree *t) {
    if (isLeaf(t)) {
        return cons(t->elem, NULL);
    } else {
        return concat(flattenTree(t->left), cons(t->elem, flattenTree(t->right)));
    }
}

List *reverseRemember(List *oldList) {
    List *newList = NULL;

    while (oldList != NULL) {
        // remove from old
        List *temp = oldList;
        oldList = oldList->tail;

        // add to new
        temp->tail = newList;
        newList = temp;
    }

    return newList;
}


List *copyRemember(List *oldList) {
    if (oldList == NULL) {
        return NULL;
    }

    List *newList = cons(oldList->head, NULL);
    List *head = newList;
    oldList = oldList->tail;

    for (; oldList!=NULL; oldList=oldList->tail) {
        newList->tail = cons(oldList->head, NULL);
        newList = newList->tail;
    }

    return head;

}


//List *mapO(List *acc, List *xs) {
//    return xs == NULL ? acc : mapO(cons(acc))
//}

//List *map(int f(int), List *xs) {
//    if (xs == NULL) {
//        return NULL;
//    } else {
//        return cons(f(xs->head), map(f, xs->tail));
//    }
//}

int factRec(int x) {
    return x == 0 ? 1 : x * factRec(x-1);
}

int factTailRec(int acc, int x) {
    return x == 0 ? acc : factTailRec(acc * x, x-1);
}

int factIter(int x) {
    int acc = 1;
    for (; x>0; x--) {
        acc = acc * x;
    }
    return acc;
}

int fibRec(int n) {
    return n <= 1 ? n : fibRec(n-1) + fibRec(n-2);
}

int fibTailRec(int cur, int nxt, int n) {
    return n == 0 ? cur : fibTailRec(nxt, cur+nxt, n-1);
}

int fibTailRecWrapper(int n) {
    return fibTailRec(0, 1, n);
}

int fibIter(int n) {
    int cur = 0;
    int nxt = 1;

    for (; n>0; n--) {
        int tmp = cur;
        cur = nxt;
        nxt = tmp+nxt;
        // (cur, nxt) = (nxt, cur+nxt)
    }

    return cur;
}

int main() {
    List *xs = list(5, 1, 2, 3, 4, 5);
//    List *ys = list(5, 11, 12, 13, 14, 15);
//    print(reverseRec((xs));
    print(copyTailRec(xs));
//    printf("%d ", factIter(4));

//    int len = 8;
//    int arr[] = {1,2,3,2,3,5,1,3};
//    printArr(len, arr);
//    countSort(len, arr, max(len, arr));
//    printArr(len, arr);

//    print(flattenTree(tree()));

    return 0;
}
