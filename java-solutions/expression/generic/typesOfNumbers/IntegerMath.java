package expression.generic.typesOfNumbers;


import expression.generic.exceptions.*;

public class IntegerMath implements DiffrentTypes<Integer> {
    private final boolean checking;

    public IntegerMath(boolean checking) {
        this.checking = checking;
    }

    @Override
    public Integer add(Integer x, Integer y) {
        if (checking) {
            if ((long) x + (long) y > Integer.MAX_VALUE || (long) x + (long) y < Integer.MIN_VALUE) {
                throw new OverflowExceptions();
            }
        }
        return x + y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        if (checking) {
            if ((long) x - (long) y > Integer.MAX_VALUE || (long) x - (long) y < Integer.MIN_VALUE) {
                throw new OverflowExceptions();
            }
        }
        return x - y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        if (checking) {
            if ((long) x * (long) y > Integer.MAX_VALUE || (long) x * (long) y < Integer.MIN_VALUE) {
                throw new OverflowExceptions();
            }
        }
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        if (y == 0) {
            throw new DivisionByZeroExceptions();
        }
        if (checking) {

        }
        return x / y;
    }

    @Override
    public Integer getValueInType(int x) {
        return x;
    }

    @Override
    public Integer min(Integer x, Integer y) {
        return Integer.min(x, y);
    }

    @Override
    public Integer max(Integer x, Integer y) {
        return Integer.max(x, y);
    }

    @Override
    public Integer parseElement(String x) {
        return Integer.parseInt(x);
    }

    @Override
    public Integer negate(Integer x) {
        if (checking) {
            if (x == Integer.MIN_VALUE) {
                throw new OverflowExceptions();
            }
        }
        return -x;
    }

    @Override
    public Integer count(Integer x) {
        int count = 0;
        while (x != 0) {
            x &= (x - 1);
            count++;
        }
        return count;
    }
}
