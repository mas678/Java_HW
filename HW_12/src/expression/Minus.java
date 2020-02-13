package expression;

public class Minus extends AbstractUnaryOperation {
    public Minus(CommonExpression expression) {
        super(expression);
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int function(int i) {
        return -i;
    }

    @Override
    public String getSymbol() {
        return "-";
    }
}
