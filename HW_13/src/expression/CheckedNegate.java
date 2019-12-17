package expression;

public class CheckedNegate extends AbstractUnaryOperation {
    public CheckedNegate(CommonExpression expression) {
        super(expression);
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int function(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new MathException("overflow");
        }
        return -a;
    }

    @Override
    public String getSymbol() {
        return "-";
    }
}
