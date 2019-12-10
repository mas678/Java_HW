package expression;

public class ShiftRight extends BinaryOperation {
    public ShiftRight(FullExpression firstExpression, FullExpression secondExpression) {
        super(firstExpression, secondExpression, ">>", true);
    }

    @Override
    public int function(int first, int second) {
        return first >> second;
    }
}
