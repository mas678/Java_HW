package expression.exceptions;

import expression.CommonExpression;
import expression.Const;
import expression.Divide;

public class CheckedLogarithm extends Divide {
    public CheckedLogarithm(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int function(int a, int b) {
        if (a < 1 || b <= 1) {
            throw new MathException("Logarithm from none positive value");
        }
        int c = 0;
        while (a >= b) {
            c++;
            a /= b;
        }
        return c;
    }
}