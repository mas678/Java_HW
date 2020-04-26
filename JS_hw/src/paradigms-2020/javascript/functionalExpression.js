"use strict";

const polyExpression = func => {
    let f = (...args) => (...variables) => func(...(args.map(arg => arg(...variables))));
    f.countVars = func.length;
    return f;
};

const cnst = value => x => value;
const variables = {x : 0, y : 1, z : 2};
const variable = (variable) => {
    let index = variables[variable];
    return (...args) => (args[index]);
};
const negate = polyExpression((a) => -a);
const add = polyExpression((a, b) => a + b);
const subtract = polyExpression((a, b) => a - b);
const divide = polyExpression((a, b) => a / b);
const multiply = polyExpression((a, b) => a * b);
const iff = polyExpression((a, b, c) => (a >= 0 ? b : c));
const abs = polyExpression(Math.abs);
const one = cnst(1);
const two = cnst(2);


const operationFromToken = {
    "negate" : negate,
    "-" : subtract,
    "+" : add,
    "*" : multiply,
    "/" : divide,
    "abs" : abs,
    "iff" : iff
};

const consts = {
    "one" : one,
    "two" : two,
};

const parse = input => {
    let stack = [];
    let mas = input.trim().split(/\s+/);
    for (let i = 0; i < mas.length; i++){
        let element = mas[i];
        if (element in operationFromToken) {
            let token = operationFromToken[element];
            stack.push(token(...stack.splice(-token.countVars)));
        } else if (element in variables) {
            stack.push(variable(element));
        } else if (element in consts) {
            stack.push(consts[element]);
        } else {
            stack.push(cnst(+element));
        }
    }
    return stack[0];
};

