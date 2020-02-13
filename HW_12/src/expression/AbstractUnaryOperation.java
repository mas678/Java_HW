package expression;

public abstract class AbstractUnaryOperation implements CommonExpression, UnaryFunction {
    private CommonExpression expression;

    public AbstractUnaryOperation(CommonExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        StringBuilder into = new StringBuilder();
        toString(into);
        return into.toString();
    }

    @Override
    public void toString(StringBuilder into) {
        into.append(getSymbol()).append(")");
        expression.toString(into);
        into.append(")");
    }

    @Override
    public String toMiniString() {
        StringBuilder into = new StringBuilder();
        toMiniString(into);
        return into.toString();
    }

    @Override
    public void toMiniString(StringBuilder into) {
        into.append(getSymbol());
        String end = "";
        if (expression.getClass() == AbstractBinaryOperation.class) {
            into.append("(");
            end = ")";
        }
        expression.toMiniString(into);
        into.append(end);
    }

    @Override
    public int evaluate(int x) {
        return function(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return function(expression.evaluate(x, y, z));
    }
}
