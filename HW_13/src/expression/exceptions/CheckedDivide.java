package expression.exceptions;

import expression.CommonExpression;
import expression.Divide;

public class CheckedDivide extends Divide {
    public CheckedDivide(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int function(int a, int b) {
        if (b == 0) {
            throw new MathException("division by zero");
        }
        if (a == Integer.MIN_VALUE) {
            if (b == -1) {
                throw new MathException("Integer overflow");
            }
        }
        return a / b;
    }
}
