package expression.generic.operation;

import expression.generic.typesOfNumbers.DiffrentTypes;


public class Count<T> extends AbstractUnarOp<T> {
    private TripleExpressionGeneric<T> x;

    public Count(TripleExpressionGeneric<T> x, DiffrentTypes<T> mode) {
        super(x, mode);
        this.x = x;
    }

    @Override
    protected T eval(T x){
        return mode.count(x);
    }
}