//package queue;
//
//import java.util.function.Predicate;
//
//
//// notNull <=> ∀ i ∈ [fornt:rear] || ∀ i ∈ [front:capacity-1] ∪ [0:rear] : elements[i] != null
//// queueСondition <=> elements[rear] - last in queue && elements[front] - first in queue
//// saveQueue <=> nFix = size: for i in range(size): elements.pop() == elements'.pop() && size' == size
//
///*
// * Pred: size == 0 &&
// *       capacity = 2 &&
// *       front = 0 &&
// *       rear = -1 &&
// *       elements
// * Inv: size >= 0 &&
// *      size <= capacity &&
// *      capacity >= 2 &&
// *      notNull &&
// *      front ∈ [0:capacity-1] &&
// *      rear ∈ [0:capacity-1] &&
// * Post:
// */
//public class ArrayQueue_save_3 {
//    private Object[] elements;
//    private int capacity;
//    private int size;
//    private int front;
//    private int rear;
//
//
//    public ArrayQueue_save_3(){
//        elements = new Object[2];
//        capacity = 2;
//        size = 0;
//        front = 0;
//        rear = -1;
//    }
//
//    /*
//     * Pred: true
//     * Post: capacity' = capacity * 2 &&
//     *       queueСondition &&
//     *       saveQueue
//     */
//    private void alloc() {
//        if (capacity == size) {
//            Object[] work_elements = new Object[capacity * 2];
//            System.arraycopy(elements, front, work_elements, front + size, size - front);
//            if (rear > front) {
//                rear = rear + size;
//            } else {
//                System.arraycopy(elements, 0, work_elements, 0, rear + 1);
//            }
//            elements = work_elements;
//            front = front + size;
//            capacity *= 2;
//        }
//    }
//
//    /*
//     * Pred: el != null
//     * Post: notNull &&
//     *       size = size` + 1 &&
//     *       rear = (rear` + 1) % capacity` &&
//     *       elements[rear] = el &&
//     *       capacity = capacity` || capacity = capacity` * 2 &&
//     *       queueСondition
//     */
//    public void enqueue(Object el) {
//        assert el != null;
//
//        alloc();
//        rear = (rear + 1) % capacity;
//        elements[rear] = el;
//        size += 1;
//    }
//
//    /*
//     * Pred: notNull &&
//     *       size` >= 1
//     * Post: R = elements[front] &&
//     *       saveOueue && queueСondition
//     */
//    public Object element() {
//        assert size > 0;
//
//        return elements[front];
//    }
//
//    /*
//     * Pred: notNull &&
//     *       size` >= 1
//     * Post: R = elements[front] &&
//     *       size = size` - 1 &&
//     *       front = (front + 1 + capacity) % capacity &&
//     *       queueСondition
//     */
//    public Object dequeue() {
//        assert size > 0;
//
//        Object ans = elements[front];
//        front = (front + 1) % capacity;
//        size -= 1;
//        return ans;
//    }
//
//    /*
//     * Pred: true
//     * Post: saveQueue && queueСondition
//     */
//    public int size() {
//        return size;
//    }
//
//    /*
//     * Pred: true
//     * Post: saveQueue && queueСondition
//     */
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    /*
//     * Pred: true
//     * Post: new ArrayQueue()
//     */
//    public void clear() {
//        elements = new Object[2];
//        capacity = 2;
//        size = 0;
//        front = 0;
//        rear = -1;
//    }
//
//    /*
//     * Pred: el != null
//     * Post: elements[front] == el &&
//     *       queueСondition  &&
//     *       size += 1;
//     */
//    public void push(Object el){
//        assert el != null;
//
//        alloc();
//        Object fixLast = elements[capacity-1];
//        for (int i = capacity-1; i >= 1; i --){
//            elements[i] = elements[i-1];
//        }
//        rear = (rear + 1) % capacity;
//        elements[0] = fixLast;
//        elements[front] = el;
//        size += 1;
//    }
//
//     /*
//     * Pred: notNull &&
//     *       size` >= 1
//     * Post: R = elements[rear] &&
//     *       saveOueue && queueСondition
//     */
//    public Object peek(){
//        assert size > 0;
//
//        return elements[rear];
//    }
//
//    /*
//     * Pred: notNull &&
//     *       size` >= 1
//     * Post: R = elements[rear] &&
//     *       size = size` - 1 &&
//     *       rear = (rear - 1 + capacity) % capacity &&
//     *       queueСondition
//     */
//    public Object remove(){
//        assert size > 0;
//
//        Object ans = peek();
//        rear = (rear - 1 + capacity) % capacity;
//        size -= 1;
//        return ans;
//    }
//
//    /*
//     * Pred: notNull
//     * Post: R = ans &&
//     *       saveQueue && queueСondition && notNull
//     */
//    public int countIf(Predicate<Object> predicate){
//        int ans = 0;
//        for (int i = 0; i < size(); i++){
//            int idx = (front + i) % capacity;
//            if (predicate.test(elements[idx])){
//                ans += 1;
//            }
//        }
//        return ans;
//    }
//}
