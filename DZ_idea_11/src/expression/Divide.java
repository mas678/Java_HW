package expression;

public class Divide extends BinaryOperation {
    public Divide(FullExpression firstExpression, FullExpression secondExpression) {
        super(firstExpression, secondExpression, "/");
    }

    @Override
    public int evaluate(int x) {
        return firstExpression.evaluate(x) / secondExpression.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return firstExpression.evaluate(x, y, z) / secondExpression.evaluate(x, y, z);
    }
}
