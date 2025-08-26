package expression;

import java.math.BigInteger;

public class UnarMinus extends AbstractUnarOp {
    private myExpression x;
    private int priority = 20;

    public UnarMinus(myExpression x) {
        super(x, "-");
        this.x = x;
    }

    @Override
    public int getPtiority() {
        return this.priority;
    }

    @Override
    public int evaluate() {
        return -this.x.evaluate();
    }

    @Override
    public int evaluate(int a) {
        return -this.x.evaluate(a);
    }

    @Override
    public int evaluate(int a, int b) {
        return -this.x.evaluate(a, b);
    }

    @Override
    public int evaluate(int a, int b, int c) {
        return -this.x.evaluate(a, b, c);
    }
}
