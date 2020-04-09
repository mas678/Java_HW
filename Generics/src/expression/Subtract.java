package expression;

public class Subtract extends AbstractBinaryOperation {
    public Subtract(CommonExpression firstExpression, CommonExpression secondExpression) {
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
        return a - b;
    }
}
