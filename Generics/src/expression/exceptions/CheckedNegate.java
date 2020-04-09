package expression.exceptions;

import expression.CommonExpression;
import expression.Negate;

public class CheckedNegate extends Negate {
    public CheckedNegate(CommonExpression expression) {
        super(expression);
    }

    @Override
    public int function(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new MathException("Integer overflow");
        }
        return -a;
    }
}
