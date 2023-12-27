import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class WindowWithdraw extends JFrame{
  
  private JPanel contentPane;
  private JTextField textFieldOne;
  private BankAccount chequing;
  private BankAccount savings;
  private String accountName;
//frame
  public WindowWithdraw(String accountName,BankAccount chequing,BankAccount savings) {
    this.chequing = chequing; //account info
    this.savings = savings;
    this.accountName = accountName;
    setTitle("Withdraw");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(0, 0, 330, 330); //window
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(null); 
    setContentPane(contentPane);

    JLabel lblNumberOne = new JLabel("Amount:");
    lblNumberOne.setBounds(65, 46, 90, 21);
    contentPane.add(lblNumberOne);

    textFieldOne = new JTextField();
    textFieldOne.setBounds(160, 46, 96, 20);
    textFieldOne.setColumns(10);
    contentPane.add(textFieldOne);

    JButton btnCalculate = new JButton("Done");
    btnCalculate.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e) {
        calculate(); //calculates balance
    }
  });  

    btnCalculate.setBounds(115, 155, 100, 25);
    contentPane.add(btnCalculate);

    JButton cancel = new JButton("Cancel");
    cancel.setBounds(115, 225, 100, 25);
    contentPane.add(cancel);
    cancel.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        returnBack(); //takes you back to window one
      }
    });
  }
   
  public void calculate() {//updating balance of the  account
    double currentBalance= Double.valueOf(textFieldOne.getText()); 
    if(accountName.equals("Chequing")){ 
      chequing.withdraw(currentBalance,"Withdrawal"); //description is provided for transactions
    } else {
      savings.withdraw(currentBalance,"Withdrawal");
    }
    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
  public void returnBack(){ //activated by cancel button 
    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); 
  }
}