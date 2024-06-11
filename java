import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class GradeCalculatorGUI extends JFrame {
    private JTextField subjectInput;
    private JTextField gradeInput;
    private JTextArea gradeListArea;
    private JLabel averageLabel;
    private JLabel highestLabel;
    private JLabel lowestLabel;
    private ArrayList<GradeEntry> grades;

    public GradeCalculatorGUI() {
        // Initialize the grade list
        grades = new ArrayList<>();

        // Set up the frame
        setTitle("Grade Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create GUI components
        subjectInput = new JTextField(10);
        gradeInput = new JTextField(10);
        JButton addButton = new JButton("Add Grade");
        JButton calculateButton = new JButton("Calculate");
        gradeListArea = new JTextArea(10, 40);
        gradeListArea.setEditable(false);
        averageLabel = new JLabel("Average: N/A");
        highestLabel = new JLabel("Highest: N/A");
        lowestLabel = new JLabel("Lowest: N/A");

        // Add components to frame
        add(new JLabel("Enter Subject:"));
        add(subjectInput);
        add(new JLabel("Enter Grade:"));
        add(gradeInput);
        add(addButton);
        add(calculateButton);
        add(new JScrollPane(gradeListArea));
        add(averageLabel);
        add(highestLabel);
        add(lowestLabel);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGrade();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateGrades();
            }
        });
    }

    private void addGrade() {
        try {
            String subject = subjectInput.getText().trim();
            int grade = Integer.parseInt(gradeInput.getText().trim());
            if (subject.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a subject.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            grades.add(new GradeEntry(subject, grade));
            gradeListArea.append(subject + ": " + grade + "\n");
            subjectInput.setText("");
            gradeInput.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer grade.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateGrades() {
        if (grades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades to calculate.", "No Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double average = grades.stream().mapToInt(GradeEntry::getGrade).average().orElse(0.0);
        int highest = grades.stream().mapToInt(GradeEntry::getGrade).max().orElse(0);
        int lowest = grades.stream().mapToInt(GradeEntry::getGrade).min().orElse(0);

        averageLabel.setText("Average: " + String.format("%.2f", average));
        highestLabel.setText("Highest: " + highest);
        lowestLabel.setText("Lowest: " + lowest);
    }

    private static class GradeEntry {
        private String subject;
        private int grade;

        public GradeEntry(String subject, int grade) {
            this.subject = subject;
            this.grade = grade;
        }

        public String getSubject() {
            return subject;
        }

        public int getGrade() {
            return grade;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GradeCalculatorGUI().setVisible(true);
            }
        });
    }
}
