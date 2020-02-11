package expression.parser;
import expression.*;

import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

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

    public void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            super.nextChar();
        }
    }

    private CommonExpression parse(int previousLevel) {
        CommonExpression temp = parseValue();
        currentOperation = parseBinaryOperation();
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
        } else if (between('x', 'z')){
            String s = Character.toString(ch);
            nextChar();
            return (new Variable(s));
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
        for (Map.Entry<Character, List<BinaryParserConst>> entry : ExpressionMaps.BINARY_OPERATIONS.entrySet()) {
            if (test(entry.getKey())) {
                for (BinaryParserConst temp: entry.getValue()) {
                    if (temp.end.length() == 0) {
                        return temp;
                    }
                    if (test(temp.end.charAt(0))) {
                        expect(temp.end.substring(1));
                        return temp;
                    }
                }
                return null;
            }
        }
        return null;
    }

    private CommonExpression create(BinaryParserConst temp, CommonExpression past) {
        return temp.create.create(past, parse(temp.level));
    }
}
