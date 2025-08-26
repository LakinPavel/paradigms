package expression.exceptions;

import expression.Divide;
import expression.myExpression;

public class CheckedDivide extends Divide {
    public CheckedDivide(myExpression x, myExpression y) {
        super(x, y);
    }
}