package expression;

import java.math.BigInteger;

public class Add extends binOperation {
    private myExpression x;
    private myExpression y;
    private int priority = 1;

    public Add(myExpression x, myExpression y) {
        super(x, y, " + ");
        this.x = x;
        this.y = y;
    }

    @Override
    public int getPtiority() {
        return this.priority;
    }

    @Override
    public int evaluate() {
        return this.x.evaluate() + this.y.evaluate();
    }

    @Override
    public int evaluate(int a) {
        return this.x.evaluate(a) + this.y.evaluate(a);
    }

    @Override
    public int evaluate(int a, int b) {
        return this.x.evaluate(a, b) + this.y.evaluate(a, b);
    }

    @Override
    public int evaluate(int a, int b, int c) {
        return this.x.evaluate(a, b, c) + this.y.evaluate(a, b, c);
    }

    @Override
    public BigInteger evaluate(BigInteger a) {
        return this.x.evaluate(a).add(this.y.evaluate(a));
    }

    @Override
    public BigInteger BigEvaluate() {
        return this.x.BigEvaluate().add(this.y.BigEvaluate());
    }
}
