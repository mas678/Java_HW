package expression;

public class Abs extends AbstractUnaryOperation {
    public Abs(CommonExpression expression) {
        super(expression);
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int function(int i) {
        if (i > 0) {
            return i;
        }
        return -i;
    }

    @Override
    public String getSymbol() {
        return "abs";
    }
}
