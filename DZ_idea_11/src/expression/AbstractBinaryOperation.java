package expression;

public abstract class AbstractBinaryOperation implements CommonExpression, BinaryFunction {
    protected CommonExpression firstExpression;
    protected CommonExpression secondExpression;

    AbstractBinaryOperation(CommonExpression firstExpression, CommonExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    @Override
    public String toMiniString() {
        StringBuilder into = new StringBuilder();
        toMiniString(into);
        return into.toString();
    }


    public void toMiniString(StringBuilder into) {
        bracketNeeding(into, firstExpression, false);
        into.append(" ");
        into.append(getSymbol());
        into.append(" ");
        bracketNeeding(into, secondExpression, true);
    }

    void bracketNeeding(StringBuilder into, CommonExpression exp, boolean isSecond) {
        String end = "";
        if (getLevel() > exp.getLevel() || (getOrder() && getLevel() == exp.getLevel() && isSecond))  {
            into.append('(');
            end = ")";
        }
        exp.toMiniString(into);
        into.append(end);
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
    public int hashCode() {
        return (31 * firstExpression.hashCode() + secondExpression.hashCode()) * 31 + getClass().hashCode();
    }
    @Override
    public int evaluate(int x) {
        return function(firstExpression.evaluate(x), secondExpression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) throws Exception {
        return function(firstExpression.evaluate(x, y, z), secondExpression.evaluate(x, y, z));
    }
}
