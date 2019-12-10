package expression;

public class Divide extends BinaryOperation {
    public Divide(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression, "/", true, 3);
    }

    @Override
    public int function(int first, int second) {
        return first / second;
    }
}
