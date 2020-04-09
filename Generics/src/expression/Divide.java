package expression;

public class Divide extends AbstractBinaryOperation {
    public Divide(CommonExpression firstExpression, CommonExpression secondExpression) {
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
    public <T extends Number> T function(T a, T b) {

        return a / b;
    }
}
