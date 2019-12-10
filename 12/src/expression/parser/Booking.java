package expression.parser;

import expression.FullExpression;

interface Booking {
    FullExpression create(FullExpression childrenOne, FullExpression childrenTwo);
}