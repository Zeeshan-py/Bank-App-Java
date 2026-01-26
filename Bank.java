import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Bank {

    private String name;
    List<Client> clients;
    List<Account> accounts;

    public Bank() {
        this.name = "";
    }
    public Bank(String name, List<Client> clients, List<Account> accounts) {
        this.name = name;
        this.clients = clients;
        this.accounts = accounts;
    }
    public String getName() {
        return name;
    }
    public List<Client> getClients() {
        return clients;
    }
    public List<Account> getAccounts() {
        return accounts;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    // 1. addClient
    public Client addClient(Person p) {
        Client newClient = new Client();
        newClient.setPersonalInfo(p);
        clients.add(newClient);
        saveClientsToFile("clients.txt");
        return newClient;
    }

    // 2. addAccount
    public Account addAccount(String id, float amount, Client c) {
        Account newAccount = new Account();
        newAccount.setBalance(amount);
        newAccount.setAccountHolder(c);
        
        // Add account to client's account list
        if (c.getAccounts() == null) {
            c.setAccounts(new java.util.ArrayList<>());
        }
        c.getAccounts().add(newAccount);
        
        // Add account to bank's account list
        accounts.add(newAccount);
        saveAccountsToFile("accounts.txt");
        return newAccount;
    }

    // 3. searchAccount(String id)
    public Account searchAccount(String id) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(id)) {
                return account;
            }
        }
        return null;
    }

    // 4. removeClient(String id)
    public Boolean removeClient(String id) {
        Client clientToRemove = null;
        
        // Find the client
        for (Client client : clients) {
            if (client.getId().equals(id)) {
                clientToRemove = client;
                break;
            }
        }
        
        if (clientToRemove != null) {
            // Remove all accounts of this client
            if (clientToRemove.getAccounts() != null) {
                accounts.removeAll(clientToRemove.getAccounts());
            }
            
            // Remove the client
            clients.remove(clientToRemove);
            saveClientsToFile("clients.txt");
            saveAccountsToFile("accounts.txt");
            return true;
        }
        return false;
    }

    // 5. totalAmount()
    public float totalAmount() {
        float total = 0.0f;
        for (Client client : clients) {
            if (client.getAccounts() != null) {
                for (Account account : client.getAccounts()) {
                    total += account.getBalance();
                }
            }
        }
        return total;
    }

    // 6. searchCustomerDetail(String CNIC)
    public Client searchCustomerDetail(int CNIC) {
        for (Client client : clients) {
            if (client.getPersonalInfo().getCNIC() == CNIC) {
                return client;
            }
        }
        return null;
    }

    // Save clients to file
    public void saveClientsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("# Client Data Format: Name, CNIC, Phone");
            for (Client client : clients) {
                writer.println(client.getPersonalInfo().getName() + "," + 
                             client.getPersonalInfo().getCNIC() + "," + 
                             client.getPersonalInfo().getPhoneNumber());
            }
        } catch (IOException e) {
            System.err.println("Error saving clients: " + e.getMessage());
        }
    }

    // Save accounts to file
    public void saveAccountsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("# Account Data Format: Client_CNIC, Balance");
            for (Account account : accounts) {
                writer.println(account.getAccountHolder().getPersonalInfo().getCNIC() + "," + 
                             account.getBalance());
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
}