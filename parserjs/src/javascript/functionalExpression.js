const cnst = value => x => value;
const variable = () => x => x;
const binaryExpression = func => (expression1, expression2) => x => func(expression1(x), expression2(x));
const add = binaryExpression((a, b) => a + b);
const subtract = binaryExpression((a, b) => a - b);
const divide = binaryExpression((a, b) => a / b);
const multiply = binaryExpression((a, b) => a * b);