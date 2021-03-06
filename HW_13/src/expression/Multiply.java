package expression;

import expression.exceptions.MathException;

public class Multiply extends AbstractBinaryOperation {
    public Multiply(CommonExpression firstExpression, CommonExpression secondExpression) {
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
        return a * b;
    }
}
