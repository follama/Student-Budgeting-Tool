package budget;

//import swing 
import javax.swing.*;

public class Calculation extends JPanel {

    public void calculateTotalIncome(){

        
        // get values from income text fields.  valie is NaN if an error occurs
        double wages = getTextFieldValue(wagesField);
        double loans = getTextFieldValue(loansField);
        double bills = getTextFieldValue(billsField);

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(wages) || Double.isNaN(loans) || Double.isNan(bills)) {
            totalIncomeField.setText("");  // clear total income field
            wages = 0.0;
            return wages;   // exit method and do nothing
    }

}
