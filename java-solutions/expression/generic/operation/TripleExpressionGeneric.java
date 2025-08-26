package expression.generic.operation;
import expression.ToMiniString;
@FunctionalInterface
public interface TripleExpressionGeneric<T> extends ToMiniString{
    T evaluate (T x, T y, T z);
}
