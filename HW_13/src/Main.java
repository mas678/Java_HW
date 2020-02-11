import expression.CommonExpression;
import expression.TripleExpression;
import expression.parser.ExpressionParser;


public class Main {
    public static void main(String[] args) {
        final ExpressionParser parser = new ExpressionParser();
        CommonExpression x = parser.parse("(x ** (y * z))");
        System.out.println(x.evaluate(1, 1, -10));
    }
}
