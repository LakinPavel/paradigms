package queue;

import java.util.HashMap;

public class MyArrayQueueTest {
    private static void checker(ArrayQueue mem){
        while (!mem.isEmpty()){
            System.out.println(mem.element());
            System.out.println(mem.size());
            System.out.println(mem.dequeue());
            System.out.println(mem.size());
            System.out.println("next");
        }
    }

    private static void last(ArrayQueue mem, int a){
        mem.enqueue("new test" + a);
        System.out.println(mem.element());
        mem.clear();
        System.out.println();
        System.out.println(mem.size());
        System.out.println(mem.isEmpty());
        System.out.println();
        mem.enqueue("pobedim!" + a);
        System.out.println(mem.size());
        System.out.println(mem.isEmpty());
        System.out.println(mem.dequeue());
        System.out.println();
    }
    public static void main(String[] args) {
        ArrayQueue queq1 = new ArrayQueue();
        ArrayQueue queq2 = new ArrayQueue();
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
        for (int i = 0; i < 10; i++) {
            queq1.enqueue(i);
            queq2.enqueue(ma.get(i));
        }
        System.out.println(queq1.element());
    }
}

// :NOTE: raw use
