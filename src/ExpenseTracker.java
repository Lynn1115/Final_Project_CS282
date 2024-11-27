/*
    Final Project for CS282. 
    The propose of this application is to create an application for user to track 
    their daily expense.
    Group of 2 people: Yuni Lin and Darcie McCrary
    New Git Repo initialized.
*/
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Frame class
class Frame {
    private JFrame frame;

    public Frame() {
        frame = new JFrame();
        configureFrame();
    }

    public void configureFrame() {
        frame.setTitle("Expense Tracker                    Yuni Lin & Darcie McCrary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(920, 620);

        JPanel fullPanel = new JPanel();
        fullPanel.setSize(920, 620);
        fullPanel.setBackground(new Color(225, 240, 245));
        fullPanel.setLayout(null);
        frame.add(fullPanel);
        frame.add(fullPanel, BorderLayout.CENTER);

        JPanel expensePanel = createExpensePanel();
        expensePanel.setBounds(10, 15, 160, 40);
        fullPanel.add(expensePanel);

        JPanel logPanel = createLogPanel();
        logPanel.setBounds(10, 260, 880, 300);
        fullPanel.add(logPanel);

        // Updated to integrate the PieChartPanel directly
        JPanel pieChartPanel = createPieChartPanel();
        pieChartPanel.setBounds(200, 15, 328, 220);  // Adjust size and position as necessary
        fullPanel.add(pieChartPanel);

        JPanel trendChartPanel = createTrendChartPanel();
        trendChartPanel.setBounds(560, 15, 328, 220);
        fullPanel.add(trendChartPanel);
        
        JPanel removeAExpense = removeAddExpense();
        removeAExpense.setBounds(10, 75, 160, 40);
        fullPanel.add(removeAExpense);

        frame.setVisible(true);
    }

    private JPanel createExpensePanel() {
        JPanel panel = new SemiRoundedPanel(15, true, true);
        panel.setBackground(Color.white);
      
        JButton addExpense = new JButton();
        addExpense.setText("Add Expense");
        addExpense.setHorizontalAlignment(JTextField.CENTER);
        addExpense.setForeground(new Color(133, 177, 204));
        addExpense.setFont(new Font("Arial", Font.BOLD, 20));
        addExpense.setBorder(null);
        addExpense.setContentAreaFilled(false);
        addExpense.setPreferredSize(new Dimension(160, 32));
        addExpense.setFocusPainted(false);
        
        addExpense.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        addExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null, "Expense added");
            }
        });

        panel.add(addExpense);

        return panel;
    }

    private JPanel createLogPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        
        return panel;
    }

    // Replaced the original panel with PieChartPanel
    private JPanel createPieChartPanel() {
        return new PieChartPanel();  // Directly return the PieChartPanel
    }

    private JPanel createTrendChartPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        
        return panel;
    }
    
    private JPanel removeAddExpense() {
        
        JPanel panel = new SemiRoundedPanel(15, true, true);
        panel.setBackground(Color.white);
        
        
        JButton removeExpense = new JButton();
        removeExpense.setText("Remove Expense");
        removeExpense.setHorizontalAlignment(JTextField.CENTER);
        removeExpense.setForeground(new Color(133, 177, 204));
        removeExpense.setFont(new Font("Arial", Font.BOLD, 17));
        removeExpense.setBorder(null);
        removeExpense.setContentAreaFilled(false);
        removeExpense.setPreferredSize(new Dimension(160, 32));
        removeExpense.setFocusPainted(false);
        removeExpense.setFocusable(true);

        
        removeExpense.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        String[] expenseCategories = {"Housing", "Transportation","Food","Utilities","Healthcare","Entertainment","Savings"};       
        JComboBox selectCategoryBox = new JComboBox(expenseCategories);
 
        selectCategoryBox.setPreferredSize(new Dimension(120, 32));
        
        removeExpense.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                
                JLabel categoryLabel = new JLabel("Select a Category:");
                JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                categoryPanel.add(categoryLabel);
                categoryPanel.add(selectCategoryBox);
                 
                JOptionPane.showMessageDialog(null, categoryPanel); 
            }           
        });
       
        panel.add(removeExpense);
        panel.add(selectCategoryBox);
 
        return panel;
    }
}

/*  PieChartPanel class for drawing pie charts
    ------------------------------------------------------------
    This code was adapted from the 1BestCsharp blog.
    Original Source: https://1bestcsharp.blogspot.com/2024/09/java-create-pie-donut-chart.html
    Author: 1BestCsharp blog
    Changes were made to integrate this functionality into the Expense Tracker project.
   ------------------------------------------------------------
*/
class PieChartPanel extends JPanel {
    private Color[] sliceColors = {Color.decode("#FEC107"), Color.decode("#2196F3"), Color.decode("#4CAF50")};
    private int[] data = {40, 30, 30};

    
   public PieChartPanel() {
       setBackground(Color.white);  // Explicitly set the background to white
   }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPieChart(g);
    }

    private void drawPieChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        
        // Adjust this value to change the size of the pie chart
        int diameter = (Math.min(width, height) - 50); // Slightly smaller
        int x = (width - diameter) / 2 - 50;  // Moves the pie chart 20 pixels to the left
        int y = (height - diameter) / 2;
        int startAngle = 0;

        // Draw the slices of the pie chart
        for (int i = 0; i < data.length; i++) {
            int arcAngle = (int) ((double) data[i] / 100 * 360);
            g2d.setColor(sliceColors[i]);
            g2d.fillArc(x, y, diameter, diameter, startAngle, arcAngle);
            startAngle += arcAngle;
        }

        // Optionally, draw a legend for the slices
        int legendX = width - 110;
        int legendY = 20;
        for (int i = 0; i < data.length; i++) {
            g2d.setColor(sliceColors[i]);
            g2d.fillRect(legendX, legendY, 20, 20);
            g2d.setColor(Color.black);
            g2d.drawString("Slice " + (i + 1) + ": " + data[i] + "%", legendX + 30, legendY + 15);
            legendY += 30;
        }
    }
}

// Main class to run the application
public class ExpenseTracker {
    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}
