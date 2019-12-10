package expression;


public class Add extends BinaryOperation {
    public Add(FullExpression firstExpression, FullExpression secondExpression) {
        super(firstExpression, secondExpression, "+", false);
    }


    @Override
    public int function(int first, int second) {
        return first + second;
    }
}
