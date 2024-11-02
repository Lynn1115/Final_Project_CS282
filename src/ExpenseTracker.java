/*
    Final Project for CS282. 
    The propose of this application is to create an application for user to track 
    their daily expense.
    Group of 2 people: Yuni Lin and Darcie McCrary
*/
import java.awt.*;
import javax.swing.*;

class Frame {
    private JFrame frame;

    public Frame() {
        frame = new JFrame();
        configureFrame();
    }

    public void configureFrame()
    {
        frame.setTitle("Expense Tracker                    Yuni Lin & Darcie McCrary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(900, 600);
        frame.setVisible(true);

        JPanel fullPanel = new JPanel();
        fullPanel.setSize(900, 600);
        fullPanel.setBackground(new Color(225, 240, 245));
        fullPanel.setLayout(null);
        frame.add(fullPanel);

        JPanel expensePanel = createExpensePanel();
        fullPanel.add(expensePanel);

        JPanel logPanel = createLogPanel();
        fullPanel.add(logPanel);

        JPanel peiChartPanel = createPeiChartPanel();
        fullPanel.add(peiChartPanel);

        JPanel trendChartPanel = createTrendChartPanel();
        fullPanel.add(trendChartPanel);
        
        JPanel removeAExpense = removeAddExpense();
        fullPanel.add(removeAExpense);

    }

    private JPanel createExpensePanel() 
    {
        JPanel panel = new JPanel();
        panel.setBounds(10, 15, 160, 35);
        panel.setBackground(Color.white);
      
        return panel;
    }

    private JPanel createLogPanel() 
    {
        JPanel panel = new JPanel();
        panel.setBounds(10, 260, 880, 300);
        panel.setBackground(Color.white);
        
        return panel;
    }

    private JPanel createPeiChartPanel() 
    {
        JPanel panel = new JPanel();
        panel.setBounds(200, 15, 328, 220);
        panel.setBackground(Color.white);
       
        return panel;
    }

    private JPanel createTrendChartPanel()
    {
        JPanel panel = new JPanel();
        panel.setBounds(560, 15, 328, 220);
        panel.setBackground(Color.white);
        
        return panel;
    }
    
    private JPanel removeAddExpense() 
    {
        JPanel panel = new JPanel();
        panel.setBounds(10, 75, 160, 35);
        panel.setBackground(Color.white);
      
        return panel;
    }
}

public class ExpenseTracker {
    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}