"use strict"


function expression(constructor, evaluate, toString, diff, prefix, postfix) {
    constructor.prototype.evaluate = evaluate;
    constructor.prototype.toString = toString;
    constructor.prototype.diff = diff;
    constructor.prototype.prefix = prefix;
    constructor.prototype.postfix = postfix;
}

function Const(constant) {
    this.constant = constant;
}

const const0 = new Const(0);
const const1 = new Const(1);

expression(
    Const,
    function () {
        return this.constant;
    },
    function () {
        return String(this.constant);
    },
    function () {
        return const0;
    },
    function () {
        return String(this.constant);
    },
    function () {
        return String(this.constant);
    }
);

function Variable(v) {
    this.v = v;
}

expression(
    Variable,
    function (...args) {
        return args[VariablesMap[this.v]];
    },
    function () {
        return this.v;
    },
    function (diffVariable) {
        return this.v === diffVariable ? const1 : const0;
    },
    function () {
        return this.v;
    },
    function () {
        return this.v;
    }
);


function operations(sign, operation, diffOperation) {
    function Oper(...expr) {
        this.expr = expr;
        this.sign = sign;
        this.diffOperation = diffOperation;
        this.operation = operation;
    }

    expression(
        Oper,
        function (x, y, z) {
            return this.operation(...this.expr.map(expr => expr.evaluate(x, y, z)));
        },
        function () {
            let string = '';
            string += this.expr.map(expr => String(expr)).join(" ") + " " + this.sign;
            return string;
        },
        function (diffVariable) {
            // return this.diffOperation(...this.expr, ...this.expr.map(ar => ar.diff(diffVariable)))
            return this.diffOperation(diffVariable, ...this.expr)
        },
        function () {
            let string = '';
            string += "(" + this.sign + " " + this.expr.map(expr => String(expr.prefix())).join(" ") + ")";
            return string;
        },
        function () {
            let string = '';
            string += "(" + this.expr.map(expr => String(expr.postfix())).join(" ") + " " + this.sign + ")";
            return string;
        }
    );
    return Oper;
}

const Add = operations("+", (a, b) => a + b,
    (respect, a, b) => {
        let da = a.diff(respect);
        let db = b.diff(respect);
        return new Add(da, db)
    }
);
const Subtract = operations("-", (a, b) => a - b,
    (respect, a, b) => {
        let da = a.diff(respect);
        let db = b.diff(respect);
        return new Subtract(da, db)
    }
);
const Multiply = operations("*", (a, b) => a * b,
    (respect, a, b) => {
        let da = a.diff(respect);
        let db = b.diff(respect);
        return new Add(
            new Multiply(da, b),
            new Multiply(a, db)
        )
    }
);
const Divide = operations("/", (a, b) => a / b,
    (respect, a, b) => {
        let da = a.diff(respect);
        let db = b.diff(respect);
        return new Divide(
            new Subtract(
                new Multiply(da, b),
                new Multiply(a, db)
            ),
            new Multiply(b, b)
        )
    }
);
const Negate = operations("negate", (a) => -a,
    (respect, a) => new Negate(a.diff(respect))
);
const ArcTan = operations("atan", (a) => Math.atan(a),
    (respect, a) => new Divide(
        a.diff(respect), new Add(new Const(1), new Multiply(a, a)))
);
const ArcTan2 = operations("atan2", (a, b) => Math.atan2(a, b),
    (respect, a, b) => {
        let da = a.diff(respect);
        let db = b.diff(respect);
        return new Divide(
            new Subtract(
                new Multiply(b, da),
                new Multiply(db, a)
            ),
            new Add(
                new Multiply(a, a),
                new Multiply(b, b)
            )
        )
    }
);
const Mean = operations("mean", (...args) => {
        let p = 1 / args.length;
        let sum = args.reduce((acc, curr) => acc + curr, 0);
        return p * sum;
    }, (respect, ...args) => {
        let p = new Divide(const1, new Const(args.length));
        let cur = const0;
        for (let i = 0; i < args.length; i++) {
            cur = new Add(cur, new Multiply(p, args[i].diff(respect)));
        }
        return cur;
    }
);
const Var = operations("var", (...args) => {
        let p = 1 / args.length;
        let sum = args.reduce((acc, curr) => acc + curr, 0);
        let sumOfSqr = args.reduce((acc, curr) => acc + Math.pow(curr, 2), 0);
        let mInSqr = Math.pow(p * sum, 2);
        let sqrM = p * sumOfSqr;
        return sqrM - mInSqr;
    }, (respect, ...args) => {
        let p = new Divide(const1, new Const(args.length));
        let curOf = const0;
        let curI = const0;
        for (let i = 0; i < args.length; i++) {
            let curArg = args[i];
            curOf = new Add(curOf, new Multiply(p, new Multiply(curArg, curArg)).diff(respect));
            curI = new Add(curI, new Multiply(p, curArg));
        }
        curI = new Multiply(curI, curI).diff(respect);
        return new Subtract(curOf, curI);
    }
);

const VariablesMap = {
    x: 0, y: 1, z: 2
}

const typeOfOperations = {
    '+': Add,
    '-': Subtract,
    '*': Multiply,
    '/': Divide,
    "negate": Negate,
    "atan": ArcTan,
    "atan2": ArcTan2,
    "mean": Mean,
    "var": Var
}

const count = {
    '+': 2, '-': 2, '*': 2, '/': 2,
    "atan2": 2, "atan": 1, "negate": 1,
    "mean": -1, "var": -1
}

function CustomError(message) {
    this.message = message;

}

CustomError.prototype = Object.create(Error.prototype);
CustomError.prototype.name = "CustomError";

const parse = expression => {
    let elements = expression.split(' ').filter(el => el.length !== 0);
    let stack = [];
    elements.forEach(el => {
        if (el in count) {
            const n = count[el];
            let args = stack.splice(-n);
            stack.push(new typeOfOperations[el](...args));
        } else if (el in VariablesMap) {
            stack.push(new Variable(el));
        } else {
            stack.push(new Const(parseFloat(String(el))));
        }
    });
    return stack.pop();
}


const parseType = (type, expr) => {
    let balanceBrackets = 0;
    let exprList = [];
    let i = 0;
    let curStr = "";
    while (i < expr.length) {
        let current = expr[i];
        if (/^[0-9]$/.test(current)) {
            curStr += current;
        } else if (current === "n" && curStr.length === 0) {
            if (i + 5 < expr.length) {
                if ("negate" === current + expr[i + 1] + expr[i + 2] + expr[i + 3] + expr[i + 4] + expr[i + 5]) {
                    curStr = "negate";
                } else {
                    throw new CustomError("Unknown symbol " + "<<" + current + ">>");
                }
                i += 5;
            } else {
                throw new CustomError("Unknown symbol " + "<<" + current + ">>");
            }
        } else if (current === "m" && curStr.length === 0) {
            if (i + 3 < expr.length) {
                if ("mean" === current + expr[i + 1] + expr[i + 2] + expr[i + 3]) {
                    curStr = "mean";
                } else {
                    throw new CustomError("Unknown symbol " + "<<" + current + ">>");
                }
                i += 3;
            } else {
                throw new CustomError("Unknown symbol " + "<<" + current + ">>");
            }
        } else if (current === "v" && curStr.length === 0) {
            if (i + 2 < expr.length) {
                if ("var" === current + expr[i + 1] + expr[i + 2]) {
                    curStr = "var";
                } else {
                    throw new CustomError("Unknown symbol " + "<<" + current + ">>");
                }
                i += 2;
            } else {
                throw new CustomError("Unknown symbol " + "<<" + current + ">>");
            }
        } else if (((current === "x") || (current === "y") || (current === "z")
            || (current === "+") || (current === "-") || (current === "*") || (current === "/")) && curStr.length === 0) {
            curStr += current;
        } else if ((current === "(") || (current === ")")) {
            current === "(" ? balanceBrackets++ : balanceBrackets--;
            if (curStr.length > 0) {
                exprList.push(curStr);
                curStr = "";
            }
            exprList.push(current);
        } else if (current === " ") {
            if (curStr.length > 0) {
                exprList.push(curStr);
                curStr = "";
            }
        } else {
            throw new CustomError("Unknown symbol " + "<<" + current + ">>");
        }
        i++;
    }
    if (curStr.length !== 0) {
        exprList.push(curStr);
    }

    i = 0;
    if (balanceBrackets !== 0) {
        let message = "Error balance of brackets, missing ";
        balanceBrackets < 0 ? message += "(" : message += ")";
        throw new CustomError(message);
    }
    if (exprList.length === 1) {
        let el = exprList[0];
        if (el in VariablesMap) {
            return (new Variable(el));
        } else {
            return (new Const(parseFloat(String(el))))
        }
    } else {
        while (i < exprList.length) {
            if (exprList[i] === ")") {
                let values = [];
                let sign = "";
                let startInd = i;
                let counter = 1;
                i--;
                while (exprList[i] !== "(" && i > -1) {
                    let el = exprList[i];
                    if (el in count) {
                        let message = "Don't forget that your parse type is: " + type
                        if (sign.length === 0) {
                            if (type === "prefix") {
                                if (exprList[i - 1] !== "(" || i - 1 < 0) {
                                    throw new CustomError(message)
                                }
                            } else if (type === "postfix") {
                                if (exprList[i + 1] !== ")" || i + 1 >= exprList.length) {
                                    throw new CustomError(message)
                                }
                            }
                            sign = el;
                        } else {
                            throw new CustomError("An extra sign")
                        }
                    } else if (el in VariablesMap) {
                        values.unshift(new Variable(el));
                    } else if (!isNaN(el)) {
                        values.unshift(new Const(parseFloat(String(el))))
                    } else {
                        values.unshift(el);
                    }
                    i--;
                    counter++;
                }
                if (i === -1) {
                    throw new CustomError("NO (, when it must be");
                }
                if (count[sign] > 0 && count[sign] !== values.length) {
                    throw new CustomError("Incorrect number of arguments")
                }
                exprList.splice(startInd - counter, counter + 1, new typeOfOperations[sign](...values))
            }
            i++;
        }
        if (exprList.length !== 1) {
            let message;
            exprList.length === 0 ? message = "input is empty" : message = "There some trash :)";
            throw new CustomError(message);
        }
        return exprList.pop();
    }
}


const parsePrefix = express => parseType("prefix", express);
const parsePostfix = express => parseType("postfix", express);