package expression;

import java.math.BigInteger;
import java.util.List;

public class Const implements myExpression, Expression {

    public Const(int i) {

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
