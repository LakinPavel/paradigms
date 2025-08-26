"use strict";

const operation = operation => (...operands) => (...args) => {
    let result = [];
    operands.forEach(operand =>
        result.push(operand.apply(null, args))
    );
    return operation.apply(null, result);
}


const cnst = c => () => +c;
const min5 = operation((...args) => Math.min(...args));
const max3 = operation((...args) => Math.max(...args));
const pi = cnst(Math.PI);
const e = cnst(Math.E);
const add = operation((a, b) => a + b);
const subtract = operation((a, b) => a - b);
const multiply = operation((a, b) => a * b);
const divide = operation((a, b) => a / b);
const negate = operation(a => -a);
const variable = v => (...args) => args[variblesMap[v]];

const count = {
    '+': 2, '-': 2, '*': 2, '/': 2,
    "negate": 1, "min5": 5, "max3": 3
}


const typeOfOperations = {
    "min5": min5,
    "max3": max3,
    '+': add,
    '-': subtract,
    '*': multiply,
    '/': divide,
    "negate": negate
}
const variblesMap = {
    x: 0, y: 1, z: 2
}
const notNumbers = {pi: pi, e: e, x: variable("x"), y: variable("y"), z: variable("z")}

const parse = expression => {
    let elements = expression.split(' ').filter(el => el.length !== 0);
    let stack = [];
    elements.forEach(el => {
        if (el in typeOfOperations) {
            const n = count[el];
            let args = stack.splice(-n);
            stack.push(typeOfOperations[el](...args));
        } else if (el in notNumbers) {
            stack.push(notNumbers[el]);
        } else {
            stack.push(cnst(el));
        }
    });
    return stack.pop();
}