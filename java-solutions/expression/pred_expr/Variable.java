package expression;

import java.math.BigInteger;

public class Variable implements myExpression {
    private String name;
    private int priority = 20;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int getPtiority() {
        return this.priority;
    }

    @Override
    public int evaluate() {
        return 0;
    }

    @Override
    public int evaluate(int a) {
        return a;
    }

    @Override
    public int evaluate(int a, int b) {
        if (this.name.equals("x")) {
            return a;
        }
        return b;
    }

    @Override
    public int evaluate(int a, int b, int c) {
        if (this.name.equals("x")) {
            return a;
        } else if (this.name.equals("y")) {
            return b;
        }
        return c;
    }

    @Override
    public StringBuilder toStringBuilder() {
        StringBuilder s = new StringBuilder();
        s.append(this.name);
        return s;
    }

    @Override
    public StringBuilder toMiniStringBuilder() {
        StringBuilder s = new StringBuilder();
        s.append(this.name);
        return s;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String toMiniString() {
        return this.name;
    }

    @Override
    public BigInteger evaluate(BigInteger a) {
        return a;
    }

    @Override
    public BigInteger BigEvaluate() {
        return new BigInteger("0");
    }

    @Override
    public boolean equals(Object a) {
        if (a == null || this == null || !(a instanceof Variable)) {
            return false;
        }
        if (a.toString().isEmpty() || this.toString().isEmpty()) {
            return false;
        } else {
            return this.toString().equals(a.toString());
        }
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
