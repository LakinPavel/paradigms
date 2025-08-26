package queue;

import java.util.function.Predicate;

// import queue.*
// QueueNotNull <=> queu != null
// notNull <=> ∀ i ∈ [fornt:rear] || ∀ i ∈ [front:capacity-1] ∪ [0:rear] : elements[i] != null
// queueСondition <=> elements[rear] - last in queue && elements[front] - first in queue
// saveQueue <=> nFix = size: for i in range(size): elements.pop() == elements'.pop() && size' == size


/*
 * Pred: size == 0 && 
 *       capacity = 2 &&
 *       front = 0 &&
 *       rear = -1 &&
 *       elements
 * Inv: QueueNotNull &&
 *      size >= 0 &&
 *      size <= capacity &&
 *      capacity >= 2 &&
 *      notNull &&
 *      front ∈ [0:capacity-1] &&
 *      rear ∈ [0:capacity-1] &&
 * Post:
 */

public class ArrayQueueADT {
    private Object[] elements = new Object[2];
    private int capacity = 2;
    private int size = 0;
    private int front = 0;   
    private int rear = -1;   

    /*
     * Pred: true
     * Post: new ArrayQueueADT()
     */
    public static ArrayQueueADT creacte(){
        return new ArrayQueueADT();
    }


     /*
     * Pred: true
     * Post: capacity' = capacity * 2 &&
     *       queueСondition &&
     *       saveQueue
     */
    private static void alloc(ArrayQueueADT queue){
        if (queue.capacity == queue.size){
            Object[] work_elements = new Object[queue.capacity*2];
            System.arraycopy(queue.elements, queue.front, work_elements, queue.front + queue.size , queue.size - queue.front); 
            if (queue.rear > queue.front){
                queue.rear = queue.rear + queue.size;
            }
            else{
                System.arraycopy(queue.elements, 0, work_elements, 0, queue.rear + 1);        
            }
            queue.elements = work_elements;
            queue.front = queue.front + queue.size;
            queue.capacity *= 2;
        }
    }

     /*
     * Pred: el != null
     *       QueueNotNull &&
     * Post: notNull &&
     *       size = size` + 1 &&
     *       rear = (rear` + 1) % capacity` && 
     *       elements[rear] = el &&
     *       capacity = capacity` || capacity = capacity` * 2 && 
     *       queueСondition
     */
    public static void enqueue(ArrayQueueADT queue, Object el){
        assert el != null;
        assert queue != null;

        alloc(queue);
        queue.rear = (queue.rear + 1) % queue.capacity;
        queue.elements[queue.rear] = el;
        queue.size += 1;
    }


    /*
     * Pred: QueueNotNull &&
     *       notNull &&
     *       size` >= 1 
     * Post: R = elements[front] &&
     *       saveOueue && queueСondition
     */
    public static Object element(ArrayQueueADT queue){
        assert queue != null;
        assert queue.size > 0;

        return queue.elements[queue.front];
    }

    /*
     * Pred: QueueNotNull &&
     *       notNull &&
     *       size` >= 1
     * Post: R = elements[front] &&
     *       size = size` - 1 &&
     *       front = (front + 1 + capacity) % capacity && 
     *       queueСondition
     */
    public static Object dequeue (ArrayQueueADT queue){
        assert queue != null;
        assert queue.size > 0;

        Object ans = queue.elements[queue.front];
        queue.front = (queue.front + 1) % queue.capacity;
        queue.size -= 1;
        return ans;
    }

    /*
     * Pred: QueueNotNull
     * Post: saveQueue && queueСondition
     */
    public static int size(ArrayQueueADT queue){
        assert queue != null;

        return queue.size;
    }

    /*
     * Pred: QueueNotNull
     * Post: saveQueue && queueСondition
     */
    public static boolean isEmpty(ArrayQueueADT queue){
        assert queue != null;

        return queue.size == 0;
    }
 
    /*
     * Pred: QueueNotNull
     * Post: new ArrayQueueADT()
     */
    public static void clear(ArrayQueueADT queue){
        assert queue != null;

        queue.capacity = 2;
        queue.size = 0;
        queue.front = 0;
        queue.rear = -1;
    }

    /*
     * Pred: QueueNotNull &&
     *       el != null
     * Post: elements[front] == el &&
     *       queueСondition &&
     *       size += 1;
     */
    public static void push(ArrayQueueADT queue, Object el){
        assert el != null;
        assert queue != null;

        alloc(queue);
        if (queue.size == 0) {
            clear(queue);
            queue.rear = 0;
            queue.front = 0;
        } else {
            queue.front = (queue.capacity + queue.front - 1) % queue.capacity;
        }
        queue.elements[queue.front] = el;
        queue.size += 1;
    }

     /*
     * Pred: QueueNotNull &&
     *       notNull &&
     *       size` >= 1 
     * Post: R = elements[rear] && 
     *       saveOueue && queueСondition
     */
    public static Object peek(ArrayQueueADT queue){
        assert queue != null;
        assert queue.size > 0;

        return queue.elements[queue.rear];
    }

    /*
     * Pred: QueueNotNull &&
     *       notNull &&
     *       size` >= 1
     * Post: R = elements[rear] &&
     *       size = size` - 1 &&
     *       rear = (rear - 1 + capacity) % capacity && 
     *       queueСondition
     */
    public static Object remove(ArrayQueueADT queue){
        assert queue != null;
        assert queue.size > 0;

        Object ans = peek(queue);
        queue.rear = (queue.rear - 1 + queue.capacity) % queue.capacity;
        queue.size -= 1;
        return ans;
    }

    /*
     * Pred: QueueNotNull &&
     *       predicate != null && notNull
     * Post: R = ans &&
     *       saveQueue && queueСondition && notNull
     */
    public static int countIf(ArrayQueueADT queue, Predicate<Object> predicate){
        assert queue != null;
        assert predicate != null;

        int ans = 0;
        for (int i = 0; i < size(queue); i++){
            int idx = (queue.front + i) % queue.capacity;
            if (predicate.test(queue.elements[idx])){
                ans += 1;
            }
        }
        return ans;
    }

}

// :NOTE: queue ?= null   +
