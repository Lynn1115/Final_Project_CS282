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

//frame class.
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

        JPanel peiChartPanel = createPeiChartPanel();
        peiChartPanel.setBounds(200, 15, 328, 220);
        fullPanel.add(peiChartPanel);

        JPanel trendChartPanel = createTrendChartPanel();
        trendChartPanel.setBounds(560, 15, 328, 220);
        fullPanel.add(trendChartPanel);
        
        JPanel removeAExpense = removeAddExpense();
        removeAExpense.setBounds(10, 75, 160, 40);
        fullPanel.add(removeAExpense);

        frame.setVisible(true);
    }

    private JPanel createExpensePanel() 
    {
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
        
        addExpense.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                JOptionPane.showMessageDialog(null,"Exspense added");
            }
        });

        panel.add(addExpense);

        return panel;
    }

    private JPanel createLogPanel() 
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        
        return panel;
    }

    private JPanel createPeiChartPanel() 
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
       
        return panel;
    }

    private JPanel createTrendChartPanel()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        
        return panel;
    }
    
    private JPanel removeAddExpense() 
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
      
        return panel;
    }
}

public class ExpenseTracker {
    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}