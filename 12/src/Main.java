import expression.FullExpression;
import expression.parser.ExpressionParser;


public class Main {
    public static void main(String[] args) {
        final ExpressionParser parser = new ExpressionParser();
        FullExpression x = parser.parse("x*y+(z-1   )/10");
        System.out.println(x);
    }
}
