package expression.parser;

import expression.*;
import expression.exceptions.*;

import java.util.Map;

public class ExpressionParser extends BaseParser implements Parser {
    private final int bracketLevel = -1;

    private final Map<Character, String> oneVar = Map.of(
            'a', "bs",
            's', "quare"
    );

    private final Map<Character, OneVariable> createOneVar = Map.of(
            'a', Abs::new,
            's', Square::new
    );

    private final Map<Character, String> afterNumb = Map.of(
            '*', "",
            '-', "",
            '+', "",
            '/', "",
            '>', ">",
            '<', "<"
    );

    private final Map<Character, Integer> levelMap = Map.of(
            '*', 3,
            '-', 2,
            '+', 2,
            '/', 3,
            '<', 1,
            '>', 1
    );

    private final Map<Character, Booking> create = Map.of(
            '+', CheckedAdd::new,
            '-', CheckedSubtract::new,
            '*', CheckedMultiply::new,
            '/', CheckedDivide::new
    );


    @Override
    public TripleExpression parse(String expression) {
        newSource(new StringSource(expression));
        nextChar();
        TripleExpression ans = parse(bracketLevel);
        if (ch != '\0') {
            throw error("waste \")\"");
        }
        return ans;
    }

    public void nextChar() {
        do {
            super.nextChar();
        } while (Character.isWhitespace(ch));
    }

    private CommonExpression parse(int previousLevel) {
        CommonExpression temp = parseValue();
        while (!testSymbol(previousLevel)) {
            temp = parseSymbol(temp);
        }
        return temp;
    }

    private boolean testSymbol(int previousLevel) {
        if (!afterNumb.containsKey(ch)) {
            return ch == ')' || ch == '\0';
        }
        return (levelMap.get(ch) <= previousLevel);
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
        } else if (between('x', 'z')){
            String s = Character.toString(ch);
            nextChar();
            return (new Variable(s));
        } else if (test('(')) {
            CommonExpression temp = parse(bracketLevel);
            if (ch == '\0') {
                throw error("Missed \")\"");
            }
            nextChar();
            return temp;
        } else if (oneVar.containsKey(ch)) {
            for (Map.Entry<Character, String> temp : oneVar.entrySet()) {
                if (test(temp.getKey())) {
                    expect(temp.getValue());
                    return createOneVar.get(temp.getKey()).create(parseValue());
                }
            }
        }
        throw error("No Value (bracket, number or variable)");
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
            if (Character.isWhitespace(ch)) {
                nextChar();
            }
            return new Const(Integer.parseInt(numb.toString()));
        } catch (NumberFormatException e) {
            throw error("Illegal number " + e);
        }
    }

    private CommonExpression parseSymbol(CommonExpression past) {
        for (Map.Entry<Character, String> temp : afterNumb.entrySet()) {
            if (test(temp.getKey())) {
                expect(temp.getValue());
                return create.get(temp.getKey()).create(past, parse(levelMap.get(temp.getKey())));
            }
        }
        throw error("Expected Math sign");
    }
}
