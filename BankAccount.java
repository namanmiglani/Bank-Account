import java.util.ArrayList;
import java.time.LocalDateTime;

public class BankAccount{
  //Create your BankAccount object here and run your unit tests!
  //instance variables
  String accountNumber;
  double balance;
  double withdrawalFee;
  double annualInterestRate;
  //accesors
  public String getAccountNumber(){
    return accountNumber;
  }
  public double getBalance(){
    return balance;
  }
  public double getWithdrawalFee(){
    return withdrawalFee;
  }
  public double getAnnualInterestRate(){
    return annualInterestRate;
  }
  //mutators
  public void setWithdrawalFee(double withdrawalFee){
    this.withdrawalFee = withdrawalFee;
  }

  public void setAnnualInterestRate(double annualInterestRate){
    this.annualInterestRate = annualInterestRate;
  }
  //constructors
  public BankAccount(String accountNumber){
    this.accountNumber=accountNumber;
  }

  public BankAccount(String accountNumber, double initialBalance){
    this.accountNumber=accountNumber;
    balance=initialBalance; //setting balance to the inital balance
  }
  public BankAccount(String accountNumber,double balance,double withdrawalFee,double annualInterestRate){
    this.accountNumber=accountNumber;
    this.balance=balance;
    this.withdrawalFee=withdrawalFee;
    this.annualInterestRate=annualInterestRate;
   }

  public ArrayList<Transaction> getTransactions = new ArrayList<Transaction>(); //using transaction file 
  //public methods
  //overloaded methods
  public void deposit(LocalDateTime transactionTime, double amount, String description){
    this.balance += amount;
    getTransactions.add(new Transaction(transactionTime, amount, description));
  }
  public void deposit(double amount, String description){
    this.balance += amount; //adding deposit to balance
    getTransactions.add(new Transaction(amount, description));
  }
  public void withdraw(LocalDateTime transactionTime, double amount, String description){
    balance -= (amount + withdrawalFee);
    getTransactions.add(new Transaction(transactionTime, amount, description));
  }
  public void withdraw(double amount, String description){
    balance-= (amount+withdrawalFee); //removing withdrawal + fee from balance
    getTransactions.add(new Transaction(amount, description));
  }
  //returns transactions based of time 
  public ArrayList<Transaction> getTransactions(LocalDateTime startTime, LocalDateTime endTime){
    for(int i = 0; i< (getTransactions.size()-1); i++){ //sorting transactions based of time
      if ((getTransactions.get(i).getTransactionTime()).compareTo(getTransactions.get(i+1).getTransactionTime()) > 0){
        getTransactions.add(getTransactions.get(i));
        getTransactions.remove(i);
      }
    }
  

  if (startTime == null && endTime==null){
    return getTransactions;
    
  }else if(startTime==null){
    ArrayList<Transaction> transactionList = new ArrayList<>();
    for (int i = 0; i< (getTransactions.size()); i++){
      if ((getTransactions.get(i).getTransactionTime()).compareTo(endTime)<=0){
        transactionList.add(getTransactions.get(i));
      }
    }
    return transactionList;
  }else if(endTime==null){
    ArrayList<Transaction> transactionList = new ArrayList<>();
    for (int i = 0; i< (getTransactions.size()); i++){
      if ((getTransactions.get(i).getTransactionTime()).compareTo(startTime)>=0){
        transactionList.add(getTransactions.get(i));
      }
    }
    return transactionList;
  }else{
    ArrayList<Transaction> transactionList = new ArrayList<>();
    for (int i = 0; i< (getTransactions.size()); i++){
      if ((getTransactions.get(i).getTransactionTime()).compareTo(startTime) >= 0 && (getTransactions.get(i).getTransactionTime()).compareTo(endTime) <= 0){
        transactionList.add(getTransactions.get(i));
      }
    }  
      return transactionList;
    }
  }
  //editing array based of money being withrawn or deposited  
  public void deposit(double amount){
    balance += amount;
    getTransactions.add(new Transaction(amount, "Deposited"));
  } 
  public void withdraw(double amount){
    balance -= (amount+withdrawalFee);
    getTransactions.add(new Transaction(amount, "Withdrawn"));
  }
  

  public boolean isOverDrawn(){ //determining if balance is negative
    if (balance<0){
      return true;
    }
    else{
      return false;
    }
    
  }
  //output rounded to 2 decimal places
  public String toString(){
    if (isOverDrawn()){
      return "BankAccount " + accountNumber + ": " + "($"+ String.format("%.2f" , Math.abs(balance))+")";
    }
    else{
      return "BankAccount " + accountNumber + ": " + "$" + String.format("%.2f",balance);
    }
  }
}