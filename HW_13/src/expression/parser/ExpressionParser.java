package expression.parser;
import expression.*;
import expression.exceptions.*;

public class ExpressionParser extends BaseParser implements Parser {
    private final int bracketLevel = -1;
    private BinaryParserConst currentOperation;

    @Override
    public CommonExpression parse(String expression) {
        newSource(new StringSource(expression));
        nextChar();
        CommonExpression ans = parse(bracketLevel);
        if (ch != '\0') {
            throw new ExpressionException("Missed '(' or Math sign");
        }
        return ans;
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            super.nextChar();
        }
    }

    private CommonExpression parse(int previousLevel) {
        currentOperation = null;
        CommonExpression temp = parseValue();
        if (currentOperation == null) {
            currentOperation = parseBinaryOperation();
        }
        while (testBinaryOperation(currentOperation, previousLevel)) {
            temp = create(currentOperation, temp);
        }
        return temp;
    }

    private boolean testBinaryOperation(BinaryParserConst op, int previousLevel) {
        return op != null && op.level > previousLevel;
    }

    private CommonExpression parseValue() {
        skipWhitespace();
        if (test('-')) {
            if (between('0', '9')) {
                return parseNumber(true);
            } else {
                return new Negate(parseValue());
            }
        } else if (between('0', '9')) {
            return parseNumber(false);
        } else if (between('x', 'z')) {
            String s = Character.toString(ch);
            nextChar();
            return (new Variable(s));
        } else if (test('l')) {
            expect("og2");
            if (!(between(' ', ' ') || between('-', '-') || between('(', '('))) {
                throw new ExpressionException("Oh ");
            }
            CommonExpression temp = parse(5);
            return new CheckedLogarithm(temp, new Const(2));
        } else if (test('p')) {
            expect("ow2");
            if (!(between(' ', ' ') || between('-', '-') || between('(', '('))) {
                throw new ExpressionException("Oh ");
            }
            CommonExpression temp = parse(5);
            return new CheckedPower(new Const(2), temp);
        } else if (test('(')) {
            CommonExpression temp = parse(bracketLevel);
            if (ch != ')') {
                throw error("Missed \")\"");
            }
            nextChar();
            return temp;
        }
        throw error("No Value (bracket, number, variable or unaryOperation)");
    }

    private CommonExpression parseNumber(boolean isMinus) {
        StringBuilder numb = new StringBuilder();
        if (isMinus) {
            numb.append("-");
        }
        while (between('0', '9')) {
            numb.append(ch);
            super.nextChar();
        }
        try {
            skipWhitespace();
            return new Const(Integer.parseInt(numb.toString()));
        } catch (NumberFormatException e) {
            throw error("Illegal number " + e);
        }
    }

    private BinaryParserConst parseBinaryOperation() {
        skipWhitespace();
        if (test('*')) {
            if (test('*')) {
                return new BinaryParserConst(4, CheckedPower::new, "*");
            }
            return new BinaryParserConst(3, CheckedMultiply::new, "");
        } else if (test('/')) {
            if (test('/')) {
                return new BinaryParserConst(4, CheckedLogarithm::new, "*");
            }
            return new BinaryParserConst(3, CheckedDivide::new, "");
        } else if (test('+')) {
            return new BinaryParserConst(2, CheckedAdd::new, "");
        } else if (test('-')) {
            return new BinaryParserConst(2, CheckedSubtract::new, "");
        }
        return null;
    }

    private CommonExpression create(BinaryParserConst temp, CommonExpression past) {
        return temp.create.create(past, parse(temp.level));
    }
}
