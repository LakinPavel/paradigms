package queue;

import java.util.HashMap;

public class MyArrayQueqeADTTest {

    private void cheker(ArrayQueueADT mem){
        while (!ArrayQueueADT.isEmpty(mem)){
            System.out.println(ArrayQueueADT.element(mem));
            System.out.println(ArrayQueueADT.size(mem));
            System.out.println(ArrayQueueADT.dequeue(mem));
            System.out.println(ArrayQueueADT.size(mem));
            System.out.println("next");
        }
    }
    public static void main(String[] args) {
        ArrayQueueADT que1 = ArrayQueueADT.creacte();
        ArrayQueueADT que2 = ArrayQueueADT.creacte();
        HashMap<Integer, String> ma = new HashMap<Integer, String>();
        ma.put(0, "a");
        ma.put(1, "b");
        ma.put(2, "c");
        ma.put(3, "d");
        ma.put(4, "e");
        ma.put(5, "f");
        ma.put(6, "g");
        ma.put(7, "h");
        ma.put(8, "i");
        ma.put(9, "j");
        for (int i = 0; i < 10; i ++){
            ArrayQueueADT.enqueue(que1, i);
            ArrayQueueADT.enqueue(que2, ma.get(i));
        }
        System.out.println("que1 " + ArrayQueueADT.dequeue(que1));
        System.out.println("que2 " + ArrayQueueADT.dequeue(que2));
        System.out.println();
        System.out.println("que1 " + ArrayQueueADT.dequeue(que1));
        System.out.println("que2 " + ArrayQueueADT.dequeue(que2));
        System.out.println();
        System.out.println("que1 " + ArrayQueueADT.dequeue(que1));
        System.out.println("que2 " + ArrayQueueADT.dequeue(que2));
        System.out.println();
        System.out.println("que1 " + ArrayQueueADT.dequeue(que1));
        System.out.println("que2 " + ArrayQueueADT.dequeue(que2));
        System.out.println();
        System.out.println("que1 " + ArrayQueueADT.dequeue(que1));
        System.out.println("que2 " + ArrayQueueADT.dequeue(que2));
        System.out.println();
        System.out.println("que1 " + ArrayQueueADT.dequeue(que1));
        System.out.println("que2 " + ArrayQueueADT.dequeue(que2));
        System.out.println();
        System.out.println("que1 " + ArrayQueueADT.dequeue(que1));
        System.out.println("que2 " + ArrayQueueADT.dequeue(que2));
        System.out.println();
        System.out.println("que1 " + ArrayQueueADT.dequeue(que1));
        System.out.println("que2 " + ArrayQueueADT.dequeue(que2));
        System.out.println();

    }
}
