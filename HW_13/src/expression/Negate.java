package expression;

import expression.exceptions.MathException;

public class Negate extends AbstractUnaryOperation {
    public Negate(CommonExpression expression) {
        super(expression);
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int function(int a) {
        return -a;
    }

    @Override
    public String getSymbol() {
        return "-";
    }
}
