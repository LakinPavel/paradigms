package expression.generic.typesOfNumbers;

public class DoubleMath implements DiffrentTypes<Double>{
    @Override
    public Double add(Double x, Double y){
        return x + y;
    }
    @Override
    public Double subtract(Double x, Double y){
        return x - y;
    }
    @Override
    public Double multiply(Double x, Double y){
        return x * y;
    }
    @Override
    public Double divide(Double x, Double y){

        return x / y;
    }
    @Override
    public Double getValueInType(int x){ return (double) x; }
    @Override
    public Double min(Double x, Double y) { return Double.min(x, y); }
    @Override
    public Double max(Double x, Double y) { return Double.max(x, y); }
    @Override
    public Double parseElement(String x) { return Double.parseDouble(x); }
    @Override
    public Double negate(Double x) { return -x; }
    @Override
    public Double count(Double x){ return Double.valueOf(Long.bitCount(Double.doubleToLongBits(x))); }
}
