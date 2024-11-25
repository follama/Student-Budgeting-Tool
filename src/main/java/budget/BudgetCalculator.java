//Not directly using the budget base but using it as inspo & taking some of the code and altering it where appropriate

package budget;

import javax.print.attribute.standard.MediaSize.Other;

//import swing 
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//class created based on jswing
public class BudgetCalculator extends JPanel {
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    //widgets that we will use 
    private JButton calculateButton;   // Calculate button
    private JButton exitButton;        // Exit button
    private JButton undoButtton;       // Undo button
    private JTextField wagesField;     // Wages text field
    private JTextField loansField;     // Loans text field
    private JTextField cashBackField;       // Cash back text field
    private JTextField billsField;    // Bills text field
    private JTextField rentField;     // Rent text field
    private JTextField foodShopField;  // Food Shop text field
    private JTextField totalIncomeField; // Total Income field

    //the constructor - creates th UI
    public BudgetCalculator(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout());  // use GridBag layout
        initComponents();  // initalise components
    }

    //method with initialises the components
    private void initComponents(){

        //Top row (0) - the Income Label
        addLabelAndTextField("INCOME", null, 0, 0);

        // Middle row (4) - The Expenses Label
        addLabelAndTextField("EXPENSES", null, 4, 0);

        //wages, loans and cashback
        wagesField = addLabelAndTextField("Wages", "", 1, 0);
        loansField = addLabelAndTextField("Loans", "", 2, 0);
        cashBackField = addLabelAndTextField("Cash Back", "TOOL_TIP_TEXT_KEY", 3, 0);

        //bills, rent, food 
        billsField = addLabelAndTextField("Bills", "TOOL_TIP_TEXT_KEY", 5, 0);
        rentField = addLabelAndTextField("Rent", "TOOL_TIP_TEXT_KEY", 6, 0);
        foodShopField = addLabelAndTextField("Food Shop", "TOOL_TIP_TEXT_KEY", 7, 0);

        //Total Income 
        JLabel totalIncomeLabel = new JLabel("Total Income");
         addComponent(totalIncomeLabel, 8, 0);
        totalIncomeField = createTextField("0", false, 10); // Read-only
        addComponent(totalIncomeField, 8, 1);

        // Rows 9-11 - Buttons
        calculateButton = createButton("Calculate", e -> calculateTotalIncome());
        addComponent(calculateButton, 9, 0);

        undoButtton = createButton("Undo", e -> undoLastAction());
        addComponent(undoButtton, 10, 0);

        exitButton = createButton("Exit", e -> System.exit(0));
        addComponent(exitButton, 11, 0);

        // set up  listeners (in a spearate method)
        initListeners();
    }

    private JTextField addLabelAndTextField(String labelText, String initialText, int row, int col) {
        JLabel label = new JLabel(labelText);
        addComponent(label, row, col);
    
        if (initialText != null) { // If a text field is associated with the label
            JTextField textField = createTextField(initialText, true, 10);
            addComponent(textField, row, col + 1);
            return textField;
        }
        return null; // No text field created
    }

    private JTextField createTextField(String text, boolean editable, int columns) {
        JTextField textField = new JTextField(text, columns);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(editable);
        return textField;
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    // set up listeners
    // initially just for buttons, can add listeners for text fields
    private void initListeners() {

        // exitButton - exit program when pressed
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // calculateButton - call calculateTotalIncome() when pressed
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
              //  calculateTotalIncome();
            }
        });

    }

    // add a component at specified row and column in UI.  (0,0) is top-left corner
    private void addComponent(Component component, int gridrow, int gridcol) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;   // always use horixontsl filll
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);

    }

      // standard mathod to show UI
      private static void createAndShowGUI() {
 
        //Create and set up the window.
        JFrame frame = new JFrame("Budget Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        BudgetCalculator newContentPane = new BudgetCalculator(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    // standard main class to set up Swing UI
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }
    

}
