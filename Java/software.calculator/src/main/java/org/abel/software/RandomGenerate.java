package org.abel.software;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

public class RandomGenerate {
    private Random rd;
    private double[] status = { 0, 0, 0 }; // 分别表示是否包含括号，是否包含小数，数据最大值
    private String[] operator = { "+", "-", "*", "/" };
    private Equation eq;

    RandomGenerate(double[] status) {
        Date date = new Date();
        rd = new Random(date.getTime());
        this.status = status;
        eq = new Equation(null);
    }

    Equation getEquation() {
        int partNum = rd.nextInt(4) + 2; // 式子总共几个部分
        for (int i = 0; i < partNum; i++) {
            String tmpEq = generateEquation();
            if (rd.nextInt(5) >= 3 && status[0] == 1 && !eq.isEmpty()) {
                tmpEq = "(" + tmpEq + ")";
            }
            if (!eq.isEmpty()) {
                tmpEq = operator[rd.nextInt(4)] + tmpEq;
            }
            eq.add(tmpEq);
        }
        return eq;
    }

    String generateEquation() {
        int numCount = rd.nextInt(4) + 2; // 该部分中数的个数
        DecimalFormat df;
        if (status[1] == 1) {
            df = new DecimalFormat("0.00");
        } else {
            df = new DecimalFormat("#");
        }
        String tmpF = new String();
        for (int i = 0; i < numCount - 1; i++) {
            double tmpDouble = rd.nextDouble() * status[2];
            if (rd.nextInt(4) == 1) {
                tmpDouble *= -1;
                tmpF += "(" + String.valueOf(df.format(tmpDouble)) + ")";
            } else {
                tmpF += String.valueOf(df.format(tmpDouble));
            }
            tmpF += operator[rd.nextInt(4)];
        }
        tmpF += String.valueOf(df.format(rd.nextDouble() * status[2]));
        return tmpF;
    }

    public static void main(String[] args) {
        double[] s = { 1, 1, 20 };
        RandomGenerate rg = new RandomGenerate(s);
        System.out.println(rg.getEquation());
    }

}
