package expression.parser;

import expression.*;

import java.util.Map;

public class ExpressionParser extends BaseParser implements Parser {
    private final int bracketLevel = -1;

    @Override
    public TripleExpression parse(String expression) {
        newSource(new StringSource(expression));
        nextChar();
        TripleExpression ans = parse(bracketLevel);
        if (ch == ')') {
            throw error("Extra \")\"");
        }
        return ans;
    }

    public void nextChar() {
        do {
            super.nextChar();
        } while (Character.isWhitespace(ch));
    }

    private void next() {
        if (Character.isWhitespace(ch)) {
            nextChar();
        }
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
                return new Negate(parseValue());
            }
        } else if (between('0', '9')) {
            return parseNumber(false);
        } else if (ExpressionMaps.Variables.containsKey(ch)){
            String s = ExpressionMaps.Variables.get(ch);
            expect(s);
            next();
            return new Variable(s);
        } else if (test('(')) {
            CommonExpression temp = parse(bracketLevel);
            if (ch == '\0') {
                throw error("Missed \")\"");
            }
            nextChar();
            return temp;
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
            super.nextChar();
        }
        try {
            next();
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
                        (past, parse(temp.getValue().level));
            }
        }
        throw error("Expected Math sign");
    }
}
