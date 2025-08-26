package expression.exceptions;

import expression.Subtract;
import expression.myExpression;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(myExpression x, myExpression y) {
        super(x, y);
    }
}