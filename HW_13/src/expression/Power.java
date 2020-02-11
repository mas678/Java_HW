package expression;

import expression.exceptions.MathException;

public class Power extends AbstractBinaryOperation {
    public Power(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public String getSymbol() {
        return "**";
    }

    @Override
    public boolean getOrder() {
        return true;
    }

    @Override
    public int function(int a, int b) {
        if (b == 0) {
            return 1;
        }
        int x = (function(a, b / 2));
        x *= x;
        if (b % 2 == 0) {
            x *= a;
        }
        return x;
    }
}
