package expression;

import java.math.BigInteger;

public interface myExpression extends TripleExpression{
    public int evaluate();

    public int evaluate(int a, int b);

    public StringBuilder toStringBuilder();

    public StringBuilder toMiniStringBuilder();

    public String toString();

    public BigInteger BigEvaluate();

    public int getPtiority();

    public boolean equals(Object a);

    public int hashCode();
}
