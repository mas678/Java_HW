package expression;

public class CheckedDivide extends AbstractBinaryOperation {
    public CheckedDivide(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public String getSymbol() {
        return "/";
    }

    @Override
    public boolean getOrder() {
        return true;
    }

    @Override
    public int function(int a, int b) {
        if (b == 0) {
            throw new MathException("division by zero");
        }
        if (a == Integer.MIN_VALUE) {
            if (b == -1) {
                throw new MathException("overflow");
            }
        }
        return a / b;
    }
}
