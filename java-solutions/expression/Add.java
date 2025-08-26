package expression;

import java.math.BigInteger;
import java.util.List;

public class Add implements myExpression, Expression {

    public Add (myExpression a, myExpression b) {

    }

    @Override
    public int evaluate(List<Integer> variables) {
        return 0;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return 0;
    }

    @Override
    public int evaluate(int variables) {
        return 0;
    }
}
