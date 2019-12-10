package expression;

public class Multiply extends BinaryOperation {
    public Multiply(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression, "*", false, 3);
    }

    @Override
    public int function(int first, int second) {
        return first * second;
    }

    public void toMiniString(StringBuilder into) {
        bracketNeeding(into, firstExpression, false);
        into.append(" ");
        into.append(symbol);
        into.append(" ");
        bracketNeeding(into, secondExpression, true);
    }

    private void bracketNeeding(StringBuilder into, CommonExpression exp, boolean isSecond) {
        String end = "";
        if (getLevel() > exp.getLevel() || (exp.getSymbol().equals("/")
                || (order && getLevel() == exp.getLevel())) && isSecond)  {
            into.append('(');
            end = ")";
        }
        exp.toMiniString(into);
        into.append(end);
    }
}
