package expression.exceptions;

import expression.Add;
import expression.myExpression;

public class CheckedAdd extends Add {
    public CheckedAdd(myExpression x, myExpression y) {
        super(x, y);
    }
}
