package expression;

import java.math.BigInteger;
import java.util.List;

public interface myExpression extends TripleExpression, ListExpression{
    int evaluate(int variables);
}
