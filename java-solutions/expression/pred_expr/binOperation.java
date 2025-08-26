package expression;

import java.math.BigInteger;
import java.util.Objects;

public abstract class binOperation implements myExpression {
    private myExpression x;
    private myExpression y;
    private int priority;
    private String sign;

    public binOperation(myExpression x, myExpression y, String sign) {
        this.priority = 7;
        this.x = Objects.requireNonNull(x);
        this.y = Objects.requireNonNull(y);
        this.sign = sign;
    }

    @Override
    public int evaluate() {
        return 0;
    }

    @Override
    public int evaluate(int a) {
        return evaluate();
    }

    @Override
    public int evaluate(int a, int b) {
        return evaluate();
    }

    @Override
    public int evaluate(int a, int b, int c) {
        return evaluate();
    }

    @Override
    public StringBuilder toStringBuilder() {
        StringBuilder s = new StringBuilder();
        s.append('(').append(this.x.toStringBuilder()).append(this.sign).append(this.y.toStringBuilder()).append(')');
        return s;
    }

    @Override
    public StringBuilder toMiniStringBuilder() {
        StringBuilder s = new StringBuilder();
        if (this.x.getPtiority() < this.getPtiority() - 1) {
            s.append('(').append(this.x.toMiniStringBuilder()).append(')');
        } else {
            s.append(this.x.toMiniStringBuilder());
        }
        s.append(sign);
        if ((this.y.getPtiority() < this.getPtiority()) || (this.getPtiority() == 2 && this.y.getPtiority() == 2)
                || ((this.getPtiority() == 11 || this.getPtiority() == 10) && this.y.getPtiority() == 11)) {
            s.append('(').append(this.y.toMiniStringBuilder()).append(')');
        } else {
            s.append(this.y.toMiniStringBuilder());
        }
        return s;
    }

    @Override
    public String toString() {
        return toStringBuilder().toString();
    }

    @Override
    public String toMiniString() {
        return toMiniStringBuilder().toString();
    }

    @Override
    public BigInteger evaluate(BigInteger a) {
        return BigEvaluate();
    }

    @Override
    public int getPtiority() {
        return this.priority;
    }

    @Override
    public boolean equals(Object a) {
        if (a == null || this == null || !(a instanceof binOperation)) {
            return false;
        }
        binOperation b = (binOperation) a;
        if (this.getPtiority() == b.getPtiority()) {
            return this.x.equals(b.x) && this.y.equals(b.y);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}