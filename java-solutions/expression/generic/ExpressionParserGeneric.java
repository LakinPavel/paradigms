package expression.generic;


import expression.generic.operation.Add;
import expression.generic.operation.Const;
import expression.generic.operation.Divide;
import expression.generic.operation.Multiply;
import expression.generic.operation.Subtract;
import expression.generic.operation.TripleExpressionGeneric;
import expression.generic.operation.UnarMinus;
import expression.generic.operation.Variable;
import expression.generic.typesOfNumbers.DiffrentTypes;
import expression.generic.operation.*;


import java.util.ArrayList;

public class ExpressionParserGeneric<T> {
    ArrayList<Lexeme> workLexeme = new ArrayList<>();
    int position = 0;

    public TripleExpressionGeneric<T> parse(DiffrentTypes<T> mode, String expression) {
        workLexeme = new ArrayList<>();
        position = 0;
        doAllLexeme(expression);
        position = 0;
        return expr(mode);
    }


    public boolean UnM(LexemeType t) {
        return t == LexemeType.ADD || t == LexemeType.SUBTRACT || t == LexemeType.MULTIPLY || t == LexemeType.DIVIDE
                || t == LexemeType.NEGATE || t == LexemeType.MIN || t == LexemeType.MAX
                || t == LexemeType.LEFT_BRACKET;
    }

    public static String chToStr(Character a) {
        return String.valueOf(a);
    }

    public boolean between(Character a) {
        return ('a' <= a && a <= 'z') || ('0' <= a && a <= '9');
    }

    public void doAllLexeme(String inputExpression) {
        while (!addLexeme(inputExpression)) {
        }
    }

    public boolean addLexeme(String inputExpession) {
        while (position < inputExpession.length() && Character.isWhitespace(inputExpession.charAt(position))) {
            position++;
        }
        if (position >= inputExpession.length()) {
            workLexeme.add(new Lexeme(LexemeType.END, Integer.toString(position)));
            return true;
        }
        Character element = inputExpession.charAt(position);
        switch (element) {
            case '(':
                workLexeme.add(new Lexeme(LexemeType.LEFT_BRACKET, String.valueOf(element)));
                break;
            case ')':
                workLexeme.add(new Lexeme(LexemeType.RIGHT_BRACKET, String.valueOf(element)));
                break;
            case '+':
                workLexeme.add(new Lexeme(LexemeType.ADD, String.valueOf(element)));
                break;
            case '-':
                if (workLexeme.isEmpty() || UnM(workLexeme.get(workLexeme.size() - 1).type)) {
                    position++;
                    if (position < inputExpession.length() && Character.isDigit(inputExpession.charAt(position))) {
                        StringBuilder sb = new StringBuilder();
                        sb.append('-');
                        while (position < inputExpession.length()
                                && Character.isDigit(inputExpession.charAt(position))) {
                            sb.append(inputExpession.charAt(position));
                            position++;
                        }
                        workLexeme.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                        return false;
                    } else {
                        while (position < inputExpession.length()
                                && Character.isWhitespace(inputExpession.charAt(position))) {
                            position++;
                        }
                        if (inputExpession.charAt(position) == '(' || between(inputExpession.charAt(position))
                                || inputExpession.charAt(position) == '-') {
                            workLexeme.add(new Lexeme(LexemeType.NEGATE, "-"));
                            position--;
                        } else {
                            throw new IllegalArgumentException(
                                    "93 Incorrect data " + inputExpession.charAt(--position));
                        }
                    }
                } else {
                    workLexeme.add(new Lexeme(LexemeType.SUBTRACT, String.valueOf(element)));
                }
                break;
            case '*':
                workLexeme.add(new Lexeme(LexemeType.MULTIPLY, String.valueOf(element)));
                break;
            case '/':
                workLexeme.add(new Lexeme(LexemeType.DIVIDE, String.valueOf(element)));
                break;
            case 'c':
                position++;
                if (position < inputExpession.length() && inputExpession.charAt(position) == 'o') {
                    position++;
                    if (position < inputExpession.length() && inputExpession.charAt(position) == 'u') {
                        position++;
                        if (position < inputExpession.length() && inputExpession.charAt(position) == 'n') {
                            position++;
                            if (position < inputExpession.length() && inputExpession.charAt(position) == 't') {
                                workLexeme.add(new Lexeme(LexemeType.COUNT, "count"));
                                break;
                            } else {
                                throw new IllegalArgumentException("118 Incorrect data " + inputExpession.charAt(--position));
                            }
                        } else {
                            throw new IllegalArgumentException("121 Incorrect data " + inputExpession.charAt(--position));
                        }
                    } else {
                        throw new IllegalArgumentException("124 Incorrect data " + inputExpession.charAt(--position));
                    }
                } else {
                    throw new IllegalArgumentException("127 Incorrect data " + inputExpession.charAt(--position));
                }
            case 'm':
                position++;
                if (position < inputExpession.length() && inputExpession.charAt(position) == 'i') {
                    position++;
                    if (position < inputExpession.length() && inputExpession.charAt(position) == 'n') {
                        workLexeme.add(new Lexeme(LexemeType.MIN, String.valueOf(element)));
                        break;
                    } else {
                        throw new IllegalArgumentException("137 Incorrect data " + inputExpession.charAt(--position));
                    }
                } else if (position < inputExpession.length() && inputExpession.charAt(position) == 'a') {
                    position++;
                    if (position < inputExpession.length() && inputExpession.charAt(position) == 'x') {
                        workLexeme.add(new Lexeme(LexemeType.MAX, String.valueOf(element)));
                        break;
                    } else {
                        throw new IllegalArgumentException("145 Incorrect data " + inputExpession.charAt(--position));
                    }
                } else {
                    throw new IllegalArgumentException("148 Incorrect data " + inputExpession.charAt(--position));
                }
            case 'x':
            case 'y':
            case 'z':
                workLexeme.add(new Lexeme(LexemeType.VARIBLE, String.valueOf(element)));
                break;
            default:
                if (Character.isDigit(inputExpession.charAt(position))) {
                    StringBuilder sb = new StringBuilder();
                    while (position < inputExpession.length() && Character.isDigit(inputExpession.charAt(position))) {
                        sb.append(inputExpession.charAt(position));
                        position++;
                    }
                    workLexeme.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    return false;
                }
                throw new IllegalArgumentException("165 Incorrect data " + inputExpession.charAt(--position));
        }
        position++;
        return false;
    }

    int balanceBrackets = 0;

    public TripleExpressionGeneric<T> expr(DiffrentTypes<T> mode) {
        TripleExpressionGeneric<T> current = term(mode);
        while (true) {
            LexemeType elementType = workLexeme.get(position++).type;
            switch (elementType) {
                case ADD:
                    current = new Add<>(current, term(mode), mode);
                    break;
                case SUBTRACT:
                    current = new Subtract<>(current, term(mode), mode);
                    break;
                case MIN:
                    current = new Min<>(current, term(mode), mode);
                    break;
                case MAX:
                    current = new Max<>(current, term(mode), mode);
                    break;
                case RIGHT_BRACKET:
                    balanceBrackets -= 1;
                    position -= 1;
                    return current;
                case END:
                    if (balanceBrackets == 0) {
                        position -= 1;
                        return current;
                    } else {
                        throw new IllegalArgumentException(
                                "200 Incorrect data " + workLexeme.get(--position).inputElement);
                    }
                default:
                    throw new IllegalArgumentException("203 Incorrect data " + workLexeme.get(--position).inputElement);
            }
        }
    }

    public TripleExpressionGeneric<T> term(DiffrentTypes<T> mode) {
        TripleExpressionGeneric<T> current = factor(mode);
        while (true) {
            LexemeType elementType = workLexeme.get(position++).type;
            switch (elementType) {
                case MULTIPLY:
                    current = new Multiply<>(current, factor(mode), mode);
                    break;
                case DIVIDE:
                    current = new Divide<>(current, factor(mode), mode);
                    break;
                case RIGHT_BRACKET:
                    position -= 1;
                    return current;
                case END:
                case ADD:
                case SUBTRACT:
                case MAX:
                case MIN:
                    position -= 1;
                    return current;
                default:
                    throw new IllegalArgumentException("230 Incorrect data " + workLexeme.get(position).inputElement);
            }
        }
    }

    public TripleExpressionGeneric<T> factor(DiffrentTypes<T> mode) {
        Lexeme element = workLexeme.get(position++);
        switch (element.type) {
            case NEGATE:
                return new UnarMinus<>(factor(mode), mode);
            case NUMBER:
                return new Const<>(mode.parseElement(element.inputElement));
            case COUNT:
                return new Count<>(factor(mode), mode);
            case LEFT_BRACKET:
                balanceBrackets += 1;
                TripleExpressionGeneric<T> ret = expr(mode);
                position += 1;
                return ret;
            case VARIBLE:
                return new Variable<>(element.inputElement);
            default:
                throw new IllegalArgumentException("252 Incorrect data " + element.type + " " + workLexeme.get(--position).inputElement);
        }
    }

}
