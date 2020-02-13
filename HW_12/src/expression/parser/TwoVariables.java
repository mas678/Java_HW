package expression.parser;

import expression.CommonExpression;

interface TwoVariables {
    CommonExpression create(CommonExpression childrenOne, CommonExpression childrenTwo);
}