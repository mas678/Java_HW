package expression.exceptions;

import expression.Add;
import expression.CommonExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int function(int a, int b) {
        if (b == Integer.MIN_VALUE) {
            if (a < 0) {
                return a - b;
            }
            throw new MathException("Integer overflow");
        }
        return CheckedAdd.addCheck(a, -b);
    }
}
