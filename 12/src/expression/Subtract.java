package expression;

public class Subtract extends BinaryOperation {
    public Subtract(FullExpression firstExpression, FullExpression secondExpression) {
        super(firstExpression, secondExpression, "-", true);
    }

    @Override
    public int function(int first, int second) {
        return first - second;
    }
}
