package expression.exceptions;

import expression.Multiply;
import expression.myExpression;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(myExpression x, myExpression y) {
        super(x, y);
    }
}