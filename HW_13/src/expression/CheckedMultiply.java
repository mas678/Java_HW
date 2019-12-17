package expression;

public class CheckedMultiply extends AbstractBinaryOperation {
    public CheckedMultiply(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public String getSymbol() {
        return "*";
    }

    @Override
    public boolean getOrder() {
        return false;
    }

    @Override
    public int function(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        if (Integer.min(a, b) == Integer.MIN_VALUE) {
            if (Integer.max(a, b) != 1) {
                throw new MathException("overflow");
            }
            return Integer.MIN_VALUE;
        }
        int c = sign(a, b);
        a = Integer.max(a, -a);
        b = Integer.max(b, -b);
        if (c > 0 && b > Integer.MAX_VALUE / a) {
            throw new MathException("overflow");
        } else if (c < 0 && -b < Integer.MIN_VALUE / a) {
            throw new MathException("overflow");
        }
        return a * b * c;
    }

    private int sign(int a, int b) {
        int c = -1;
        if (a < 0 && b < 0
                || a > 0 && b > 0) {
            c = 1;
        }
        return c;
    }
}
