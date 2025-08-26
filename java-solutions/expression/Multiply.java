package expression;

import java.math.BigInteger;
import java.util.List;

public class Multiply implements myExpression, Expression {
    public Multiply (myExpression a, myExpression b) {

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
