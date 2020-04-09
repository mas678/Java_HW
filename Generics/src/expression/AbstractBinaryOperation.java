package expression;

public abstract class AbstractBinaryOperation implements CommonExpression, BinaryFunction {
    protected CommonExpression firstExpression;
    protected CommonExpression secondExpression;

    public AbstractBinaryOperation(CommonExpression firstExpression, CommonExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    @Override
    public String toString() {
        StringBuilder into = new StringBuilder();
        toString(into);
        return into.toString();
    }

    @Override
    public void toString(StringBuilder into) {
        into.append('(');
        firstExpression.toString(into);
        into.append(" ");
        into.append(getSymbol());
        into.append(" ");
        secondExpression.toString(into);
        into.append(')');
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            AbstractBinaryOperation tmp = (AbstractBinaryOperation) obj;
            return firstExpression.equals(tmp.firstExpression)
                    && secondExpression.equals(tmp.secondExpression);
        }
        return false;
    }

    @Override
    public <T extends Number> T evaluate(T x) {
        return function(firstExpression.evaluate(x), secondExpression.evaluate(x));
    }

    @Override
    public <T extends Number> T evaluate(T x, T y, T z) {
        return function(firstExpression.evaluate(x, y, z), secondExpression.evaluate(x, y, z));
    }

    @Override
    public int hashCode() {
        return 57 * firstExpression.hashCode() + 57 * 57 * secondExpression.hashCode() + getClass().hashCode();
    }
}
