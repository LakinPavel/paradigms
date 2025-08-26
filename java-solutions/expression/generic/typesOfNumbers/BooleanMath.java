package expression.generic.typesOfNumbers;

import expression.generic.exceptions.DivisionByZeroExceptions;

public class BooleanMath implements DiffrentTypes<Boolean> {
    @Override
    public Boolean getValueInType(int x) {
        return x != 0;
//        if (x == 0) {
//            return false;
//        }
//        return true;
    }

    @Override
    public Boolean add(Boolean x, Boolean y) {
        if (x.equals(false) && y.equals(false)) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean subtract(Boolean x, Boolean y) {
        if ((x.equals(true) && y.equals(false)) || (x.equals(false) && y.equals(true))) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean multiply(Boolean x, Boolean y) {
        return Boolean.logicalAnd(x, y);
    }

    @Override
    public Boolean divide(Boolean x, Boolean y) {
        if (!y) {
            throw new DivisionByZeroExceptions();
        }
        return Boolean.logicalAnd(x, y);
    }

    @Override
    public Boolean min(Boolean x, Boolean y) {
        if (!x || !y) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean max(Boolean x, Boolean y) {
        if (x || y) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean parseElement(String x) {
        if (Integer.parseInt(x) == 0) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean negate(Boolean x) {
        return x;
    }

    @Override
    public Boolean count(Boolean x) {
        return x;
    }
}
