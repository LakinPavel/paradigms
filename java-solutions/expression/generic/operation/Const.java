package expression.generic.operation;


public class Const<T> implements TripleExpressionGeneric<T> {
    private T a;

    public Const(T x) {
        this.a = x;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return a;
    }
}
