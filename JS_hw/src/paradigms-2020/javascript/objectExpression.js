"use strict";

function creator(constructor, evaluate, diff, toString, toArray) {
    constructor.prototype.evaluate = evaluate;
    constructor.prototype.diff = diff;
    constructor.prototype.toString = toString;
    constructor.prototype.toArray = toArray;
}

function Const(number) {
    this._number = number;
}
creator(
    Const,
    function (...args) {return this._number},
    function (varName) {return Const.ZERO;},
    function () {return this._number + "";},
    function () {return [this._number];}
);
Const.ZERO = new Const(0);
Const.ONE = new Const(1);
Const.TWO = new Const(2);


const variables = {x : 0, y : 1, z : 2};
function Variable (variable) {
    this._variable = variable;
    this._index = variables[variable];
}
creator(
    Variable,
    function (...args) {return args[this._index]},
function (varName) {return varName === this._variable ? Const.ONE : Const.ZERO;},
    function () {return this._variable;},
    function () {return new Array(this._variable);}
);

function Expression (...args) {
    this._args = args;
}

creator(
    Expression,
    function (...args) {
        return this.func(...this._args.map(arg => arg.evaluate(...args)))
    },
function (varName) {
        return this._toDiff(...this._args, ...this._args.map(arg => arg.diff(varName)))
    },
    function () {
        let arr = this.toArray();
        return arr.join(" ");
    },
    function () {
        let arr = ((this._args.map(element => (element.toArray())))
            .reduce((last, cur) => [...last, ...cur]));
        arr.push(this._sign);
        return arr;
    }
);

function makeFunc (sign, func, toDiff){
    let AbstractOperation = function(...args) {
        Expression.call(this, ...args);
    };
    AbstractOperation.prototype = Object.create(Expression.prototype);
    AbstractOperation.prototype._sign = sign;
    AbstractOperation.prototype.func = func;
    AbstractOperation.prototype.length = func.length;
    AbstractOperation.prototype._toDiff = toDiff;
    return AbstractOperation;
}

const Add = makeFunc("+", (a, b) => a + b, (a, b, da, db) => new Add(da, db));
const Negate = makeFunc("negate", (a) => -a, (a, da) => new Negate(da));
const Subtract = makeFunc("-", (a, b) => a - b, (a, b, da, db) => new Subtract(da, db));
const Multiply = makeFunc("*", (a, b) => a * b,
    (a, b, da, db)  => new Add(new Multiply(a, db), new Multiply(b, da)));
const Divide = makeFunc("/", (a, b) => a / b,
    (a, b, da, db)  => new Divide(new Subtract(new Multiply(da, b), new Multiply(a, db)), new Multiply(b, b)));
const Gauss = makeFunc("gauss", (a, b, c, x) => a * Math.exp(-(x - b) * (x - b)/(2 * c * c)),
    (a, b, c, x, da, db, dc, dx) => {
        let subXB = new Subtract(x, b);
        let mulSubSmt = (smt) => new Multiply(
            subXB, smt);
        return new Add(
            new Gauss(da, b, c, x),
            new Multiply(
                new Gauss(a, b, c, x),
                new Divide(
                    new Subtract(
                        new Multiply(new Multiply(c, dc), mulSubSmt(subXB)),
                        new Multiply(new Multiply(c, c), mulSubSmt(new Subtract(dx, db)))),
                    new Multiply(new Multiply(c, c), new Multiply(c, c)))))});



const operationFromToken = {
    "negate" :  Negate,
    "-" :  Subtract,
    "+" :  Add,
    "*" :  Multiply,
    "/" :  Divide,
    "gauss" : Gauss
};

const parse = input => {
    let stack = [];
    let mas = input.trim().split(/\s+/);
    for (let i = 0; i < mas.length; i++){
        let element = mas[i];
        if (element in operationFromToken) {
            let token = operationFromToken[element];
            stack.push(new token(...stack.splice(-new token().length)));
        } else if (element in variables) {
            stack.push(new Variable(element));
        } else {
            stack.push(new Const(+element));
        }
    }
    return stack[0];
};