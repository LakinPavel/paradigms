package expression.generic;

import expression.generic.operation.TripleExpressionGeneric;
import expression.generic.typesOfNumbers.*;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private final Map<String, DiffrentTypes<?>> typeMode = Map.of(
            "i", new IntegerMath(true),
            "u", new IntegerMath(false),
            "d", new DoubleMath(),
            "bi", new BigIntegerMath(),
            "b", new BytesMath(),
            "bool", new BooleanMath()
    );

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
            throws Exception {
        return finalTable(typeMode.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] finalTable(DiffrentTypes<T> myMode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        int x = x2 - x1 + 1;
        int y = y2 - y1 + 1;
        int z = z2 - z1 + 1;
        Object[][][] answer = new Object[x][y][z];
        TripleExpressionGeneric<T> exp = new ExpressionParserGeneric<T>().parse(myMode, expression);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    try {
                        answer[i][j][k] = exp.evaluate(myMode.getValueInType(x1 + i), myMode.getValueInType(y1 + j), myMode.getValueInType(z1 + k));
                    } catch (RuntimeException a) {
                        answer[i][j][k] = null;
                    }
                }
            }
        }
        return answer;
    }


}
