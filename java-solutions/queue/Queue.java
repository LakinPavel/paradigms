package queue;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;


// notNull <=> ∀ i ∈ [fornt:rear] || ∀ i ∈ [front:capacity-1] ∪ [0:rear] : elements[i] != null
// queueСondition <=> elements[rear] - last in queue && elements[front] - first in queue
// saveQueue <=> nFix = size: for i in range(size): elements.pop() == elements'.pop() && size' == size

/*
 * для удобства будем говорит,что наша очередь q утсроенна как обычный arrayList, но с функциями очереди.
 * те первый элемент q это начало очереди, последний соответсвенно конец
 * Inv: forall q[i], q[i] != null &&
 *      size >= 0
 */

public interface Queue {
    /*
     * Pred: element != null
     * Post: size' = size + 1 &&
     *       q[size' - 1] = element
     */
    void enqueue(Object element);

    /*
     * Pred: size > 0
     * Post: return q[0] &&
     *       saveQueue && queueСondition && notNull
     */
    Object element();

    /*
     * Pred: size > 0
     * Post: return q[0] &&
     *       size' = size - 1 &&
     *       forall i ∈ [0, size'-1] forall j ∈ [0, size-2]: q'[i] = q[j+1]
     */
    Object dequeue ();

    /*
     * Pred: true
     * Post: return q.size &&
     *       saveQueue && queueСondition && notNull
     */
    int size();

    /*
     * Pred: true
     * Post: return 1: if size == 0; else return 0 &&
     *       saveQueue && queueСondition && notNull
     */
    boolean isEmpty();

    /*
     * Pred: true
     * Post: new q:  size = 0 <=>  q.isEmpty == 1
     */
    void clear();

    /*
     * Pred: element != null
     * Post: size' = size + 1 &&
     *       q' = [element] + q <=>
     *           forall i ∈ [0, size - 1]: q[i] == q'[i+1] && q'[0] == element
     */
    void push(Object element);

    /*
     * Pred: size > 0
     * Post: return q[size-1] &&
     *       saveQueue && queueСondition && notNull
     */
    Object peek();

    /*
     * Pred: size > 0
     * Post: return q[size - 1] &&
     *       size' = size - 1 &&
     *       forall i ∈ [0, size - 2]: q[i] == q'[i]
     */
    Object remove();

    /*
     *Pred: notNull
     *Post: R = ArrayQueue || LinkedQueue &&
     *       saveQueue && queueСondition && notNull
     */
    Queue flatMap(Function<Object, Collection> func);


    /*
     * Pred: notNull
     * Post: R = ans &&
     *       saveQueue && queueСondition && notNull
     */
    int countIf(Predicate<Object> predicate);
}

// :NOTE: remove generics