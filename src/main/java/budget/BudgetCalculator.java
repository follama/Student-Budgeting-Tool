//Not directly using the budget base but using it as inspo & taking some of the code and altering it where appropriate
package budget;

//import swing 
import javax.swing.*;
import java.awt.*;

//class created based on jswing
public class BudgetCalculator extends JPanel {
    //declares the instance variables
    private JButton calculateButton;
    private JButton exitButton;
    private JButton undoButtton;
    private JTextField wagesField, loansField, cashBackField, billsField, rentField, foodShopField, totalIncomeField;
    private JComboBox<String> wagesTP, loansTP, cashBackTP, billsTP, rentTP, foodShopTP;


    public BudgetCalculator() {
        setLayout(new GridBagLayout());
        initComponents();
    }

    //widgets that we will use 
    private void initComponents() {
        //Income Label
        addLabelAndTextField("INCOME", null, 0, 0);

        //wages, loans & cashback
        wagesField = addLabelAndTextField("Wages", "", 1, 0);
        wagesTP = new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly", "Per Year"});
        addComponent(wagesTP, 1, 2); // Add to the right hand side of wages field
        // Add ActionListener to the update calculations and do that to the other fields below 
        wagesTP.addActionListener(e -> calculateTotalIncome());

        loansField = addLabelAndTextField("Loans", "", 2, 0);
        loansTP = new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly","Per Year"});
        addComponent(loansTP, 2, 2);
        loansTP.addActionListener(e -> calculateTotalIncome());

        cashBackField = addLabelAndTextField("Cash Back", "", 3, 0);
        cashBackTP =  new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly" , "Per Year"});
        addComponent(cashBackTP, 3, 2);
        cashBackTP.addActionListener(e -> calculateTotalIncome());

        //Expesnses Label
        addLabelAndTextField("EXPENSES", null, 4, 0);

        //bills, rent & foodShop
        billsField = addLabelAndTextField("Bills", "", 5, 0);
        billsTP =  new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly" , "Per Year"});
        addComponent(billsTP, 5, 2);
        billsTP.addActionListener(e -> calculateTotalIncome());

        rentField = addLabelAndTextField("Rent", "", 6, 0);
        rentTP =  new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly","Per Year"});
        addComponent(rentTP, 6, 2);
        rentTP.addActionListener(e -> calculateTotalIncome());

        foodShopField = addLabelAndTextField("Food Shop", "", 7, 0);
        foodShopTP =  new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly" , "Per Year"});
        addComponent(foodShopTP, 7, 2);
        foodShopTP.addActionListener(e -> calculateTotalIncome());

        //Total Income Label
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, 8, 0);
        totalIncomeField = createTextField("0", false, 10);
        addComponent(totalIncomeField, 8, 1);

        //calculate button
        calculateButton = createButton("Calculate", e -> calculateTotalIncome());
        addComponent(calculateButton, 9, 0);

        //undo button
        undoButtton = createButton("Undo", e -> undoLastAction());
        addComponent(undoButtton, 10, 0);

        //exit button
        exitButton = createButton("Exit", e -> System.exit(0));
        addComponent(exitButton, 11, 0);
    }

    //this method simplifies the init - initialising - components and makes it less repetitive
    private JTextField addLabelAndTextField(String labelText, String initialText, int row, int col) {
        JLabel label = new JLabel(labelText);
        addComponent(label, row, col);

        if (initialText != null) {
            JTextField textField = createTextField(initialText, true, 10);
            addComponent(textField, row, col + 1);
            return textField;
        }
        return null;
    }

    //creates a text field
    private JTextField createTextField(String text, boolean editable, int columns) {
        JTextField textField = new JTextField(text, columns);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(editable);
        return textField;
    }

    //creates a new button
    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    //add a component at specified row and column in UI.  (0,0) is top-left corner
    private void addComponent(Component component, int gridrow, int gridcol) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = gridcol;
        constraints.gridy = gridrow;
        add(component, constraints);
    }

    //calculates the total income based off the user input 
    public double calculateTotalIncome() {
        //gets the input from the user from the respective JTextField and assigns the number to the variable 
        double wages = timePeriod(getTextFieldValue(wagesField), wagesTP);
        double loans = timePeriod(getTextFieldValue(loansField), loansTP);
        double cashBack = timePeriod(getTextFieldValue(cashBackField), cashBackTP);
        double bills = timePeriod(getTextFieldValue(billsField), billsTP);
        double rent = timePeriod(getTextFieldValue(rentField), rentTP);
        double foodShop = timePeriod(getTextFieldValue(foodShopField), foodShopTP);

        double totalIncome = (wages + loans + cashBack) - (bills + rent + foodShop);
        totalIncomeField.setText(String.format("%.2f", totalIncome));

        //create the surplus / deficit part
        totalIncomeField.setText(String.format("%.2f", totalIncome));
        //ref https://examples.javacodegeeks.com/java-development/desktop-java/swing/jlabel/set-foreground-color-in-jlabel/
        totalIncomeField.setForeground(totalIncome >= 0 ? Color.BLACK : Color.RED);

        return totalIncome;
    }

    private double getTextFieldValue(JTextField field) {
        String fieldString = field.getText();
        if (fieldString.isBlank()) {
            return 0; // returns zero if the test field is blank so the program still works
        } else { // if text field is not blank, parse it into a double
            try {
                return Double.parseDouble(fieldString);
            } catch (NumberFormatException ex) {// catch invalid number exception
                JOptionPane.showMessageDialog(this, "Invlid input, please enter a valid number");
                return Double.NaN;// return NaN to show that field is not a number
            }
        }
    }

    private double timePeriod(double inputVal, JComboBox<String> timePeriodComboBox){
        String tp = (String) timePeriodComboBox.getSelectedItem();
        switch (tp) {
            case "Per Week":
                return inputVal * 52;
            case "Per Month":
                return inputVal * 12;
            case "Weekly to Monthly":
            return inputVal * 4.3333333 * 12;
            default:
                return inputVal; // Default is Per Year 
        }
    }
        

    //NEED TO CODE THE UNDO BUTTON 
    private void undoLastAction() {
        JOptionPane.showMessageDialog(this, "Undo functionality not implemented yet.");
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Budget Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new BudgetCalculator());
        frame.pack();
        frame.setVisible(true); //displays the window
    }

    //main calss to set up the SWING UI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BudgetCalculator::createAndShowGUI);
    }
}
