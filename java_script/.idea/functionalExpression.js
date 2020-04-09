let cnst = function (x) {
    function f(a) {
        return x
    }
    return f
}

let negate = function (x) {
    function f(a) {
        return -x(a)
    }
    return f
}

let variable = function (x) {
    function f(a) {
        return a
    }
    return f
}

let add = function (x, y) {
    function f(a) {
        return x(a) + y(a)
    }
    return f
}

let multiply = function (x, y) {
    function f(a) {
        return x(a) * y(a)
    }
    return f
}

let divide = function (x, y) {
    function f(a) {
        return x(a) / y(a)
    }
    return f
}

let subtract = function (x, y) {
    function f(a) {
        return x(a) - y(a)
    }
    return f
}

let expr = subtract(
    multiply(
        cnst(2),
        variable("x")
    ),
    cnst(3)
);