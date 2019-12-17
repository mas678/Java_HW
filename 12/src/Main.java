import expression.CommonExpression;
import expression.TripleExpression;
import expression.parser.ExpressionParser;


public class Main {
    public static void main(String[] args) {
        final ExpressionParser parser = new ExpressionParser();
        TripleExpression x = parser.parse("reverse 12345");
        System.out.println(x.evaluate(0, 0, 0));
    }
}
