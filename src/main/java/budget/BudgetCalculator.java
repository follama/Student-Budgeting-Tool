//Not directly using the budget base but using it as inspo & taking some of the code and altering it where appropriate
package budget;

//import swing 
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashMap;
import java.util.Map;

//class created based on jswing
public class BudgetCalculator extends JPanel {
    //declares the instance variables
    private JButton calculateButton;
    private JButton exitButton;
    private JButton undoButton;
    private JTextField wagesField, loansField, cashBackField, billsField, rentField, foodShopField, totalIncomeField;
    private JComboBox<String> wagesTP, loansTP, cashBackTP, billsTP, rentTP, foodShopTP;
    private MultiLevelUndo stateManager = new MultiLevelUndo(); // creates an object of the multilevel undo class to access the methods
    private boolean isRestoringState = false;


    //constructor that initialises the componets/ makes them accessible throughout the class
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
        wagesTP = new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly", "Per Year"}); //extension i) 
        addComponent(wagesTP, 1, 2); // Add to the right hand side of wages field
        // Add ActionListener to the update calculations and do that to the other fields below 
        wagesTP.addActionListener(e -> {
            if (!isRestoringState) saveCurrent();
        });
        wagesField.addFocusListener(new FocusAdapter() {// exercise ii)
            @Override
            public void focusLost(FocusEvent e) {
                if (!isRestoringState) saveCurrent();
            }
        });

        loansField = addLabelAndTextField("Loans", "", 2, 0);
        loansTP = new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly","Per Year"});
        addComponent(loansTP, 2, 2);
        loansTP.addActionListener(e -> {
            if (!isRestoringState)  saveCurrent();
        });
        
        loansField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!isRestoringState)  saveCurrent();
               }
            });

        cashBackField = addLabelAndTextField("Cash Back", "", 3, 0);
        cashBackTP =  new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly" , "Per Year"});
        addComponent(cashBackTP, 3, 2);
        cashBackTP.addActionListener(e -> {
            if (!isRestoringState) saveCurrent();
        });
        cashBackField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!isRestoringState)  saveCurrent();
                }
            });

        //Expesnses Label
        addLabelAndTextField("EXPENSES", null, 4, 0);

        //bills, rent & foodShop
        billsField = addLabelAndTextField("Bills", "", 5, 0);
        billsTP =  new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly" , "Per Year"});
        addComponent(billsTP, 5, 2);
        billsTP.addActionListener(e -> {
            if (!isRestoringState) saveCurrent();
        });
        billsField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!isRestoringState) saveCurrent();
                }
            });


        rentField = addLabelAndTextField("Rent", "", 6, 0);
        rentTP =  new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly","Per Year"});
        addComponent(rentTP, 6, 2);
        rentTP.addActionListener(e -> {
            if (!isRestoringState) saveCurrent();
        });
        rentField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!isRestoringState) saveCurrent();
                }
            });


        foodShopField = addLabelAndTextField("Food Shop", "", 7, 0);
        foodShopTP =  new JComboBox<>(new String[]{"Per Week", "Per Month", "Weekly to Monthly" , "Per Year"});
        addComponent(foodShopTP, 7, 2);
        foodShopTP.addActionListener(e -> {
            if (!isRestoringState)  saveCurrent();
        });
        foodShopField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!isRestoringState) saveCurrent();
                }
            });


        //Total Income Label
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, 8, 0);
        totalIncomeField = createTextField("0", false, 10);
        addComponent(totalIncomeField, 8, 1);

        //calculate button
        calculateButton = createButton("Calculate", e -> calculateTotalIncome());
        addComponent(calculateButton, 9, 0);

        //undo buttons
        undoButton = createButton("Multilevel Undo", e -> undoLastAction());
        addComponent(undoButton, 10, 0);

        JButton undoButton2 = createButton("Single Level Undo", e -> singleLevelUndo());
        addComponent(undoButton2, 11, 0);

        //exit button
        exitButton = createButton("Exit", e -> System.exit(0));
        addComponent(exitButton, 12, 0);
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
        constraints.insets = new Insets(3, 5, 3, 5); //paddinggg
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
        totalIncomeField.setForeground(totalIncome >= 0 ? Color.BLACK : Color.RED); //can also use an if else statement but this is just faster

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

    //based on the case that the user picks in the combo box, this methods turns it into a string and executes the calculation based on the choice of the user
    private double timePeriod(double inputVal, JComboBox<String> timePeriodComboBox){
        String tp = (String) timePeriodComboBox.getSelectedItem(); 
        switch (tp) { 
            case "Per Week":
                return inputVal * 52;
            case "Per Month":
                return inputVal * 12;
            case "Weekly to Monthly Equivalent":
            return inputVal * 4.3333333;
            default:
                return inputVal; // Default is Per Year 
        }
    }

    private void addFocusListenerToField(JTextField field) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calculateTotalIncome();
            }
        });
    }

    //gets from the MultiLevelUndo class and saves the state of all the filds
    private void saveCurrent() {
        Map<String, Object> currentState = new HashMap<>();
        currentState.put("Wages", wagesField.getText());
        currentState.put("WagesTP", wagesTP.getSelectedItem());
        currentState.put("Loans", loansField.getText());
        currentState.put("LoansTP", loansTP.getSelectedItem());
        currentState.put("CashBack", cashBackField.getText());
        currentState.put("CashBackTP", cashBackTP.getSelectedItem());
        currentState.put("Bills", billsField.getText());
        currentState.put("BillsTP", billsTP.getSelectedItem());
        currentState.put("Rent", rentField.getText());
        currentState.put("RentTP", rentTP.getSelectedItem());
        currentState.put("FoodShop", foodShopField.getText());
        currentState.put("FoodShopTP", foodShopTP.getSelectedItem());
        
        //checks if the state of te stack is different and if it is then save the current state
        if (stateManager.isStateDifferent(currentState)){
            stateManager.saveCurrent(currentState);
        }

        calculateTotalIncome(); //this automaticall updates the totals put inside the saveCurrent method as it saves lines of code and improve readability 
    }

    //restore state to previous value
    private void restoreState(Map<String, Object> state) {
        isRestoringState = true; // this disables the listeners
        try {
            wagesField.setText((String) state.get("Wages"));
            wagesTP.setSelectedItem(state.get("WagesTP"));
            loansField.setText((String) state.get("Loans"));
            loansTP.setSelectedItem(state.get("LoansTP"));
            cashBackField.setText((String) state.get("CashBack"));
            cashBackTP.setSelectedItem(state.get("CashBackTP"));
            billsField.setText((String) state.get("Bills"));
            billsTP.setSelectedItem(state.get("BillsTP"));
            rentField.setText((String) state.get("Rent"));
            rentTP.setSelectedItem(state.get("RentTP"));
            foodShopField.setText((String) state.get("FoodShop"));
            foodShopTP.setSelectedItem(state.get("FoodShopTP"));
            calculateTotalIncome(); // Update total income
        } catch (Exception e) { //if there's an error show this message 
            JOptionPane.showMessageDialog(this, "Error restoring state: " + e.getMessage());
        } finally {
            isRestoringState = false; // Re-enable listeners
        }
    }
    
    //undoes the previous action - mulitilevel
    private void undoLastAction() {
        Map<String, Object> lastState = stateManager.undoLastState(); // gets the last state
        if (lastState == null) {
            JOptionPane.showMessageDialog(this, "There is nothing to undo");
            return;
        }

        // Restore state to previous state 
        restoreState(lastState);
    }

    //undoes the previous action - singlelevel
    private void singleLevelUndo() {
        Map<String, Object> lastState2 = stateManager.peekLastState();
        if (lastState2 == null) {
            JOptionPane.showMessageDialog(this, "There is nothing to undo");
            return;
        }
        restoreState(lastState2); // Restore state
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
