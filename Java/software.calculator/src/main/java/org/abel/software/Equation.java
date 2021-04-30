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
        unitNum = -1;
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
        boolean thisIsEmpty = true;
        boolean isNum = false;
        String[] tmpForm = new String[form.length()];
        char[] ss = form.toCharArray();
        for (int i = 0; i < ss.length; i++) {
            if (Character.isDigit(ss[i]) || ss[i] == '.') {
                if (!isNum && !thisIsEmpty) {
                    count++;
                }
                isNum = true;
                thisIsEmpty = false;
                if (tmpForm[count] == null)
                    tmpForm[count] = ss[i] + "";
                else
                    tmpForm[count] += ss[i];
            } else {
                if (thisIsEmpty == false) {
                    count++;
                }
                tmpForm[count] = ss[i] + "";
                if (i == ss.length - 1)
                    break;
                if (count == 0 && Character.isDigit(ss[i + 1]) && ss[i] == '-' && isEmpty()) {
                    thisIsEmpty = true;
                } else if (count != 0 && ss[i] == '-' && Character.isDigit(ss[i + 1])
                        && tmpForm[count - 1].equals("(")) {
                    thisIsEmpty = true;
                } else {
                    thisIsEmpty = false;
                    isNum = false;
                }
            }
        }
        tmpForm = Arrays.copyOf(tmpForm, count + 1);
        return tmpForm;
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
        Equation eq = new Equation("17+(-9.56+89*77)-5.6");
        eq.add("-(34+7)");
        System.out.println(eq);
    }

}
