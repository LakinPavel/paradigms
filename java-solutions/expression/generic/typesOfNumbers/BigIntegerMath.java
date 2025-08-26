package expression.generic.typesOfNumbers;

import expression.generic.exceptions.DivisionByZeroExceptions;

import java.math.BigInteger;

public class BigIntegerMath implements  DiffrentTypes<BigInteger> {
    @Override
    public BigInteger add(BigInteger x, BigInteger y){
        return x.add(y);
    }
    @Override
    public BigInteger subtract(BigInteger x, BigInteger y){
        return x.subtract(y);
    }
    @Override
    public BigInteger multiply(BigInteger x, BigInteger y){
        return x.multiply(y);
    }
    @Override
    public BigInteger divide(BigInteger x, BigInteger y){
        if (y.equals(BigInteger.ZERO)){
            throw new DivisionByZeroExceptions();
        }
        return x.divide(y);
    }
    @Override
    public BigInteger getValueInType(int x){ return BigInteger.valueOf(x); }
    @Override
    public BigInteger min(BigInteger x, BigInteger y) { return x.min(y); }
    @Override
    public BigInteger max(BigInteger x, BigInteger y) { return x.max(y); }
    @Override
    public BigInteger parseElement(String x) { return new BigInteger(x); }
    @Override
    public BigInteger negate(BigInteger x) { return x.negate(); }
    @Override
    public BigInteger count(BigInteger x) { return BigInteger.valueOf(x.bitCount()); }
}
