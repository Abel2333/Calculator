package org.abel.software;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;

public class UI extends JFrame {
    private double[] s = { 0, 0, 0 };

    private JButton overButton = new JButton("生成");
    private JLabel formuNumLabel = new JLabel("题目数量"), maxNumLabel = new JLabel("最大数");
    private JTextField formNumText = new JTextField(5), maxNumText = new JTextField(5);
    private JCheckBox parenthesesCheckBox = new JCheckBox("括号"), decimalCheckBox = new JCheckBox("小数");
    private JTextArea equalTextArea = new JTextArea(10, 20);

    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();

    private UI() {
        setLayout(gridBagLayout);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        locationMod(formuNumLabel, 0, 0, 3, 1);
        locationMod(formNumText, 3, 0, 3, 1);
        locationMod(maxNumLabel, 8, 0, 3, 1);
        locationMod(maxNumText, 12, 0, 3, 1);
        locationMod(parenthesesCheckBox, 2, 1, 6, 2);
        locationMod(decimalCheckBox, 10, 1, 6, 2);
        locationMod(overButton, 16, 0, 6, 4);
        locationMod(equalTextArea, 0, 5, 22, 22);

        parenthesesCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (parenthesesCheckBox.isSelected()) {
                    s[0] = 1;
                } else {
                    s[0] = 0;
                }
            }
        });
        decimalCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (decimalCheckBox.isSelected()) {
                    s[1] = 1;
                } else {
                    s[1] = 0;
                }
            }
        });

        overButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                equalTextArea.setText("");
                giveEqual(Integer.valueOf(formNumText.getText()), Double.valueOf(maxNumText.getText()));
            }
        });

        add(formuNumLabel);
        add(formNumText);
        add(maxNumLabel);
        add(maxNumText);
        add(parenthesesCheckBox);
        add(decimalCheckBox);
        add(overButton);
        add(equalTextArea);
    }

    private void locationMod(final Component Comp, final int x, final int y, final int w, final int h) {
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        gridBagConstraints.gridwidth = w;
        gridBagConstraints.gridheight = h;
        gridBagLayout.setConstraints(Comp, gridBagConstraints);
    }

    private void giveEqual(int formNum, double maxNum) {
        s[2] = maxNum;
        for (int i = 0; i < formNum; i++) {
            if (s[1] == 1) {
                RandomGenerate rg = new RandomGenerate(s);
                DecimalFormat df = new DecimalFormat("0.00");
                Equation eq = rg.getEquation();
                double ans = Calculate.getAnswer(eq);
                equalTextArea.append(eq + " = " + df.format(ans) + "\r\n");
            } else {
                double ans;
                Equation eq;
                do {
                    RandomGenerate rg = new RandomGenerate(s);
                    eq = rg.getEquation();
                    ans = Calculate.getAnswer(eq);
                } while (Double.isNaN(ans) || Double.isInfinite(ans));
                equalTextArea.append(eq + " = " + Calculate.getAnswer(eq) + "\r\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingConsole.run(new UI(), 800, 500, "随机计算器");
    }
}
