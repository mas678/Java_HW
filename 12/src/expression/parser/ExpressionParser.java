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
        while (!testBinaryOperation(previousLevel)) {
            temp = parseSymbol(temp);
        }
        return temp;
    }

    private boolean testBinaryOperation(int previousLevel) {
        if (!ExpressionMaps.binaryOperationMap.containsKey(ch)) {
            return ch == ')' || ch == '\0';
        }
        return (ExpressionMaps.binaryOperationMap.get(ch).level <= previousLevel);
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
        throw error("No Value (bracket, number, variable or UnaryOperation)");
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

    private CommonExpression parseSymbol(CommonExpression past) {
        for (Map.Entry<Character, BinaryParserConst> temp : ExpressionMaps.binaryOperationMap.entrySet()) {
            if (test(temp.getKey())) {
                expect(temp.getValue().end);
                return temp.getValue().create.create
                        (past, parse(ExpressionMaps.binaryOperationMap.get(temp.getKey()).level));
            }
        }
        throw error("Expected Math sign");
    }
}
