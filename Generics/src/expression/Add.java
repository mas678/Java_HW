package expression;

public class Add extends AbstractBinaryOperation {
    public Add(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public String getSymbol() {
        return "+";
    }

    @Override
    public <S extends Number, T extends Number> S function( T first, T second) {
            if (first instanceof Integer) {
            return Integer.sum((int)first, (int)second);
        }
        return first second;
    }
}
