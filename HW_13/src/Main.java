import expression.CommonExpression;
import expression.TripleExpression;
import expression.parser.ExpressionParser;


public class Main {
    public static void main(String[] args) {
        final ExpressionParser parser = new ExpressionParser();
        TripleExpression x = parser.parse(("x+2"));
        System.out.println(x.evaluate(2, 0, 0));
    }
}
