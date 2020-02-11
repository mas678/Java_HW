package expression.parser;

import expression.*;

import java.util.Map;

public class ExpressionParser extends BaseParser implements Parser {
    private final int bracketLevel = -1;

    @Override
    public TripleExpression parse(String expression) {
        newSource(new StringSource(expression));
        nextChar();
        return parse(bracketLevel);
    }

    public void nextChar() {
        do {
            super.nextChar();
        } while (Character.isWhitespace(ch));
    }

    private CommonExpression parse(int previousLevel) {
        CommonExpression temp = parseValue();
        while (testBinaryOperation(previousLevel)) {
            temp = parseBinaryOperation(temp);
        }
        return temp;
    }

    private boolean testBinaryOperation(int previousLevel) {
        BinaryParserConst op = ExpressionMaps.BINARY_OPERATIONS.get(ch);
        return op != null || op.level > previousLevel;
    }


    private CommonExpression parseValue() {
        if (test('-')) {
            if (between('0', '9')) {
                return parseNumber(true);
            } else {
                return new Minus(parseValue());
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
        } else if (ExpressionMaps.unaryOperationMap.containsKey(ch)) {
            for (Map.Entry<Character, UnaryParserConst> temp : ExpressionMaps.unaryOperationMap.entrySet()) {
                if (test(temp.getKey())) {
                    expect(temp.getValue().end);
                    return temp.getValue().create.create(parse(temp.getValue().level));
                }
            }
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
            nextChar();
        }
        try {
            return new Const(Integer.parseInt(numb.toString()));
        } catch (NumberFormatException e) {
            throw error("Illegal number " + e);
        }
    }

    private CommonExpression parseBinaryOperation(CommonExpression past) {
        for (Map.Entry<Character, BinaryParserConst> entry : ExpressionMaps.BINARY_OPERATIONS.entrySet()) {
            if (test(entry.getKey())) {
                expect(entry.getValue().end);
                return entry.getValue().create.create(past, parse(entry.getValue().level));
            }
        }
        throw error("Expected Math sign");
    }
}
