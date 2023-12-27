import javax.swing.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.*;  
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

public class WindowTransactions extends JFrame {

  private JPanel contentPane;
  private JLabel endT;
  private JTextField textFieldOne;
  private JTextField textFieldTwo;
  private BankAccount chequing;
  private BankAccount savings;
  private String accountName;
  private JTextArea transactions;
  private JScrollPane scrollPane;

  //frame
  public WindowTransactions(String accountName,BankAccount chequing,BankAccount savings) {
    this.chequing = chequing; //recording account information
    this.savings = savings;
    this.accountName = accountName;
    setTitle("Transactions"); //frame
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(0, 0, 330, 330); 
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(null); 
    setContentPane(contentPane);

    JLabel lblNumberOne = new JLabel("Start Time");  
    lblNumberOne.setBounds(11, 30, 90, 21);
    contentPane.add(lblNumberOne);
    endT = new JLabel("End Time");
    endT.setBounds(11, 55, 90, 21);
    contentPane.add(endT);
    textFieldOne = new JTextField();
    textFieldOne.setBounds(90, 30, 125, 20); 
    contentPane.add(textFieldOne);
    textFieldTwo = new JTextField();
    textFieldTwo.setBounds(90, 55, 125, 20); 
    contentPane.add(textFieldTwo);
    
    JLabel form = new JLabel(" YYYY-MM-DD "); //input format
    form.setBounds(100,10,125,20);
    contentPane.add(form);
    JButton btnCalculate = new JButton("Find");
    btnCalculate.addMouseListener(new MouseAdapter() { //event handler that finds transactions 
      public void mouseClicked(MouseEvent e) {
        find();
      }
    });
    btnCalculate.setBounds(220, 28, 100, 46);
    contentPane.add(btnCalculate);
    JButton cancel = new JButton("Cancel");
    cancel.setBounds(115, 270, 100, 25);
    contentPane.add(cancel);
    cancel.addMouseListener(new MouseAdapter() { 
      public void mouseClicked(MouseEvent e) {//event handler to return you to window one
        returnBack();
      }
    });
    transactions= new JTextArea(); //text area which will display all transactions
    transactions.setEditable(false); 
    scrollPane = new JScrollPane(transactions);
    scrollPane.setBounds(10,80,310,180);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    contentPane.add(scrollPane);
    }
  public void find() {
    transactions.setText("");
    ArrayList<Transaction> depWith = new ArrayList(); //holds transactions
    String endTime = String.valueOf(textFieldTwo.getText()); //user input end time
    String startTime = String.valueOf(textFieldOne.getText()); //user input start time
    DateTimeFormatter formatting= DateTimeFormatter.ofPattern("yyyy-MM-dd"); //input format
    
    if (startTime.equals("")){ //setting no input as null
      startTime=null;
    } 
    if  (endTime.equals("")){ //seting no output as null
      endTime=null;
    } 
    if (startTime!=null && endTime!=null){ //if both inputs aren't null 
      LocalDate startDate = LocalDate.parse(startTime, formatting);
      LocalDate endDate = LocalDate.parse(endTime, formatting);
      LocalDateTime startDateTime=LocalDateTime.of(startDate, LocalDateTime.now().toLocalTime()); 
      LocalDateTime endDateTime=LocalDateTime.of(endDate, LocalDateTime.now().toLocalTime());
      if (accountName.equals("Chequing")){ 
        ArrayList<Transaction> transactions = chequing.getTransactions(startDateTime, endDateTime); //arraylist for chequing transactions
        depWith=transactions; //storing them in the depWith arraylist
      }else if (accountName.equals("Savings")){
        ArrayList<Transaction> transactions = savings.getTransactions(startDateTime, endDateTime);
        //Savings array list
        depWith=transactions; //storing them in the depWith arraylist
      }
    }else if (startTime==null && endTime!=null){ //if start time is null
      LocalDate endDate = LocalDate.parse(endTime, formatting); 
      LocalDateTime endDateTime=LocalDateTime.of(endDate, LocalDateTime.now().toLocalTime());
      //only an endDateTime variable is created because the start time is null
      if (accountName.equals("Chequing")){
        ArrayList<Transaction> transactions = chequing.getTransactions(null, endDateTime);
        depWith=transactions;
      }else if (accountName.equals("Savings")){
        ArrayList<Transaction> transactions = savings.getTransactions(null, endDateTime);
        depWith=transactions;
      }
    }else if (startTime!=null && endTime==null) { //if endtime is null
      LocalDate startDate = LocalDate.parse(startTime, formatting);
      LocalDateTime startDateTime=LocalDateTime.of(startDate, LocalDateTime.now().toLocalTime());
      //only an startDateTime variable is created because the end time is null
      if (accountName.equals("Chequing")){
        ArrayList<Transaction> transactions = chequing.getTransactions(startDateTime, null);
        depWith=transactions;
      }else if (accountName.equals("Savings")){
        ArrayList<Transaction> transactions = savings.getTransactions(startDateTime, null);
        depWith=transactions;
      }
    }else if (startTime==null && endTime==null){ //if both inputs are null
      if (accountName.equals("Chequing")){
        ArrayList<Transaction> transactions = chequing.getTransactions(null, null);
        depWith=transactions;
      }else if (accountName.equals("Savings")){
          ArrayList<Transaction> transactions = savings.getTransactions(null, null);
          depWith=transactions;
      }
    }
    DateTimeFormatter formatters= DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"); //output format
    for(int i=0; i<depWith.size(); i++){ //outputs all transactions in arraylist
      double bal = depWith.get(i).getAmount(); //retrieves the double value amount of each transaction
      String finalBalance = String.format("%.2f",bal); //formats the amount of the transaction into a string
      transactions.append(depWith.get(i).getDescription()+": "+depWith.get(i).getTransactionTime().format(formatters)+": $"+finalBalance+"\n"); 
    }
  }
  public void returnBack(){
    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
}