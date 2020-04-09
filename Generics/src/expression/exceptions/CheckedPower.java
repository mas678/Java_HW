package expression.exceptions;

import expression.CommonExpression;
import expression.Power;

public class CheckedPower extends Power {
    public CheckedPower(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int function(int a, int b) {
        if (b < 0) {
            throw new MathException("0 in power 0");
        }
        if (a == 0) {
            if (b == 0) {
                throw new MathException("0 in power 0");
            }
            return 0;
        }
        if (b == 0) {
            return 1;
        }
        int x = function(a, b / 2);
        int y = new CheckedMultiply(null, null).function(x, x);
        if (b % 2 == 1) {
            y = new CheckedMultiply(null, null).function(a, y);
        }
        return y;
    }
}