package org.abel.software;

import java.util.Stack;

public class Calculate {
    static boolean hasDoubleError = false; // 表示在整数运算中出现了负数
    static boolean formulaOver = false; // 右括号结束
    static Stack<String> operStack = new Stack<>(); // 存储运算符号
    static Stack<Double> douStack = new Stack<>(); // 存储浮点数
    static Stack<String> invertOperStack = new Stack<>();
    static Stack<Double> invertDouStack = new Stack<>();

    static double getAnswer(Equation eq) {
        // 清空栈
        while (!operStack.isEmpty()) {
            operStack.pop();
        }
        while (!douStack.isEmpty()) {
            douStack.pop();
        }
        while (!invertDouStack.isEmpty()) {
            invertDouStack.pop();
        }
        while (!invertOperStack.isEmpty()) {
            invertOperStack.pop();
        }

        for (String unit : eq.getFormula()) {
            pOneUnit(unit);
            if (formulaOver) {
                invertDouStack.add(douStack.pop());
                while (!operStack.peek().equals("(")) {
                    invertOperStack.add(operStack.pop());
                    invertDouStack.add(douStack.pop());
                }
                operStack.pop();
                while (formulaOver) {
                    overProcess();
                }
                pOneUnit(String.valueOf(invertDouStack.pop()));
            }
        }
        invertDouStack.add(douStack.pop());
        while (!operStack.isEmpty() && !douStack.isEmpty()) {
            invertOperStack.add(operStack.pop());
            invertDouStack.add(douStack.pop());
        }
        formulaOver = true;
        while (formulaOver) {
            overProcess();
        }
        douStack.add(invertDouStack.pop());

        return douStack.pop();

    }

    static void overProcess() {
        if (invertOperStack.isEmpty()) {
            formulaOver = false;
            return;
        }
        String operator = invertOperStack.pop();
        if (operator.equals("(")) {
            formulaOver = false;
            return;
        }
        double a = invertDouStack.pop();
        double b = invertDouStack.pop();
        if (operator.equals("+")) {
            invertDouStack.add(a + b);
        } else if (operator.equals("-")) {
            invertDouStack.add(a - b);
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
        double[] s = { 1, 0, 20 };
        for (int i = 0; i < 10; i++) {
        RandomGenerate rg = new RandomGenerate(s);
            Equation eq = rg.getEquation();
            System.out.println(eq + "=" + Calculate.getAnswer(eq));
        }
    }
    /*
     * Equation eq = new Equation("9.89+1.77*7.88+11.71");
     * System.out.println(Calculate.getAnswer(eq));
     */
}
