package org.abel.software;

import java.util.Arrays;

public class Equation {
    private String[] formula;
    private int unitCount;

    Equation(String form, int unitNum) {
        if (form.charAt(0) == '-') {
            form = "0" + form;
        }
        unitCount = unitNum;
        formula = tansform(form, unitNum);
    }

    public int getUniCount() {
        return unitCount;
    }

    public String[] getFormula() {
        return formula;
    }

    private String[] tansform(String form, int total) {
        int count = 0;
        boolean isNum = false;
        String[] tmpForm = new String[total];
        char[] ss = form.toCharArray();
        for (char a : ss) {
            if (Character.isDigit(a)||a=='.') {
                isNum = true;
                if (tmpForm[count] == null)
                    tmpForm[count] = a + "";
                else
                    tmpForm[count] += a;
            } else {
                if (a != '(' && isNum)
                    count++;
                tmpForm[count++] = a + "";
                isNum = false;
            }
        }
        return tmpForm;
    }

    Equation(int unitNum) {
        formula = new String[unitNum];
        unitCount = unitNum;
    }

    void add(String unit, int total) {
        int oldLen = formula.length;
        formula = Arrays.copyOf(formula, oldLen + total);
        String[] tmp = tansform(unit, total);
        for (int i = 0; i < tmp.length; i++) {
            formula[oldLen + i] = tmp[i];
        }
        unitCount++;
    }

    public String toString() {
        String s = new String();
        for (String a : formula) {
            s += a;
        }
        return s;
    }
}
