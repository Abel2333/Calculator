package org.abel.software;

import java.util.Arrays;

public class Equation {
    private String[] formula;
    private int unitNum;

    Equation(String form) {
        if (form == null) {
            formula = new String[1];
            unitNum = -1;
            return;
        }
        formula = tansform(form);
        unitNum = formula.length;
    }

    public int getUniNum() {
        return unitNum;
    }

    public String[] getFormula() {
        return formula;
    }

    private String[] tansform(String form) {
        int count = 0;
        boolean isNum = false;
        String[] tmpForm = new String[form.length()];
        char[] ss = form.toCharArray();
        for (char a : ss) {
            if (Character.isDigit(a) || a == '.') {
                isNum = true;
                if (tmpForm[count] == null)
                    tmpForm[count] = a + "";
                else
                    tmpForm[count] += a;
            } else {
                if (a != '(' && isNum)
                    count++;
                tmpForm[count] = a + "";
                if (a == '-' && !isNum) {
                    if (count == 0 || !tmpForm[count - 1].equals(")"))
                        continue;
                    else count++;
                } else
                    count++;
                isNum = false;
            }
        }
        if (isNum)
            count++;
        tmpForm = Arrays.copyOf(tmpForm, count);
        return tmpForm;
    }

    Equation() {
        formula = new String[unitNum];
    }

    void add(String unit) {
        int oldLen = formula.length;
        String[] tmp = tansform(unit);
        if (isEmpty()) {
            formula = tmp;
            unitNum = formula.length;
        } else {
            formula = Arrays.copyOf(formula, oldLen + tmp.length);
            for (int i = 0; i < tmp.length; i++) {
                formula[oldLen + i] = tmp[i];
            }
            unitNum = formula.length;
        }
    }

    public boolean isEmpty() {
        if (unitNum == -1) {
            return true;
        }
        return false;
    }

    public String toString() {
        String s = new String();
        for (String a : formula) {
            s += a + " ";
        }
        return s;
    }

    public static void main(String[] args) {
        Equation eq = new Equation("-12.4+(-4)-9+(-5*7-9.7)");
        System.out.print(eq.isEmpty());
    }

}
