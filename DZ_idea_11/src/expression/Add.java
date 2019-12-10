package expression;

public class Add extends BinaryOperation {
    public Add(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression, "+", false, 2);
    }

    @Override
    public int function(int first, int second) {
        return first + second;
    }
}
