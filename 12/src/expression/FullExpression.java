package expression;

public interface FullExpression extends TripleExpression, Expression {
    void toString(StringBuilder into);
    void toMiniString(StringBuilder into);
    int getLevel();
    String getSymbol();
}
