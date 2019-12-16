package expression;

public class Square implements CommonExpression {
    private CommonExpression expression;

    public Square(CommonExpression expression) {
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
        into.append("square(");
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
        into.append("square");
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
        return square(expression.evaluate(x));
    }

    private int square(int i) {
        return i * i;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return square(expression.evaluate(x, y, z));
    }
}
