package expression.exceptions;

import expression.UnarMinus;
import expression.myExpression;

public class CheckedNegate extends UnarMinus {
    public CheckedNegate(myExpression x) {
        super(x);
    }
}
