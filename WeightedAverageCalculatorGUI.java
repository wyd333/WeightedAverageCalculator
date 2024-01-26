import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class WeightedAverageCalculatorGUI extends JFrame {
    private JTextField numSubjectsField;
    private JButton enterButton;
    private JScrollPane scrollPane;
    private JPanel inputPanel;
    private JButton calculateButton;
    private JLabel resultLabel;
    private JTextField filePathField;
    private JButton saveButton;

    public WeightedAverageCalculatorGUI() {
        setTitle("成绩加权平均分计算器");
        setSize(1600, 1200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // 添加科目数量输入框和确定按钮
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel numSubjectsLabel = new JLabel("科目数量：");
        numSubjectsLabel.setFont(Config.DEFAULT_FONT);
        numSubjectsField = new JTextField(5);
        numSubjectsField.setPreferredSize(new Dimension(140, 55));
        numSubjectsField.setFont(Config.FIELD_FONT);

        enterButton = new JButton("确定");
        enterButton.setFont(Config.DEFAULT_FONT);
        topPanel.add(numSubjectsLabel);
        topPanel.add(numSubjectsField);
        topPanel.add(enterButton);
        panel.add(topPanel, BorderLayout.NORTH);

        // 添加文件路径输入框和保存按钮
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JLabel filePathLabel = new JLabel("文件保存路径：");
        filePathLabel.setFont(Config.DEFAULT_FONT);
        filePathField = new JTextField(20);
        filePathField.setFont(Config.FIELD_FONT);
        saveButton = new JButton("保存");
        saveButton.setFont(Config.DEFAULT_FONT);
        bottomPanel.add(filePathLabel);
        bottomPanel.add(filePathField);
        bottomPanel.add(saveButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // 添加事件监听器以响应确定按钮点击事件
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Objects.equals(numSubjectsField.getText(), "")) {
                    int numSubjects = Integer.parseInt(numSubjectsField.getText());
                    createInputPanel(numSubjects);
                    revalidate();
                }
            }
        });

        // 添加事件监听器以响应保存按钮点击事件
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        add(panel, BorderLayout.NORTH);
        setVisible(true);
    }

    private void createInputPanel(int numSubjects) {
        if (scrollPane != null) {
            getContentPane().remove(scrollPane);
        }

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2));

        // 添加成绩和权重输入框
        Font labelFont = Config.DEFAULT_FONT;
        Font fieldFont = Config.FIELD_FONT;
        for (int i = 1; i <= numSubjects; i++) {
            JLabel scoreLabel = new JLabel("科目 " + i + " 成绩：");
            scoreLabel.setFont(labelFont);
            JTextField scoreField = new JTextField(5);
            scoreField.setFont(fieldFont);
            JLabel weightLabel = new JLabel("权重：");
            weightLabel.setFont(labelFont);
            JTextField weightField = new JTextField(10);
            weightField.setFont(fieldFont);

            inputPanel.add(scoreLabel);
            inputPanel.add(scoreField);
            inputPanel.add(weightLabel);
            inputPanel.add(weightField);
        }

        // 添加计算按钮和结果标签
        calculateButton = new JButton("计算");
        calculateButton.setFont(Config.DEFAULT_FONT);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateWeightedAverage(numSubjects);
            }
        });
        inputPanel.add(calculateButton);

        resultLabel = new JLabel();
        inputPanel.add(resultLabel);

        scrollPane = new JScrollPane(inputPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        revalidate();
    }

    private void calculateWeightedAverage(int numSubjects) {
        try {
            double totalWeightedScore = 0;
            double totalWeight = 0;
            resultLabel.setFont(Config.DEFAULT_FONT);
            resultLabel.setForeground(Color.RED);

            Component[] components = inputPanel.getComponents();
            for (int i = 0; i < numSubjects; i++) {
                JTextField scoreField = (JTextField) components[i * 4 + 1];
                JTextField weightField = (JTextField) components[i * 4 + 3];
                double score = Double.parseDouble(scoreField.getText());
                double weight = Double.parseDouble(weightField.getText());
                totalWeightedScore += score * weight;
                totalWeight += weight;
            }
            double weightedAverage = totalWeightedScore / totalWeight;
            resultLabel.setText("加权平均分：" + String.format("%.6f", weightedAverage));

            // 保存结果到文件
//            saveToFile();
        } catch (NumberFormatException e) {
            resultLabel.setText("输入格式错误，请重新输入！");
        }
    }

    private void saveToFile() {
        String filePath = filePathField.getText() + Config.SAVE_PATH + System.currentTimeMillis() + ".txt";

        try {
            File file = new File(filePath);

            FileWriter writer = new FileWriter(file);
            writer.write("科目 | 成绩 | 权重\n");
            if(inputPanel == null) {
                JOptionPane.showMessageDialog(this, "请先输入成绩和权重进行计算！");
                return;
            }
            Component[] components = inputPanel.getComponents();
            for (int i = 0; i < components.length / 4; i++) {
                JTextField scoreField = (JTextField) components[i * 4 + 1];
                JTextField weightField = (JTextField) components[i * 4 + 3];
                double score = Double.parseDouble(scoreField.getText());
                double weight = Double.parseDouble(weightField.getText());
                writer.write("科目 " + (i + 1) + " | " + score + " | " + weight + "\n");
            }
            writer.write("\n");
            writer.write(resultLabel.getText());
            writer.close();

            JOptionPane.showMessageDialog(this, "文件保存成功！");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "保存文件时发生错误：" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        UIManager.put("OptionPane.messageFont", Config.FIELD_FONT);
        // 设置全局对话框确认按钮大小
        UIManager.put("OptionPane.buttonFont", Config.FIELD_FONT);

        // 显示对话框
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WeightedAverageCalculatorGUI();
            }
        });
    }
}
