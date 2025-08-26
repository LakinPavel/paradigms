package expression;

import java.math.BigInteger;

public class UnaryL1 extends AbstractUnarOp {
    private myExpression x;
    private int priority = 20;

    public UnaryL1(myExpression x) {
        super(x, "l1");
        this.x = x;
    }

    @Override
    public int getPtiority() {
        return this.priority;
    }

    @Override
    public int evaluate() {
        // return -this.x.evaluate();
        return Integer.numberOfLeadingZeros(~this.x.evaluate());
    }

    @Override
    public int evaluate(int a) {
        return Integer.numberOfLeadingZeros(~this.x.evaluate(a));
    }

    @Override
    public int evaluate(int a, int b) {
        return Integer.numberOfLeadingZeros(~this.x.evaluate(a, b));
    }

    @Override
    public int evaluate(int a, int b, int c) {
        return Integer.numberOfLeadingZeros(~this.x.evaluate(a, b, c));
    }
}