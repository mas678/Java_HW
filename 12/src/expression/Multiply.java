package expression;

public class Multiply extends BinaryOperation {
    public Multiply(FullExpression firstExpression, FullExpression secondExpression) {
        super(firstExpression, secondExpression, "*", false);
    }

    @Override
    public int function(int first, int second) {
        return first * second;
    }
}
