package expression;

public interface CommonExpression extends Expression, TripleExpression {
    void toString(StringBuilder into);
    void toMiniString(StringBuilder into);
    int getLevel();
}
