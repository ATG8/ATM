package ATM;

//import packages
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Date: 7/12/16
 * Class:
 * Author: ATG8
 * Purpose: This is the user defined checked exception.
 */
public class Account extends JFrame{
    
    // set variables
    public static int trackWithdrawals = 0;
    public double balance;
    public double userInput;
    public boolean userAcceptance = true;
    
    
    // default constructor
    public Account(){
    }
    

    // Set balance
    public void setBal(double balance){
        this.balance = balance;
    }
    
    
    // Get balance
    public double getBal(){
        return this.balance;
    }
    
    
    // Get user acceptance
    public boolean getAcceptance(){
        return userAcceptance;
    }
    
    
    // Subclass for creating Checking account
    public class Checking extends Account{
        public Checking(){
        }
    }
    
    
    // Sublcass for creating Savings account
    public class Savings extends Account{
        public Savings(){
        }
    }
    
       
    // Withdraw method
    public void w(double userInput) throws InsufficientFunds{
        double serviceCharge = userInput + 1.5; //add $1.50 to user input
        ++trackWithdrawals;
        
        // If user has used up all 4 free withdrawals
        if(trackWithdrawals > 4){
            
            // Set variables or user service charge agreement
            int optionButton = JOptionPane.YES_NO_OPTION;
            int optionResult = JOptionPane.showConfirmDialog(this, "You have "
                    + "used up your four free withdrawals.\nBy selecting "
                    + "'Yes', you agree to a $1.50 service charge.\n"
                    + "Select 'No' to cancel.", "Service Charge Agreement",
                    optionButton);
            // If user selects 'Yes' and agrees to service charge
            if(optionResult == 0){
                userAcceptance = true;
                if(serviceCharge <= this.balance){
                    this.balance -= serviceCharge;
                }else{
                    double needs = serviceCharge - this.balance;
                    throw new InsufficientFunds(needs);
                }
            }else{
                userAcceptance = false;
            }
        // Else user still has free withdrawals, no service contract needed
        }else{    
            if(userInput <= this.balance){
                this.balance -= userInput;    
            }else{
                double needs = userInput - this.balance;
                throw new InsufficientFunds(needs);
            }
        }
    } //end Withdraw method
    
    
    // Deposit method
    public void d(double deposit){
        this.balance += deposit;
    }
    
    
    // Transfer to account method
    public void tTo(double transfer){
        this.balance += transfer;
    }
    
    
    // Transfer from account method
    public void tFrom(double transfer) throws InsufficientFunds{
        if(transfer > this.balance){
            double needs = transfer - this.balance;
            throw new InsufficientFunds(needs);
        }else{
            this.balance -= transfer;
        }
    }
} //end Account class
