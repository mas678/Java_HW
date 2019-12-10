package expression;

public class Subtract extends BinaryOperation {
    public Subtract(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression, "-", true, 2);
    }

    @Override
    public int function(int first, int second) {
        return first - second;
    }
}
