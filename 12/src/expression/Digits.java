package expression;

public class Digits extends AbstractUnaryOperation {
    public Digits(CommonExpression expression) {
        super(expression);
    }

    @Override
    public int getLevel() {
        return 4;
    }

    @Override
    public int function(int numb) {
        int ans = 0;
        while (numb != 0) {
            ans += numb % 10;
            numb /= 10;
        }
        return Integer.max(ans, -ans);
    }

    @Override
    public String getSymbol() {
        return "digits";
    }
}
