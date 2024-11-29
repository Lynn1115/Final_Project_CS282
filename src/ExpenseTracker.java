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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

// Frame class
class Frame {
    private JFrame frame;
    private LogTable logTableModel;
    private TrendChartPanel trendChartPanel;

    public Frame() {
        frame = new JFrame();
        logTableModel = new LogTable();
        trendChartPanel = new TrendChartPanel();
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
        addExpense.setFont(new Font("Arial", Font.BOLD, 17));
        addExpense.setBorder(null);
        addExpense.setContentAreaFilled(false);
        addExpense.setPreferredSize(new Dimension(160, 32));
        addExpense.setFocusPainted(false);
        
        addExpense.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        String[] expenseCategories = {"Housing", "Transportation","Food","Utilities","Healthcare","Entertainment","Savings"};       
        JComboBox selectCategoryBox = new JComboBox(expenseCategories);
        selectCategoryBox.setPreferredSize(new Dimension(130, 32));

        addExpense.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                JTextField amountText = new JTextField(15);
                JTextField descriptionText = new JTextField(15);

                JComboBox<String> monthBox = new JComboBox<>(new String[]{
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
                });

                JSpinner daySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
                daySpinner.setEditor(new JSpinner.NumberEditor(daySpinner, "00"));
                JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(2024, 1900, 2100, 1));

                JPanel mainPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(3, 3, 3, 3);
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;

                gbc.gridx = 0;
                gbc.gridy = 0;
                mainPanel.add(new JLabel("Category:"), gbc);

                gbc.gridx = 1;
                mainPanel.add(selectCategoryBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                mainPanel.add(new JLabel("Date:"), gbc);
                
                JPanel datePanel = new JPanel();
                datePanel.add(monthBox);
                datePanel.add(new JLabel("/"));
                datePanel.add(daySpinner);
                datePanel.add(new JLabel("/"));
                datePanel.add(yearSpinner);

                gbc.gridx = 1;
                mainPanel.add(datePanel, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                mainPanel.add(new JLabel("Amount:"), gbc);
                
                gbc.gridx = 1;
                mainPanel.add(amountText, gbc);
                
                gbc.gridx = 0;
                gbc.gridy = 3;
                mainPanel.add(new JLabel("Description:"), gbc);
                
                gbc.gridx = 1;
                mainPanel.add(descriptionText, gbc);

                int result = JOptionPane.showConfirmDialog(null, mainPanel, "Add Expense", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) 
                {
                    try {
                        String category = selectCategoryBox.getSelectedItem().toString();
                        String date = monthBox.getSelectedItem() + "/" +
                        String.format("%02d", (int) daySpinner.getValue()) + "/" +
                        yearSpinner.getValue();
                        double amount = Double.parseDouble(amountText.getText().trim());
                        String description = descriptionText.getText().trim();
                        
                        logTableModel.addLog(new LogExpense(category, date, amount, description));
                        trendChartPanel.updateChart(logTableModel.getExpenses());
                        
                    } 
                    catch (NumberFormatException e) 
                    {
                        JOptionPane.showMessageDialog(null, "Invalid input for amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(addExpense);
        return panel;
    }


    private JPanel createLogPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);

        JTable table = new JTable(logTableModel);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100,30));

        JTableHeader tableHeader = table.getTableHeader();
        Color customColor = new Color(225, 240, 245);
        tableHeader.setBackground(customColor);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Replaced the original panel with PieChartPanel
    private JPanel createPieChartPanel() {
        return new PieChartPanel();  // Directly return the PieChartPanel
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
class LogExpense
{
    private String category;
    private String date;
    private double amount;
    private String description;
            
    public LogExpense(String category, String date, double amount, String description)
    {
        this.category = category;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public String getDate() {return date;}

    public String getCategory() {return category;}

    public String getDescription() {return description;}

    public double getAmount() {return amount;}
}
class LogTable extends AbstractTableModel 
{
    private final List<LogExpense> expense;
    private final String[] columnNames = {"Category", "Date", "Amount", "Description"};

    public LogTable() 
    {
        expense = new ArrayList<>();
    }

    public LogTable(List<LogExpense> logItems) 
    {
        this.expense = logItems;
    }

    public List<LogExpense> getLogItems() 
    {
        return expense;
    }
    public void addLog(LogExpense expenses) {
        expense.add(expenses);
        fireTableRowsInserted(expense.size() - 1, expense.size() - 1);
    }

    public List<LogExpense> getExpenses() {
        return expense;
    }

    @Override
    public int getRowCount()
    {
        return expense.size();
    }

    @Override
    public int getColumnCount() 
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        LogExpense logExpense = expense.get(rowIndex);
           
        return switch (columnIndex) 
        {
            case 0 -> logExpense.getCategory();
            case 1 -> logExpense.getDate();
            case 2 -> logExpense.getAmount();
            case 3 -> logExpense.getDescription();
            default -> null;
        };
    }
        @Override
        public String getColumnName(int column) 
        {
            return columnNames[column];
        }
}

class TrendChartPanel extends JPanel 
{
    private List<LogExpense> expenses;
    private static final int CHART_WIDTH = 328;
    private static final int CHART_HEIGHT = 220;

    public TrendChartPanel() 
    {
        expenses = new ArrayList<>();
        this.setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
    }

    public void updateChart(List<LogExpense> newExpenses) 
    {
        this.expenses = new ArrayList<>(newExpenses);
        System.out.println("TrendChartPanel Expenses: " + this.expenses);
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.WHITE);

        int width = getWidth();
        int height = getHeight();

        int chartWidth = width - 50;
        int chartHeight = height - 50;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(50, 20, chartWidth, chartHeight);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(50, chartHeight + 20, chartWidth + 50, chartHeight + 20);
        g2d.drawLine(50, 20, 50, chartHeight + 20); 

        double maxAmount = 3000.0;

        int yGridCount = 6; 
        double yStep = maxAmount / yGridCount;
        int yLineSpacing = chartHeight / yGridCount;

        for (int i = 0; i <= yGridCount; i++) 
        {
            int y = chartHeight + 20 - (i * yLineSpacing);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawLine(50, y, chartWidth + 50, y); 
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.format("%.0f", i * yStep), 10, y+5);
        }

        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int xLineSpacing = chartWidth / months.length;

        for (int i = 0; i < months.length; i++) 
        {
            int x = 50 + (i * xLineSpacing) + (xLineSpacing / 2);
            g2d.drawString(months[i], x - 10, chartHeight + 40); 
        }
         int pointDiameter = 8; 

        if (expenses.isEmpty())
        {
            for (int i = 0; i < months.length; i++) {
                int x = 50 + (i * xLineSpacing) + (xLineSpacing / 2);
                int y = chartHeight + 20; 
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillOval(x - (pointDiameter / 2), y - (pointDiameter / 2), pointDiameter, pointDiameter);
            }
            return;
        }
                for (Map.Entry<String, Double> entry : calculateMonthlyTotals().entrySet()) 
        {
            int monthIndex = Arrays.asList(months).indexOf(entry.getKey());
            if (monthIndex < 0) continue; 

            int x = 50 + (monthIndex * xLineSpacing) + (xLineSpacing / 2);
            int y = chartHeight + 20 - (int) ((entry.getValue() / maxAmount) * chartHeight);

            g2d.setColor(Color.BLUE);
            g2d.fillOval(x - (pointDiameter / 2), y - (pointDiameter / 2), pointDiameter, pointDiameter);
        }
    }

    private Map<String, Double> calculateMonthlyTotals() 
    {
        Map<String, Double> monthlyTotals = new HashMap<>();
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        for (LogExpense expense : expenses) 
        {
            try 
            {
                String date = expense.getDate();
                if (date.matches("\\d{2}/\\d{2}/\\d{4}")) 
                {
                    String month = date.split("/")[0];
                    double amount = expense.getAmount();
                   
                    monthlyTotals.put(month, monthlyTotals.getOrDefault(month, 0.0) + amount);
                } 
                else 
                {
                    System.err.println("Invalid date format: " + date);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (String month : months) 
        {
            monthlyTotals.putIfAbsent(month, 0.0);
        }
        System.out.println("Monthly Totals: " + monthlyTotals);
        return monthlyTotals;
    }
}

// Main class to run the application
public class ExpenseTracker {
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(Frame::new);
    }
}
