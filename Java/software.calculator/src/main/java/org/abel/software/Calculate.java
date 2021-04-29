package org.abel.software;

import java.util.Stack;

public class Calculate {
    static boolean hasDoubleError = false; // 表示在整数运算中出现了负数
    static boolean formulaOver = false; // 右括号结束
    static Stack<String> operStack = new Stack<>(); // 存储运算符号
    static Stack<Double> douStack = new Stack<>(); // 存储浮点数

    static double getAnswer(Equation eq) {
        // 清空栈
        while (!operStack.isEmpty()) {
            operStack.pop();
        }
        while (!douStack.isEmpty()) {
            douStack.pop();
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

        return douStack.pop();
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
        double b = douStack.pop();
        double a = douStack.pop();
        if (operator.equals("+")) {
            douStack.add(a + b);
        } else if (operator.equals("-")) {
            douStack.add(a - b);
        }

    }

    static void pOneUnit(String unit) {
        String lastOper = null;
        if (operStack.isEmpty() == false) {
            lastOper = operStack.peek();
        }
        if (unit.matches("^-?[0-9]+.*[0-9]*")) {
            double uNum = Double.valueOf(unit);
            if ((lastOper == null) || (!lastOper.equals("*") && !lastOper.equals("/"))) {
                douStack.add(uNum);
            } else {
                double tmp = douStack.pop();
                if (lastOper.equals("*")) {
                    douStack.add(tmp * uNum);
                } else {
                    douStack.add(tmp / uNum);
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
        //Equation eq = new Equation("-12*8.7-(-3+6.8)");
        //System.out.print(Calculate.getAnswer(eq));
        String a = "*";
        System.out.println(a.matches("^//*"));
    }
}
