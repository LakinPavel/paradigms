package queue;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedQueue extends AbstractQueue{
    private Node head, tail;

    protected void enqueueImpl(Object el){
        Node work = new Node(el, tail);
        if (size == 0){
            head = work;
        }
        else{
            tail.next = work;
        }
        tail = work;
    }

    protected Object elementImpl(){
        return head.value;
    }


    protected Object dequeueImpl(){
        Object ans = head.value;
        head = head.next;
        return ans;
    }

    protected void init(){
        tail = null;
        head = null;
    }

    protected void pushImpl(Object el) {
        head = new Node(el, head);
    }

    protected Object peekImpl(){
        return tail.value;
    }

    public Object remove(){
        assert  size > 0;

        size -= 1;
        return tail.value;
    }


public Queue flatMap(Function<Object, Collection> func) {
    LinkedQueue ans = new LinkedQueue();
    Node current = head;
    for (int i = 0; i < size; i++) {
        Object item = current.value;
        Collection mas = func.apply(item);
        for (Object element : mas) {
            ans.enqueue(element);
        }
        current = current.next;
    }
    return ans;
}



    public int countIf(Predicate<Object> predicate){
        assert predicate != null;

        int ans = 0;
        Node current = head;
        for (int i = 0; i < size(); i++){
            if(predicate.test(current.value)){
                ans += 1;
            }
            current = current.next;
        }
        return ans;
    }



    private class Node{
        private Object value;
        private Node next;

        public Node(Object value, Node next){
            assert value != null;

            this.value = value;
            this.next = next;
        }

    }
}
