package expression.generic.typesOfNumbers;

import expression.generic.exceptions.DivisionByZeroExceptions;

public class BytesMath implements DiffrentTypes<Byte>{
    @Override
    public Byte add(Byte x, Byte y){ return (byte)(x + y); }
    @Override
    public Byte subtract(Byte x, Byte y){ return (byte)(x - y); }
    @Override
    public Byte multiply(Byte x, Byte y){ return (byte)(x * y); }
    @Override
    public Byte divide(Byte x, Byte y){
        if (y == 0){
            throw new DivisionByZeroExceptions();
        }
        return (byte) (x / y);
    }
    @Override
    public Byte getValueInType(int x){ return (byte) x; }
    @Override
    public Byte min(Byte x, Byte y) { return (byte) Math.min(x, y); }
    @Override
    public Byte max(Byte x, Byte y) { return (byte) Math.max(x, y); }
    @Override
    public Byte parseElement(String x) {return Byte.valueOf(x); }
    @Override
    public Byte negate(Byte x) { return (byte) -x; }
    @Override
    public Byte count(Byte x) { return (byte) Integer.bitCount(Byte.toUnsignedInt(x)); }
}
