package expression.parser;


import expression.CommonExpression;
import expression.TripleExpression;

public interface Parser {
    CommonExpression parse(String expression);
}