package queue;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
// notNull <=> ∀ i ∈ [fornt:rear] || ∀ i ∈ [front:capacity-1] ∪ [0:rear] : elements[i] != null
// queueСondition <=> elements[rear] - last in queue && elements[front] - first in queue
// saveQueue <=> nFix = size: for i in range(size): elements.pop() == elements'.pop() && size' == size

/*
 * Pred: size == 0 &&
 *       capacity = 2 &&
 *       front = 0 &&
 *       rear = -1 &&
 *       elements
 * Inv: size >= 0 &&
 *      size <= capacity &&
 *      capacity >= 2 &&
 *      notNull &&
 *      front ∈ [0:capacity-1] &&
 *      rear ∈ [0:capacity-1] &&
 * Post:
 */

public class ArrayQueue<T> extends AbstractQueue{
    private Object[] elements = new Object[2];
    private int capacity = 2;
    private int front = 0;
    private int rear = -1;
    /*
     * Pred: true
     * Post: capacity' = capacity * 2 &&
     *       queueСondition &&
     *       saveQueue
     */
    private void alloc() {
        if (capacity == size) {
            Object[] work_elements = new Object[capacity * 2];
            System.arraycopy(elements, front, work_elements, front + size, size - front);
            if (rear > front) {
                rear = rear + size;
            } else {
                System.arraycopy(elements, 0, work_elements, 0, rear + 1);
            }
            elements = work_elements;
            front = front + size;
            capacity *= 2;
        }
    }

    /*
     * Pred: el != null
     * Post: notNull &&
     *       size = size` + 1 &&
     *       rear = (rear` + 1) % capacity` &&
     *       elements[rear] = el &&
     *       capacity = capacity` || capacity = capacity` * 2 &&
     *       queueСondition
     */
    protected void enqueueImpl(Object el){
        alloc();
        rear = (rear + 1) % capacity;
        elements[rear] = el;
    }


    /*
     * Pred: notNull &&
     *       size` >= 1
     * Post: R = elements[front] &&
     *       saveOueue && queueСondition
     */
    protected Object elementImpl(){
        return elements[front];
    }


    /*
     * Pred: notNull &&
     *       size` >= 1
     * Post: R = elements[front] &&
     *       size = size` - 1 &&
     *       front = (front + 1 + capacity) % capacity &&
     *       queueСondition
     */
    protected Object dequeueImpl(){
        Object ans = elements[front];
        front = (front + 1) % capacity;
        return ans;
    }

    /*
     * Pred: true
     * Post: new ArrayQueue()
     */
    protected void init(){
        elements = new Object[2];
        capacity = 2;
        size = 0;
        front = 0;
        rear = -1;
    }

    /*
     * Pred: el != null
     * Post: elements[front] == el &&
     *       queueСondition  &&
     *       size += 1;
     */
    protected void pushImpl(Object el) {
        alloc();
        if (size == 0) {
            clear();
            rear = 0;
            front = 0;
        } else {
            front = (capacity + front - 1) % capacity;
        }
        elements[front] = el;
    }

    /*
     * Pred: notNull &&
     *       size` >= 1
     * Post: R = elements[rear] &&
     *       saveOueue && queueСondition
     */
    protected Object peekImpl(){
        return elements[rear];
    }

    /*
     * Pred: notNull &&
     *       size` >= 1
     * Post: R = elements[rear] &&
     *       size = size` - 1 &&
     *       rear = (rear - 1 + capacity) % capacity &&
     *       queueСondition
     */
    public Object remove(){
        assert size > 0;

        Object ans = peek();
        rear = (rear - 1 + capacity) % capacity;
        size -= 1;
        return ans;
    }

    /*
     *Pred: notNull
     *Post: R = ArrayQueue || LinkedQueue &&
     *       saveQueue && queueСondition && notNull
     */
    public Queue flatMap(Function<Object, Collection> func) {
        ArrayQueue ans = new ArrayQueue<>();
        for (int i = 0; i < size; i++){
            int idx = (front + i) % capacity;
            Object current = elements[idx];
            Collection mas = func.apply(current);
            for (Object element : mas) {
                ans.enqueue(element);
            }
        }
        return ans;
    }

    /*
     * Pred: predicate != null && notNull
     * Post: R = ans &&
     *       saveQueue && queueСondition && notNull
     */
    public int countIf(Predicate<Object> predicate){
        assert predicate != null;

        int ans = 0;
        for (int i = 0; i < size(); i++){
            int idx = (front + i) % capacity;
            if (predicate.test(elements[idx])){
                ans += 1;
            }
        }
        return ans;
    }
}
