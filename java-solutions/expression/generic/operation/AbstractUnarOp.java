package expression.generic.operation;

import expression.generic.typesOfNumbers.DiffrentTypes;
import java.util.Objects;

public abstract class AbstractUnarOp<T> implements TripleExpressionGeneric<T> {
    private TripleExpressionGeneric<T> x;
    protected DiffrentTypes<T> mode;

    public AbstractUnarOp(TripleExpressionGeneric<T> x, DiffrentTypes<T> mode) {
        this.x = Objects.requireNonNull(x);
        this.mode = mode;
    }

    protected abstract T eval(T x);

    @Override
    public T evaluate(T a, T b, T c) {
        return eval(x.evaluate(a, b, c));
    }
}