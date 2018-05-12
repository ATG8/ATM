package ATM;

// import packages
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;

/**
 * Date: 7/12/16
 * Class:
 * Author: ATG8
 * Purpose: This is the main method that will test the ATM Machine.
 */
public class ATM extends JFrame{
    
    // set main window size
    private static final int WIN_WIDTH = 320, WIN_HEIGHT = 175,
                             INPUT_WIDTH = 225, INPUT_HEIGHT = 25;
    
    // create user interfaces
    private final JButton w = new JButton("Widthdraw");
    private final JButton d = new JButton("Deposit");
    private final JButton t = new JButton("Transfer to");
    private final JButton b = new JButton("Balance");
    private final JRadioButton c = new JRadioButton("Checking", true);
    private final JRadioButton s = new JRadioButton("Savings");
    private final JTextField userInput = new JTextField("");
    private final ButtonGroup account = new ButtonGroup();
    private final JOptionPane popup = new JOptionPane();
    
    // create account objects
    private static final Account checking = new Account().new Checking();
    private static final Account savings = new Account().new Savings();
    
    // create currency output format
    private final DecimalFormat currency = new DecimalFormat("$0.00");
    
    // create constructor
    public ATM (double cBal, double sBal){
        
        // main panel setup
        super("ATM Machine");
        setLayout(new GridBagLayout());
        GridBagConstraints design = new GridBagConstraints();
        setFrame(WIN_WIDTH, WIN_HEIGHT);
        setResizable(false);
        design.gridy = 2;
        
        // create organized nested panel of buttons
        JPanel nestedPanel = new JPanel();
        nestedPanel.setLayout(new GridLayout(3,2,10,10));
        add(nestedPanel);
        nestedPanel.add(w); //adds Withdraw
        nestedPanel.add(d); //adds Deposit
        nestedPanel.add(t); //adds Transfer to
        nestedPanel.add(b); //adds Balance
        account.add(c); //adds Checking radio button function
        account.add(s); //adds Savings radio button function
        nestedPanel.add(c); //adds Checking
        nestedPanel.add(s); //adds Savings
        
        // create nested inputBox below buttons
        JPanel inputBox = new JPanel();
        inputBox.setLayout(new GridLayout(1,1));
        add(inputBox, design);
        userInput.setPreferredSize(new Dimension(INPUT_WIDTH, INPUT_HEIGHT));
        inputBox.add(userInput);
        
        // initialize accounts and add listeners for buttons to perform actions
        initialAccounts(cBal, sBal);
        w.addActionListener(new WAction()); //Withdrawal button, WAction class
        d.addActionListener(new DAction()); //Deposit button, DAction class
        t.addActionListener(new TAction()); //Transfer to button, TAction class
        b.addActionListener(new BAction()); //Balance button, BAction class
        
        // display welcome message and beginning account information
        // before showing ATM Machine
        JOptionPane.showMessageDialog(popup, "Welcome to the ATM Machine.\n"
            + "Your beginning balances are:\nChecking Account: " + 
            currency.format(checking.balance) + "\nSavings Account: " +
            currency.format(savings.balance), "Welcome to the ATM Machine",
            JOptionPane.INFORMATION_MESSAGE);
        
    } //end constructor
    
    
    // method to initialize accounts and set balances passed from
    // main into the constructor
    public static void initialAccounts(double cBal, double sBal){
        checking.setBal(cBal);
        savings.setBal(sBal);
    }
    
    
    // class to use Withdraw button
    class WAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            // Tries to withdraw money from selected account, catches Insufficient
            // Funds exceptoion if not enough money is available.
            try{
                // Input from user must be more than zero and a multiple of 20
                if(isDouble() > 0 && isDouble() %20 == 0){
                    // If checking radio button is selected then...
                    if(c.isSelected()){
                        // Withdraw method from Account class is performed against
                        // checking account, using the double input by the user.
                        checking.w(isDouble());
                        // User begins with 4 free withdrawals to use for
                        // checking and/or savings accounts.  Withdraw method
                        // from Account class tracks number of withdrawals and
                        // when 5th attemp is made, user is prompted to accept
                        // a service charge of $1.50.  If the user accepts...
                        if(checking.getAcceptance() == true){
                            // Popup confirmation
                            JOptionPane.showMessageDialog(popup, 
                                    currency.format(isDouble()) + " has been "
                                    + "successfully withdrawn from your Checking "
                                    + "account.\nYour new balance is " +
                                    currency.format(checking.balance),
                                    "Checking Withdrawal Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        // Else user was prompted and declined service charge.
                        }else{
                            // Popup displays declined service agreement message.
                            JOptionPane.showMessageDialog(popup, "You have chosen "
                            + "to decline the service fee of $1.50 and cancel "
                            + "your transaction.\nChecking balance is still "
                            + currency.format(checking.balance), "Service Fee Declined",
                            JOptionPane.INFORMATION_MESSAGE);
                        }
                    // Else if savings radio button is selected then...
                    }else if(s.isSelected()){
                        // Withdraw from savings
                        savings.w(isDouble());
                        // If user accepts service charge...
                        if(savings.getAcceptance() == true){
                            // Popup confirmation
                            JOptionPane.showMessageDialog(popup, 
                                    currency.format(isDouble()) + " has been "
                                    + "successfully withdrawn from your Savings "
                                    + "account.\nYour new balance is " +
                                    currency.format(savings.balance),
                                    "Savings Withdrawal Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        // Else user declined service charge
                        }else{
                            // Popup declined message
                            JOptionPane.showMessageDialog(popup, "You have chosen "
                            + "to decline the service fee of $1.50 and cancel "
                            + "your transaction.\nSavings balance is still "
                            + currency.format(savings.balance), "Service Fee Declined",
                            JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    // resetInput() resets the input box to blank and focuses
                    resetInput();
                // Else the amount entered wasn't a double, was less than zero,
                // or wasn't an increment of $20
                }else{
                    JOptionPane.showMessageDialog(popup, "Amounts must be entered"
                            + " in $20 increments.\nPlease try again.",
                            "Invalid Withdrawal Amount",
                            JOptionPane.ERROR_MESSAGE);
                }
                resetInput();
            // Catch and display insufficient funds exception
            }catch(InsufficientFunds iFunds){
                resetInput();
            }
        }
    } //end Withdrawal class
    
    
    // class to use Deposit button
    class DAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(isDouble() > 0){
                if(c.isSelected()){
                    checking.d(isDouble());
                    JOptionPane.showMessageDialog(popup,
                            currency.format(isDouble()) + " has been deposited "
                            + "into your Checking account.\nYour new balance is "
                            + currency.format(checking.balance), "Checking Deposit"
                            + " Success", JOptionPane.INFORMATION_MESSAGE);
                }else if(s.isSelected()){
                    savings.d(isDouble());
                    JOptionPane.showMessageDialog(popup,
                            currency.format(isDouble()) + " has been deposited "
                            + "into your Savings account.\nYour new balance is "
                            + currency.format(savings.balance), "Savings Deposit"
                            + " Success", JOptionPane.INFORMATION_MESSAGE);
                }
                resetInput();
            }else{
                JOptionPane.showMessageDialog(popup, "Amount must be larger "
                        + "than 0.", "Invalid Deposit Amount",
                        JOptionPane.ERROR_MESSAGE);
            }
            resetInput();
        }
    } //end Deposit class
    
    
    // class to use Transfer to button
    class TAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                if(isDouble() > 0){
                    if(c.isSelected()){
                        savings.tFrom(isDouble()); //call tFrom method from Account
                        checking.tTo(isDouble()); //call tTo method from Account
                        JOptionPane.showMessageDialog(popup,
                                currency.format(isDouble()) + " has been transferred "
                                + "from your Savings into your Checking account.\n"
                                + "New Checking Balance: " + currency.format(checking.balance)
                                + "\nNew Savings Balance: " + currency.format(savings.balance),
                                "Transfer to Checking Success", JOptionPane.INFORMATION_MESSAGE);
                    }else if(s.isSelected()){
                        checking.tFrom(isDouble());
                        savings.tTo(isDouble());
                        JOptionPane.showMessageDialog(popup,
                                currency.format(isDouble()) + " has been transferred "
                                + "from your Checking into your Savings account.\n"
                                + "New Savings Balance: " + currency.format(savings.balance)
                                + "\nNew Checking Balance: " + currency.format(checking.balance),
                                "Transfer to Savings Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    resetInput();
                }else{
                    JOptionPane.showMessageDialog(popup, "Amount must be larger "
                            + "than 0.", "Invalid Transfer Amount",
                            JOptionPane.ERROR_MESSAGE);
                }
                resetInput();
            }catch(InsufficientFunds iFunds){
                resetInput();
            }
        }
    }//end Transfer to class
    
    
    // class to use Balance button
    class BAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(c.isSelected()){
                JOptionPane.showMessageDialog(popup, "Your current Checking "
                        + "account balance is " + currency.format(checking.getBal()),
                        "Current Checking Balance", 
                        JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(popup, "Your current Savings "
                        + "account balance is " + currency.format(savings.getBal()),
                        "Current Savings Balance", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        resetInput();
        }
    } // end Balance class
    
    
    // get userInput from inputBox
    public double isDouble(){
        try{
            return Double.parseDouble(userInput.getText());
        }catch(NumberFormatException e){}
        resetInput();
        // Returns zero and is caught by if logic for WAction, DAction, and
        // TAction classes to display message that amount is invalid
        return 0;
    } //end userInput error checking
    
    
    // clear user input and reset focus
    public void resetInput(){
        userInput.setText("");
        userInput.requestFocus();
    } //end inputBox clear and refocus
    
    
    // set main window size
    private void setFrame(int width, int height){
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } //end main window sizing
    
    
    // display main window and focus on inputBox
    public void display(){
        setVisible(true);
        userInput.requestFocus();
    }
    
    

    // Main
    public static void main(String[] args) {
        ATM start = new ATM(1000, 1000);
        start.display();
    } //end Main
} //end program
