package expression;

import java.math.BigInteger;

public class Const implements myExpression {
    private Number a;
    private int priority = 20;

    public Const(int a) {
        this.a = a;
    }

    public Const(BigInteger a) {
        this.a = a;
    }

    @Override
    public int getPtiority() {
        return this.priority;
    }

    @Override
    public int evaluate() {
        return (int)this.a;
    }

    @Override
    public int evaluate(int a) {
        return (int)this.a;
    }

    @Override
    public int evaluate(int a, int b) {
        return (int)this.a;
    }

    @Override
    public int evaluate(int a, int b, int c) {
        return (int)this.a;
    }

    @Override
    public StringBuilder toStringBuilder() {
        StringBuilder s = new StringBuilder();
        s.append(this.a);
        return s;
    }

    @Override
    public StringBuilder toMiniStringBuilder() {
        StringBuilder s = new StringBuilder();
        s.append(this.a);
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
        return (BigInteger)this.a;
    }

    @Override
    public BigInteger BigEvaluate() {
        return (BigInteger)this.a;
    }

    @Override
    public boolean equals(Object c) {
        if (c == null || !(c instanceof Const)) {
            return false;
        }
        Const b = (Const) c;
        if (this.getPtiority() == b.getPtiority()) {
            return this.a.equals(b.a);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
