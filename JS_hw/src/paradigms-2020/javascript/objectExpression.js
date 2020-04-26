"use strict";

function Const(number) {
    this._number = number;
}
Const.prototype.evaluate = function (...args) {
    return this._number
};
Const.prototype.diff = function (varName) {
    return Const.ZERO;
};
Const.prototype.toString = function () {
    return this._number + "";
};
Const.prototype.toArray = function () {
    return [this._number];
};
Const.ZERO = new Const(0);
Const.ONE = new Const(1);
Const.TWO = new Const(2);


const variables = {x : 0, y : 1, z : 2};
function Variable (variable) {
    this._variable = variable;
    this._index = variables[variable];
}
    Variable.prototype.evaluate = function (...args) {
    return args[this._index]
};
Variable.prototype.diff = function (varName) {
    return varName === this._variable ? Const.ONE : Const.ZERO;
};
Variable.prototype.toString = function () {
    return this._variable;
};
Variable.prototype.toArray = function () {
    return new Array(this._variable);
};


function makeFunc (sign, func, toDiff){
    let AbstractOperation = function (...args) {
        this._args = args;
    };
    AbstractOperation.prototype._sign = sign;
    AbstractOperation.prototype.func = func;
    AbstractOperation.prototype.length = func.length;
    AbstractOperation.prototype._toDiff = toDiff;
    AbstractOperation.prototype.evaluate = function (...args) {
        return this.func(...this._args.map(arg => arg.evaluate(...args)))
    };
    AbstractOperation.prototype.diff = function (varName) {
        return this._toDiff(...this._args, ...this._args.map(arg => arg.diff(varName)))
    };
    AbstractOperation.prototype.toString = function () {
        let arr = this.toArray();
        return arr.join(" ");
    };
    AbstractOperation.prototype.toArray = function () {
        let arr = ((this._args.map(element => (element.toArray())))
            .reduce((last, cur) => [...last, ...cur]));
        arr.push(this._sign);
        return arr;
    };
    return AbstractOperation;
}

const Add = makeFunc("+", (a, b) => a + b, (a, b, da, db) => new Add(da, db));
const Negate = makeFunc("negate", (a) => -a, (a, da) => new Negate(da));
const Subtract = makeFunc("-", (a, b) => a - b, (a, b, da, db) => new Subtract(da, db));
const Multiply = makeFunc("*", (a, b) => a * b,
    (a, b, da, db)  => new Add(new Multiply(a, db), new Multiply(b, da)));
const Divide = makeFunc("/", (a, b) => a / b,
    (a, b, da, db)  => new Divide(new Subtract(new Multiply(da, b), new Multiply(a, db)), new Multiply(b, b)));const Gauss = makeFunc("gauss", (a, b, c, x) => a * Math.exp(-(x - b) * (x - b)/(2 * c * c)),
    (a, b, c, x, da, db, dc, dx) =>
        new Add(
            new Gauss(da, b, c, x),
            new Multiply(
                new Gauss(a, b, c, x),
                    new Divide(
                        new Subtract(
                            new Multiply(new Multiply(c, dc), new Multiply(
                                new Subtract(x, b), new Subtract(x, b))),
                            new Multiply(new Multiply(c, c), new Multiply(
                                new Subtract(x, b), new Subtract(dx, db)))),
                        new Multiply(new Multiply(c, c), new Multiply(c, c))))));



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