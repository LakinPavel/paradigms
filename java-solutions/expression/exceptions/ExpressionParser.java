package expression.exceptions;

import expression.*;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser implements TripleParser, ListParser {
    ArrayList<Lexeme> workLexeme = new ArrayList<>();
    int position = 0;

    @Override
    public TripleExpression parse(String expression) {
        return new TripleExpression() {
            @Override
            public int evaluate(int x, int y, int z) {
                return 0;
            }
        };
    }

    public ListExpression parse(String expression, List<String> v) {
        return new ListExpression() {
            @Override
            public int evaluate(List<Integer> variables) {
                return 0;
            }
        };
    }
}
