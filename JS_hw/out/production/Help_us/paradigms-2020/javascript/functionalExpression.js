"use strict";

const cnst = value => x => value;
const variable = (variable) => (...args) => (args[variables[variable]]);
const negate = (expression) => x => -expression(x);
const binaryExpression = func => (...args) => x => func(args[0](x), args[1](x));
const add = binaryExpression((a, b) => a + b);
const subtract = binaryExpression((a, b) => a - b);
const divide = binaryExpression((a, b) => a / b);
const multiply = binaryExpression((a, b) => a * b);
const iff = (...args) => x => (args[0](x) >= 0 ? args[1](x) : args[2](x));
const abs = (...args) => (iff(args[0], args[0], negate(args[0])));

const variables = {
    "x" : 0,
    "y" : 1,
    "z" : 2
};

const operationFromToken = {
    "-" : subtract,
    "+" : add,
    "*" : multiply,
    "/" : divide,
    "abs" : abs,
    "iff" : iff
};

const len = {
    "-" : 2,
    "+" : 2,
    "*" : 2,
    "/" : 2,
    "abs" : 1,
    "iff" : 3
};

const consts = {
    "one" : 1,
    "two" : 2,
};

const parse = input => {
    let stack = [];
    let mas = input.trim().split(/\s+/);
    for (let i = 0; i < mas.length; i++){
        let element = mas[i];
        if (element in operationFromToken) {
            stack.push(operationFromToken[element](...stack.splice(-len[element])));
        } else if (element in variables) {
            stack.push(variable());
        } else if (element in consts) {
            stack.push(cnst(consts[element]))
        } else {
            stack.push(cnst(+element));
        }
    }
    return stack[0];
};

parse("x";)

