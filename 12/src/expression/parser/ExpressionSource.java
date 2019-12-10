package expression.parser;

import expression.Expression;

public interface ExpressionSource {
    boolean hasNext();
    char next();
    ExpressionException error(final String message);
}