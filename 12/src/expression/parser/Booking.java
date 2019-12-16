package expression.parser;

import expression.CommonExpression;

interface Booking {
    CommonExpression create(CommonExpression childrenOne, CommonExpression childrenTwo);
}