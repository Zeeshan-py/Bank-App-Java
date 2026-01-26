import java.util.List;

public class Client {
    private String id;
    private Person personalInfo;
    List<Account> accounts;
    static int count = 0;

    public Client() {
        this.id = "CL-" + (++count);
        this.personalInfo = new Person();
    }

    public Client(String id, Person personalInfo, List<Account> accounts) {
        this.id = "CL-" + (++count);
        this.personalInfo = personalInfo;
        this.accounts = accounts;
    }

    public String getId() {
        return id;
    }
    public Person getPersonalInfo() {
        return personalInfo;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
    public static int getCount() {
        return count;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setPersonalInfo(Person personalInfo) {
        this.personalInfo = personalInfo;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }


    public float totalAmount() {
        float total = 0.0f;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void withdraw(String accountNumber, float amount) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                account.withdraw(amount);
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public void deposit(String accountNumber, float amount) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                account.deposit(amount);
                return;
            }
        }
        System.out.println("Account not found.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Client ID: ").append(id).append("\n");
        sb.append("Personal Info:\n").append(personalInfo.toString()).append("\n");
        sb.append("Accounts:\n");
        for (Account account : accounts) {
            sb.append(account.toString()).append("\n");
        }
        return sb.toString();
    }
}