package expression;

public class Add extends AbstractBinaryOperation {
    public Add(CommonExpression firstExpression, CommonExpression secondExpression) {
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
        return first + second;
    }
}
