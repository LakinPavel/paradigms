package expression;

import java.math.BigInteger;
import java.util.Objects;

public abstract class AbstractUnarOp implements myExpression {
    private myExpression x;
    private int priority;
    private String sign;

    public AbstractUnarOp(myExpression x, String sign) {
        this.priority = priority;
        this.x = Objects.requireNonNull(x);
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
        s.append(this.sign).append('(').append(this.x.toStringBuilder()).append(')');
        return s;
    }

    @Override
    public StringBuilder toMiniStringBuilder() {
        StringBuilder s = new StringBuilder();
        s.append(sign);
        if (this.x.getPtiority() != this.getPtiority()) {
            s.append('(').append(this.x.toMiniStringBuilder()).append(')');
        } else {
            s.append(' ').append(this.x.toMiniStringBuilder());
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
    public int getPtiority() {
        return this.priority;
    }

    @Override
    public boolean equals(Object a) {
        if ((a == null || this == null) || !(a.getClass() == this.getClass())) {
            return false;
        }
        AbstractUnarOp b = (AbstractUnarOp) a;
        if (this.getPtiority() == b.getPtiority()) {
            return this.x.equals(b.x);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public BigInteger BigEvaluate() {
        return new BigInteger("0");
    }

    @Override
    public BigInteger evaluate(BigInteger a) {
        return new BigInteger("0");
    }
}