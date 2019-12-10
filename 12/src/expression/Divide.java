package expression;

public class Divide extends BinaryOperation {
    public Divide(FullExpression firstExpression, FullExpression secondExpression) {
        super(firstExpression, secondExpression, "/", true);
    }

    @Override
    public int function(int first, int second) {
        return first / second;
    }
}
