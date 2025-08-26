package expression.generic.operation;

import expression.generic.typesOfNumbers.DiffrentTypes;

import java.util.Objects;

public abstract class binOperation<T> implements TripleExpressionGeneric<T> {
    private TripleExpressionGeneric<T> x;
    private TripleExpressionGeneric<T> y;

    protected DiffrentTypes<T> mode;


    public binOperation(TripleExpressionGeneric<T> x, TripleExpressionGeneric<T> y, DiffrentTypes<T> mode) {
        this.mode = mode;
        this.x = Objects.requireNonNull(x);
        this.y = Objects.requireNonNull(y);
    }

    protected abstract T eval(T x, T y);

    @Override
    public T evaluate(T a, T b, T c) {
        return eval(x.evaluate(a, b, c), y.evaluate(a, b, c));
    }

}