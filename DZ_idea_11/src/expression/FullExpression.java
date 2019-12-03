package expression;

public interface FullExpression extends Expression, TripleExpression {
    void toString(StringBuilder into);
    void toMiniString(StringBuilder into);
    int getLevel();
    String getSymbol();
}
