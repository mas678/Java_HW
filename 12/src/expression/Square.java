package expression;

public class Square extends AbstractUnaryOperation {
    public Square(CommonExpression expression) {
        super(expression);
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int function(int i) {
        return i * i;
    }

    @Override
    public String getSymbol() {
        return "square";
    }
}
