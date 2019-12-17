package expression.exceptions;

import expression.Add;
import expression.CommonExpression;

public class CheckedAdd extends Add {
    public CheckedAdd(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int function(int first, int second) {
        return addCheck(first, second);
    }

    static int addCheck(int a, int b) {
        if (a > 0 && b > 0 && a + b <= 0) {
            throw new MathException("overflow");
        } else if (a < 0 && b < 0 && a + b >= 0) {
            throw new MathException("overflow");
        }
        return a + b;
    }
}
