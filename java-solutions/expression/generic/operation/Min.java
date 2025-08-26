package expression.generic.operation;

import expression.generic.typesOfNumbers.DiffrentTypes;

public class Min<T> extends binOperation<T> {
    private TripleExpressionGeneric<T> x;
    private TripleExpressionGeneric<T> y;

    public Min(TripleExpressionGeneric<T> x, TripleExpressionGeneric<T> y, DiffrentTypes<T> mode) {
        super(x, y, mode);
        this.x = x;
        this.y = y;
    }

    @Override
    protected T eval(T x, T y){
        return mode.min(x, y);
    }

}
