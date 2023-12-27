import javax.swing.*; //imports
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.io.*;

public class WindowOne extends JFrame implements WindowListener{
  //object for each account 
  BankAccount chequing = new BankAccount("Chequing",0,0,0); 
  BankAccount savings = new BankAccount("Savings",0,0,0);
  //instance fields 
  private JPanel contentPane;
  private JLabel labelOne;
  private JLabel labelTwo;
  private JTextField textField;
  static JComboBox accounts;
  private WindowDeposit two;
  private WindowWithdraw one;
  private WindowTransactions three;
  private String accountName;
  public double findBal(String accountName) { //method for balance
    double bal=0;
    if (accountName.equals("Chequing")) {
      bal = chequing.balance;
    }else if (accountName.equals("Savings")) {
      bal = savings.balance;
    }
    return bal;
  }
  public static void main(String[] args) { //in order to run the GUI
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          WindowOne frame = new WindowOne();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  public void serialize(String accountName,BankAccount Account) {
    try { //method which serializes object into text files 
      if (accountName.equals("Chequing")) {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("chequing.txt"));
        outputStream.writeObject(Account);
        outputStream.close();
      }else if (accountName.equals("Savings")) {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("savings.txt"));
        outputStream.writeObject(Account);
        outputStream.close();
      }
    }catch (IOException e){
      System.out.println(e); 
    }
  }
  public BankAccount deSerialize(String accountName){
    BankAccount z = null;
    try{ //method deserializing the objects back into codable objects 
      if (accountName.equals("Chequing")) {
        FileInputStream inputStream = new FileInputStream("chequing.txt");
        ObjectInputStream reader = new ObjectInputStream(inputStream);
        z = (BankAccount)reader.readObject();
      }else if (accountName.equals("Savings")) {
        FileInputStream inputStream = new FileInputStream("savings.txt");
        ObjectInputStream reader = new ObjectInputStream(inputStream);
        z = (BankAccount)reader.readObject();
      }
    }catch (IOException e){
      System.out.println(e);
    }catch (ClassNotFoundException e){
      System.out.println(e);
    }
    return z;
  }
  public WindowOne() {
    if (deSerialize("Chequing")==(null)) { //deserializing chequing account object 
      chequing = new BankAccount("Chequing",0,0,0);
    } else {
      chequing = deSerialize("Chequing");
    }
    if (deSerialize("Savings")==(null)) { //deserializing savings account object 
      savings = new BankAccount("Savings",0,0,0);
    } else {
      savings = deSerialize("Savings");
    }
    accountName = "Chequing"; //setting up order for dropbox
    setTitle("NMG Banking"); //frame setup 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    setBounds(0, 0, 330, 330); 
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); 
    contentPane.setLayout(null); 
    setContentPane(contentPane);
    labelOne = new JLabel("Account:");  //labels
    labelOne.setHorizontalAlignment(JTextField.CENTER); 
    labelOne.setBounds(80, 50, 70, 50);
    contentPane.add(labelOne);
    labelTwo = new JLabel("Current Balance:");    
    labelTwo.setHorizontalAlignment(JTextField.CENTER); 
    labelTwo.setBounds(30, 200, 170, 20);
    contentPane.add(labelTwo);
    textField = new JTextField(""); //textfield showing the current balance in the respective account 
    textField.setEditable(false); 
    textField.setBounds(200, 200, 100, 20); 
    contentPane.add(textField);
    textField.setText("$0.00"); //initial balance
    JButton btnPress = new JButton("Deposit"); //buttons
    JButton btnPress1 = new JButton("Withdraw");
    JButton btnPress2 = new JButton("Transactions");
    btnPress.setBounds(35, 235, 100, 30);
    btnPress1.setBounds(190, 235, 110, 30);
    btnPress2.setBounds(95, 150, 150, 30);
    contentPane.add(btnPress);
    contentPane.add(btnPress1);
    contentPane.add(btnPress2);
    String [] accountSelect={"Chequing","Savings"}; //creating dropbox
    accounts= new JComboBox(accountSelect);
    accounts.setBounds(175, 60, 100, 30);
    contentPane.add(accounts);

    accounts.addActionListener(new ActionListener() { //finding balance depending on the account chosen 
      public void actionPerformed(ActionEvent e) {
        double bal = 0; //creating bal 
        if (accounts.getSelectedItem().equals("Savings")) {
          accountName = "Savings"; //resetting the account name if savings is chosen 
          bal = savings.balance; //finding balance of savings 
        }else if (accounts.getSelectedItem().equals("Chequing")) {
          accountName = "Chequing";//resetting the account name if chequing is chosen 
          bal = chequing.balance; //finding balance of chequing 
        }
        if(bal>=0){ //black font if account not overdrawn 
          textField.setForeground(Color.BLACK);
          textField.setText("$" + String.format("%.2f",bal));
        }else if (bal<0) { //red font if account is overdrawn 
          textField.setForeground(Color.RED);
          textField.setText("$" + String.format("%.2f",Math.abs(bal)));
        }
      }
    });
    btnPress.addMouseListener(new MouseAdapter() { //if the deposit button is pressed the event handler will open the deposit window
      public void mouseClicked(MouseEvent e) {
        openWindowDeposit();
      }
      
    });
    btnPress1.addMouseListener(new MouseAdapter() { //if the withdraw button is pressed the event handler will open the withdraw window
      public void mouseClicked(MouseEvent e) {
        openWithdraw();
      }
    });
    btnPress2.addMouseListener(new MouseAdapter() { //if the Transactions button is pressed the event handler will open the Transactions window
      public void mouseClicked(MouseEvent e) {
        openTransaction();
      }
    });
  }
  public void openWindowDeposit(){
    two = new WindowDeposit(accountName, chequing, savings); //transferring the data of both accounts and account chosen 
    two.addWindowListener(this);
    two.setVisible(true);
  }
  public void openWithdraw(){
    one = new WindowWithdraw(accountName, chequing, savings);
    one.addWindowListener(this); 
    one.setVisible(true);
  }
  public void openTransaction(){
    three = new WindowTransactions(accountName, chequing, savings);
    three.addWindowListener(this); 
    three.setVisible(true);
  }
  
  public void windowOpened(WindowEvent e) {
  }
  public void windowClosing(WindowEvent e) {
    serialize("Chequing",chequing); //serialize both objects as the main window closes 
    serialize("Savings",savings); 
  }
  public void windowClosed(WindowEvent e) {  
    if(findBal(accountName)>=0){ //refreshing the textfield 
      textField.setForeground(Color.BLACK);
      double bal=findBal(accountName);
      textField.setText("$" + String.format("%.2f",bal));
    }else if (findBal(accountName)<0) {
      textField.setForeground(Color.RED);
      double bal=findBal(accountName);
      textField.setText("$" + String.format("%.2f",Math.abs(bal)));
    }
  }
  public void windowIconified(WindowEvent e) {
  }
  public void windowDeiconified(WindowEvent e) {
  }
  public void windowActivated(WindowEvent e) {
  }
  public void windowDeactivated(WindowEvent e) {
  }
}