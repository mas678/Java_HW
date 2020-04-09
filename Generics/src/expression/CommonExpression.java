package expression;

public interface CommonExpression extends Expression, TripleExpression {
    void toString(StringBuilder into);
    int getLevel();
}
