package expression.generic.operation;

import expression.generic.typesOfNumbers.DiffrentTypes;


public class UnarMinus<T> extends AbstractUnarOp<T> {
    private TripleExpressionGeneric<T> x;

    public UnarMinus(TripleExpressionGeneric<T> x, DiffrentTypes<T> mode) {
        super(x, mode);
        this.x = x;
    }

   @Override
    protected T eval(T x){
        return mode.negate(x);
   }
}
