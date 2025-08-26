package expression.parser;

import expression.*;

import java.util.ArrayList;

public class ExpressionParser implements TripleParser {
    ArrayList<Lexeme> workLexeme = new ArrayList<>();
    int position = 0;

    @Override
    public TripleExpression parse(String expression) {
        workLexeme = new ArrayList<>();
        position = 0;
        doAllLexeme(expression);
        position = 0;
        return bOr();
    }

    public boolean UnM(LexemeType t) {  //можно ведь просто поменять на обратные, намного короче
        return t == LexemeType.ADD || t == LexemeType.SUBTRACT || t == LexemeType.MULTIPLY || t == LexemeType.DIVIDE
                || t == LexemeType.NEGATE || t == LexemeType.L1 || t == LexemeType.T1 || t == LexemeType.BIN_AND
                || t == LexemeType.BIN_OR || t == LexemeType.BIN_XOR
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
                                    "72 Incorrect data " + inputExpession.charAt(--position));
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
            case 'x':
            case 'y':
            case 'z':
                workLexeme.add(new Lexeme(LexemeType.VARIBLE, String.valueOf(element)));
                break;
            case 'l':
                position++;
                if (position < inputExpession.length() && inputExpession.charAt(position) == '1') {
                    workLexeme.add(new Lexeme(LexemeType.L1, "l1"));
                } else {
                    throw new IllegalArgumentException("115 Incorrect data " + inputExpession.charAt(--position));
                }
                break;
            case 't':
                position++;
                if (position < inputExpession.length() && inputExpession.charAt(position) == '1') {
                    workLexeme.add(new Lexeme(LexemeType.T1, "t1"));
                } else {
                    throw new IllegalArgumentException("123 Incorrect data " + inputExpession.charAt(--position));
                }
                break;
            case '&':
                workLexeme.add(new Lexeme(LexemeType.BIN_AND, String.valueOf(element)));
                break;
            case '^':
                workLexeme.add(new Lexeme(LexemeType.BIN_XOR, String.valueOf(element)));
                break;
            case '|':
                workLexeme.add(new Lexeme(LexemeType.BIN_OR, String.valueOf(element)));
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
                throw new IllegalArgumentException("135 Incorrect data " + inputExpession.charAt(--position));
        }
        position++;
        return false;
    }

    int balanceBrackets = 0;

    public myExpression bOr() {
        myExpression current = bXor();
        while (true) {
            LexemeType elementType = workLexeme.get(position++).type;
            switch (elementType) {
                case BIN_OR:
                    current = new binOr(current, bXor());
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
                                "218 Incorrect data " + workLexeme.get(--position).inputElement);
                    }
                default:
                    throw new IllegalArgumentException("221 Incorrect data " + workLexeme.get(--position).inputElement);
            }
        }
    }

    public myExpression bXor() {
        myExpression current = bAnd();
        while (true) {
            LexemeType elementType = workLexeme.get(position++).type;
            switch (elementType) {
                case BIN_XOR:
                    current = new binXor(current, bAnd());
                    break;
                case RIGHT_BRACKET:
                case END:
                case BIN_OR:
                    position -= 1;
                    return current;
                default:
                    throw new IllegalArgumentException("243 Incorrect data " + workLexeme.get(--position).inputElement);
            }
        }
    }

    public myExpression bAnd() {
        myExpression current = expr();
        while (true) {
            LexemeType elementType = workLexeme.get(position++).type;
            switch (elementType) {
                case BIN_AND:
                    current = new binAnd(current, expr());
                    break;
                case RIGHT_BRACKET:
                case END:
                case BIN_OR:
                case BIN_XOR:
                    position -= 1;
                    return current;
                default:
                    throw new IllegalArgumentException("266 Incorrect data " + workLexeme.get(--position).inputElement);
            }
        }
    }

    public myExpression expr() {
        myExpression current = term();
        while (true) {
            LexemeType elementType = workLexeme.get(position++).type;
            switch (elementType) {
                case ADD:
                    current = new Add(current, term());
                    break;
                case SUBTRACT:
                    current = new Subtract(current, term());
                    break;
                case RIGHT_BRACKET:
                    position -= 1;
                    return current;
                case END:
                case BIN_OR:
                case BIN_XOR:
                case BIN_AND:
                    position -= 1;
                    return current;
                default:
                    throw new IllegalArgumentException("292 Incorrect data " + workLexeme.get(--position).inputElement);
            }
        }
    }

    public myExpression term() {
        myExpression current = factor();
        while (true) {
            LexemeType elementType = workLexeme.get(position++).type;
            switch (elementType) {
                case MULTIPLY:
                    current = new Multiply(current, factor());
                    break;
                case DIVIDE:
                    current = new Divide(current, factor());
                    break;
                case RIGHT_BRACKET:
                    position -= 1;
                    return current;
                case END:
                case ADD:
                case SUBTRACT:
                case BIN_OR:
                case BIN_XOR:
                case BIN_AND:
                    position -= 1;
                    return current;
                default:
                    throw new IllegalArgumentException("320 Incorrect data " + workLexeme.get(position).inputElement);
            }
        }
    }

    public myExpression factor() {
        Lexeme element = workLexeme.get(position++);
        switch (element.type) {
            case L1:
                return new UnaryL1(factor());
            case T1:
                return new UnaryT1(factor());
            case NEGATE:
                return new UnarMinus(factor());
            case NUMBER:
                return new Const(Integer.parseInt(element.inputElement));
            case LEFT_BRACKET:
                balanceBrackets += 1;
                myExpression ret = bOr();
                position += 1;
                return ret;
            case VARIBLE:
                return new Variable(element.inputElement.toString());
            default:
                throw new IllegalArgumentException("355 Incorrect data " + workLexeme.get(--position).inputElement);
        }
    }

    public static void main(String[] args) {
        // TripleExpression tp = (new ExpressionParser()).parse("x * (x - 2)*x + 1");
        // System.out.println(tp.toMiniString());
        ParserTest.main("hard", "Base", "Bitwise", "Ones");
    }
}
