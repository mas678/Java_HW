package expression;

public class Logarithm extends AbstractBinaryOperation {
    public Logarithm(CommonExpression firstExpression, CommonExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public int getLevel() {
        return 5;
    }

    @Override
    public String getSymbol() {
        return "//";
    }

    @Override
    public boolean getOrder() {
        return true;
    }

    @Override
    public int function(int a, int b) {
        int ans = 0;
        while (a >= b) {
            a /= b;
            ans++;
        }
        return ans;
    }
}
