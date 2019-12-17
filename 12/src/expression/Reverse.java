package expression;

public class Reverse extends AbstractUnaryOperation {
    public Reverse(CommonExpression expression) {
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
            ans = ans * 10 + numb % 10;
            numb /= 10;
        }
        return ans;
    }

    @Override
    public String getSymbol() {
        return "reverse";
    }
}
