package expression;

public class Multiply extends AbstractBinaryOperation {
    public Multiply(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public String getSymbol() {
        return "*";
    }

    @Override
    public boolean getOrder() {
        return false;
    }

    @Override
    public int function(int first, int second) {
        return first * second;
    }

    @Override
    void bracketNeeding(StringBuilder into, CommonExpression exp, boolean isSecond) {
        String end = "";
        if (getLevel() > exp.getLevel() || (exp.getClass() == Divide.class
                || (getOrder() && getLevel() == exp.getLevel())) && isSecond)  {
            into.append('(');
            end = ")";
        }
        exp.toMiniString(into);
        into.append(end);
    }
}
