import expression.CommonExpression;
import expression.TripleExpression;
import expression.parser.ExpressionParser;


public class Main {
    public static void main(String[] args) {
        final ExpressionParser parser = new ExpressionParser();
        TripleExpression x = parser.parse(("10 20"));
        System.out.println(x.evaluate(2, -2147483647, 0));
    }
}
