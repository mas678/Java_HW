package expression;

public interface TripleExpression {
    <T extends Number> T evaluate(T x, T y, T z);
}
