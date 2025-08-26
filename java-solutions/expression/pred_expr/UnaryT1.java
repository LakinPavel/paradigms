package expression;

import java.math.BigInteger;

public class UnaryT1 extends AbstractUnarOp {
    private myExpression x;
    private int priority = 20;

    public UnaryT1(myExpression x) {
        super(x, "t1");
        this.x = x;
    }

    @Override
    public int getPtiority() {
        return this.priority;
    }

    @Override
    public int evaluate() {
        return Integer.numberOfTrailingZeros(~this.x.evaluate());
    }

    @Override
    public int evaluate(int a) {
        return Integer.numberOfTrailingZeros(~this.x.evaluate(a));
    }

    @Override
    public int evaluate(int a, int b) {
        return Integer.numberOfTrailingZeros(~this.x.evaluate(a, b));
    }

    @Override
    public int evaluate(int a, int b, int c) {
        return Integer.numberOfTrailingZeros(~this.x.evaluate(a, b, c));
    }
}