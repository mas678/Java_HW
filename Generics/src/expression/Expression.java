package expression;

public interface Expression {
    <T extends Number> T evaluate(T x);
}
