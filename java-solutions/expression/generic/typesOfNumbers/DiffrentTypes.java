package expression.generic.typesOfNumbers;

public interface DiffrentTypes<T> {
    T add(T x, T y);
    T subtract(T x, T y);
    T multiply(T x, T y);
    T divide(T x, T t);
    T getValueInType(int x);
    T min(T x, T y);
    T max(T x, T y);
    T negate(T x);
    T parseElement(String x);
    T count(T x);
}
