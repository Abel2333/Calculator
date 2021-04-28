package org.abel.software;

import java.util.Stack;

public class Calculate {
    static boolean hasDoubleError = false; // 表示在整数运算中出现了负数
    static boolean formulaOver = false; // 右括号结束
    static Stack<String> operStack = new Stack<>(); // 存储运算符号
    static Stack<Integer> intStack = new Stack<>(); // 存储整数
    static Stack<Double> douStack = new Stack<>(); // 存储浮点数

    static int getIntegerAnswer(Equation eq) {
        // 清空栈
        while (!operStack.isEmpty()) {
            operStack.pop();
        }
        while (!intStack.isEmpty()) {
            intStack.pop();
        }

        for (String unit : eq.getFormula()) {
            pOneUnit(unit);
            while (formulaOver) {
                overProcess();
            }
        }

        while (formulaOver) {
            overProcess();
        }

        return intStack.pop();
    }

    static void overProcess() {
        String operator = operStack.pop();
        if (operator == "(") {
            formulaOver = false;
            return;
        }
        int b = intStack.pop();
        int a = intStack.pop();
        if (operator == "+") {
            intStack.add(a + b);
        } else if (operator == "-") {
            intStack.add(a - b);
        }

        if (operStack.isEmpty()) {
            formulaOver = false;
        }
    }

    static void pOneUnit(String unit) {
        String lastOper = null;
        if (!operStack.isEmpty()) {
            lastOper = operStack.peek();
        }
        if (unit == "[0-9]*") {
            int uNum = Integer.valueOf(unit);
            if (lastOper == "*|/") {
                int tmp = intStack.pop();
                if (lastOper == "*") {
                    intStack.add(tmp * uNum);
                } else if ((tmp / uNum) * uNum == uNum) {
                    intStack.add(tmp / uNum);
                } else {
                    hasDoubleError = true;
                    return;
                }
                operStack.pop();
            } else {
                intStack.add(uNum);
            }
        } else {
            lastOper = unit;
            if (unit == ")") {
                formulaOver = true;
                return;
            } else {
                operStack.add(lastOper);
            }
        }
    }
}
