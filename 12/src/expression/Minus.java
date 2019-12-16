package expression;

public class Minus implements CommonExpression {
    private CommonExpression expression;

    public Minus(CommonExpression expression) {
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
        into.append("-(");
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
        into.append("-");
        String end = "";
        if (expression.getClass() == AbstractBinaryOperation.class) {
            into.append("(");
            end = ")";
        }
        expression.toMiniString(into);
        into.append(end);
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int evaluate(int x) {
        return -expression.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -expression.evaluate(x, y, z);
    }
}
