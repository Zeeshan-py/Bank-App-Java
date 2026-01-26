public class Account {

    private String accountNumber;
    private float balance;
    private Client accountHolder;
    static int accountCount = 0;

    public Account() {
        this.accountNumber = "ACC-" + (++accountCount);
        this.balance = 0.0f;
        this.accountHolder = new Client();
    }

    public Account(String accountNumber, float balance, Client accountHolder) {
        this.accountNumber = "ACC-" + (++accountCount);
        this.balance = balance;
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public float getBalance() {
        return balance;
    }
    public Client getAccountHolder() {
        return accountHolder;
    }

    public static int getAccountCount() {
        return accountCount;
    }   
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setBalance(float balance) {
        this.balance = balance;
    }
    public void setAccountHolder(Client accountHolder) {
        this.accountHolder = accountHolder;
    }

    public float withdraw(float amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
        return balance;
    }

    public float deposit(float amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid deposit amount.");
        }
        return balance;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + "\nBalance: " + balance + "\nAccount Holder: \n" + accountHolder.toString();
    }
}