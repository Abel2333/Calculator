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
        formulaOver = true;
        while (formulaOver) {
            overProcess();
        }

        return intStack.pop();
    }

    static void overProcess() {
        if (operStack.isEmpty()) {
            formulaOver = false;
            return;
        }
        String operator = operStack.pop();
        if (operator.equals("(")) {
            formulaOver = false;
            return;
        }
        int b = intStack.pop();
        int a = intStack.pop();
        if (operator.equals("+")) {
            intStack.add(a + b);
        } else if (operator.equals("-")) {
            intStack.add(a - b);
        }

    }

    static void pOneUnit(String unit) {
        String lastOper = null;
        if (operStack.isEmpty() == false) {
            lastOper = operStack.peek();
        }
        if (unit.matches("[0-9]*")) {
            int uNum = Integer.valueOf(unit);
            if ((lastOper == null) || (!lastOper.equals("*") && !lastOper.equals("/"))) {
                intStack.add(uNum);
            } else {
                int tmp = intStack.pop();
                if (lastOper.equals("*")) {
                    intStack.add(tmp * uNum);
                } else if ((tmp / uNum) * uNum == uNum) {
                    intStack.add(tmp / uNum);
                } else {
                    hasDoubleError = true;
                    return;
                }
                operStack.pop();
            }
        } else {
            if (unit.equals(")")) {
                formulaOver = true;
                return;
            } else {
                operStack.add(unit);
            }
        }
    }

    public static void main(String[] args) {
        Equation eq = new Equation("(12)*8-(3+6)");
        System.out.print(Calculate.getIntegerAnswer(eq));
    }
}
