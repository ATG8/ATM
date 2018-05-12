package ATM;

// import packages
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 * Date: 7/12/16
 * Class:
 * Author: ATG8
 * Purpose: This is the user defined checked exception.
 */
public class InsufficientFunds extends Exception{
    
    // declare variables
    JOptionPane fundWarning = new JOptionPane();
    
    // create output format
    public final DecimalFormat currency = new DecimalFormat("$0.00");
    
    // method for passing double to variable amount from Main
    public InsufficientFunds(double amountNeeded){
        
        // Display insufficient funds message with amount needed to complete transaction
        JOptionPane.showMessageDialog(fundWarning, "You do not have sufficient "
                + "funds to perform this transaction.\nYou are short "
                + currency.format(amountNeeded), "Insufficient Funds",
                JOptionPane.WARNING_MESSAGE);
    }
} //end InsufficientFunds class
