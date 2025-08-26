package queue;

//import javax.management.ObjectName;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue{
    protected int size;

    public void enqueue(Object element){
        assert element != null;

        enqueueImpl(element);
        size += 1;
    }
    protected abstract void enqueueImpl(Object element);



    public Object element(){
        assert size > 0;

        return elementImpl();
    }
    protected abstract Object elementImpl();



    public Object dequeue (){
        assert size > 0;

        size -= 1;
        return dequeueImpl();
    }
    protected abstract Object dequeueImpl();

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        size = 0;
        init();
    }
    protected abstract void init();

    public void push(Object element){
        assert element != null;

        pushImpl(element);
        size += 1;
    }
    protected abstract void pushImpl(Object element);

    public Object peek(){
        assert size > 0;

        return peekImpl();
    }
    protected abstract Object peekImpl();


    public abstract Object remove();


    public abstract int countIf(Predicate<Object> predicate);

    public abstract Queue flatMap(Function<Object, Collection> func);

}
