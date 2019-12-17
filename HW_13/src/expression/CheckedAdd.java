package expression;

public class CheckedAdd extends AbstractBinaryOperation {
    public CheckedAdd(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public String getSymbol() {
        return "+";
    }

    @Override
    public boolean getOrder() {
        return false;
    }

    @Override
    public int function(int first, int second) {
        return addCheck(first, second);
    }

    static int addCheck(int a, int b) {
        if (a > 0 && b > 0 && a + b <= 0) {
//            if (Integer.MAX_VALUE - a < b) {
            throw new MathException("overflow");
        } else if (a < 0 && b < 0 && a + b >= 0) {
//            if (Integer.MIN_VALUE - b > a) {
            throw new MathException("overflow");
        }
        return a + b;
    }
}
