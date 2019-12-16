package expression;

public class Abs implements CommonExpression {
    private CommonExpression expression;

    public Abs(CommonExpression expression) {
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
        into.append("abs(");
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
        into.append("abs");
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
        return abs(-expression.evaluate(x));
    }

    private int abs(int i) {
        if (i > 0) {
            return i;
        }
        return -i;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return abs(expression.evaluate(x, y, z));
    }
}
