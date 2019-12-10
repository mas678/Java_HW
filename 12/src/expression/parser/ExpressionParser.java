package expression.parser;

import expression.*;

import java.util.Map;

public class ExpressionParser {
    public static FullExpression parse(final String source) {
        return parse(new StringSource(source));
    }

    public static FullExpression parse(ExpressionSource source) {
        return new ExpressionParseFull(source).parse();
    }

    public static class ExpressionParseFull extends BaseParser {
        private int bracketCnt;
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
                '+', Add::new,
                '-', Subtract::new,
                '*', Multiply::new,
                '/', Divide::new,
                '<', ShiftLeft::new,
                '>', ShiftRight::new
        );

        public ExpressionParseFull(final ExpressionSource source) {
            super(source);
            nextChar();
        }

        public FullExpression parse() {
            ComIntSymb ans = new ComIntSymb();
            bracketCnt = 0;
            parseSomething(ans);
            while (!test('\0')) {
                parseElement(ans);
            }
            if (bracketCnt > 0) {
                throw error("missed \')\"");
            }
            if (ans.size() == 0) {
                throw error("No elements to parse");
            }
            review(ans, -1);
            return ans.getLastCommon();
        }


        private void parseSomething(ComIntSymb ans) {
            skipWhitespace();
            boolean c = isMinus();
            while (test('(')) {
                parseBracketBegin(ans, c);
                c = isMinus();
            }
            parseValue(ans, c);
            skipWhitespace();
            while (test(')')) {
                parseBracketEnd(ans);
            }
        }

        private void parseElement(ComIntSymb ans) {
            skipWhitespace();
            parseSymbol(ans);
            parseSomething(ans);
        }

        private void parseValue(ComIntSymb ans, boolean c) {
            if (between('0', '9')) {
                ans.add(new Const(parseNumber(c)), bracketCnt);
                return;
            } else if (between('x', 'z')){
                if (c) {
                    bracketHelper(ans);
                }
                ans.add(new Variable(Character.toString(ch)), bracketCnt);
                nextChar();
                return;
            }
            throw error("No Value (bracket, number or variable)");
        }

        private void parseSymbol(ComIntSymb ans) {
            for (Map.Entry<Character, String> temp : afterNumb.entrySet()) {
                if (test(temp.getKey())) {
                    expect(temp.getValue());
                    review(ans, levelMap.get(temp.getKey()));
                    ans.addMathSymbol(temp.getKey());
                    return;
                }
            }
            throw error("Expected Math sign");
        }

        private void parseBracketBegin(ComIntSymb ans, boolean c) {
            if (c) {
                bracketHelper(ans);
            }
            bracketCnt++;
            skipWhitespace();
        }

        private void parseBracketEnd(ComIntSymb ans) {
            review(ans, -1);
            bracketCnt--;
            if (bracketCnt < 0) {
                throw error("extra \")\"");
            }
            FullExpression com = ans.getLastCommon();
            int buck = ans.getLastBracket() - 1;
            ans.removeLastPair();
            ans.add(com, buck);
            skipWhitespace();
        }

        private void review(ComIntSymb ans, int symbolLevel) {
            while (ans.size() > 1 && ans.getPreLastBracket() == bracketCnt &&
                    levelMap.get(ans.getLastSymbol()) >= symbolLevel) {
                FullExpression temp = ans.getLastCommon();
                ans.removeLastPair();
                Character ch = ans.getLastSymbol();
                FullExpression newTemp = create.get(ch).create(ans.getLastCommon(), temp);
                ans.removeLastPair();
                ans.removeLastSymbol();
                ans.add(newTemp, bracketCnt);
            }
        }

        private Integer parseNumber(boolean c) {
            final StringBuilder sb = new StringBuilder();
            copyInteger(sb, c);
            try {
                return Integer.parseInt(sb.toString());
            } catch (NumberFormatException e) {
                throw error("Invalid number " + sb);
            }
        }

        private boolean isMinus() {
            int cnt = 0;
            while (test('-')) {
                cnt++;
                skipWhitespace();
            }
            return (cnt % 2 == 1);
        }

        private void bracketHelper(ComIntSymb ans) {
            ans.add(new Const(-1), bracketCnt);
            ans.addMathSymbol('*');
        }

        private void copyDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(ch);
                nextChar();
            }
        }

        private void copyInteger(final StringBuilder sb, boolean c) {
            if (c) {
                sb.append("-");
            }
            if (between('0', '9')) {
                copyDigits(sb);
            } else {
                throw error("Incorrect number");
            }
        }

        private void skipWhitespace() {
            while (test(' ') || test('\r') || test('\n') || test('\t')) {
            }
        }
    }
}
