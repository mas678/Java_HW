package expression;

public class CheckedSubtract extends AbstractBinaryOperation {
    public CheckedSubtract(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public String getSymbol() {
        return "-";
    }

    @Override
    public boolean getOrder() {
        return true;
    }

    @Override
    public int function(int a, int b) {
        if (b == Integer.MIN_VALUE) {
            if (a < 0) {
                return a - b;
            }
            throw new MathException("overflow");
        }
        b = -b;
        return CheckedAdd.addCheck(a, b);
    }
}
