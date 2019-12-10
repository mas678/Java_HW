package expression.parser;

import expression.FullExpression;

public interface Parser {
    FullExpression parse(String expression);
}