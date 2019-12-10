package expression;

public class ShiftLeft extends BinaryOperation {
    public ShiftLeft(FullExpression firstExpression, FullExpression secondExpression) {
        super(firstExpression, secondExpression, "<<", true);
    }

    @Override
    public int function(int first, int second) {
        return first << second;
    }
}