import expression.CommonExpression;
import expression.TripleExpression;
import expression.parser.ExpressionParser;


public class Main {
    public static void main(String[] args) {
        final ExpressionParser parser = new ExpressionParser();
        TripleExpression x = parser.parse("x*y+(z-1   )/10");
        System.out.println(x);
    }
}
