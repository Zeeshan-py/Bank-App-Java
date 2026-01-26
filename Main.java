import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== Bank Management System =====\n");
        
        // Create Bank
        Bank myBank = new Bank("National Bank", new ArrayList<>(), new ArrayList<>());
        System.out.println("Bank Created: " + myBank.getName());
        
        // Load data from files
        loadClientsFromFile(myBank, "clients.txt");
        loadAccountsFromFile(myBank, "accounts.txt");
        
        System.out.println("\n----- Data Loaded from Files -----");
        System.out.println("Total Clients: " + myBank.getClients().size());
        System.out.println("Total Accounts: " + myBank.getAccounts().size());
        
        // Print All Client Details
        System.out.println("\n----- All Client Details -----");
        for (Client client : myBank.getClients()) {
            System.out.println("\nClient ID: " + client.getId());
            System.out.println(client.getPersonalInfo().toString());
            if (client.getAccounts() != null && !client.getAccounts().isEmpty()) {
                System.out.println("Accounts:");
                for (Account acc : client.getAccounts()) {
                    System.out.println("  - Account Number: " + acc.getAccountNumber() + ", Balance: " + acc.getBalance());
                }
            }
        }
        
        // Test operations if clients exist
        if (!myBank.getClients().isEmpty()) {
            Client client1 = myBank.getClients().get(0);
            
            // Test Deposit
            if (client1.getAccounts() != null && !client1.getAccounts().isEmpty()) {
                System.out.println("\n----- Testing Deposit -----");
                Account acc1 = client1.getAccounts().get(0);
                System.out.println("Account " + acc1.getAccountNumber() + " balance before deposit: " + acc1.getBalance());
                acc1.deposit(2000.0f);
                System.out.println("Deposited: 2000.0");
                System.out.println("Account " + acc1.getAccountNumber() + " balance after deposit: " + acc1.getBalance());
            }
            
            // Test Withdraw
            if (myBank.getClients().size() > 1) {
                Client client2 = myBank.getClients().get(1);
                if (client2.getAccounts() != null && !client2.getAccounts().isEmpty()) {
                    System.out.println("\n----- Testing Withdraw -----");
                    Account acc2 = client2.getAccounts().get(0);
                    System.out.println("Account " + acc2.getAccountNumber() + " balance before withdrawal: " + acc2.getBalance());
                    acc2.withdraw(2500.0f);
                    System.out.println("Withdrawn: 2500.0");
                    System.out.println("Account " + acc2.getAccountNumber() + " balance after withdrawal: " + acc2.getBalance());
                    
                    // Test invalid withdrawal
                    System.out.println("\nAttempting invalid withdrawal (amount > balance):");
                    acc2.withdraw(10000.0f);
                    
                    // Search Account
                    System.out.println("\n----- Testing Search Account -----");
                    String searchAccNum = acc2.getAccountNumber();
                    Account foundAccount = myBank.searchAccount(searchAccNum);
                    if (foundAccount != null) {
                        System.out.println("Account Found: " + foundAccount.getAccountNumber());
                        System.out.println("Balance: " + foundAccount.getBalance());
                        System.out.println("Account Holder: " + foundAccount.getAccountHolder().getPersonalInfo().getName());
                    }
                }
            }
            
            // Search Customer by CNIC
            System.out.println("\n----- Testing Search Customer by CNIC -----");
            int searchCNIC = client1.getPersonalInfo().getCNIC();
            Client foundClient = myBank.searchCustomerDetail(searchCNIC);
            if (foundClient != null) {
                System.out.println("Client Found: " + foundClient.getId());
                System.out.println(foundClient.getPersonalInfo().toString());
                if (foundClient.getAccounts() != null) {
                    System.out.println("Number of Accounts: " + foundClient.getAccounts().size());
                }
            }
        }
        
        // Calculate Total Amount
        System.out.println("\n----- Total Bank Amount -----");
        float totalAmount = myBank.totalAmount();
        System.out.println("Total amount in all accounts: " + totalAmount);
        
        // Test Remove Client
        if (myBank.getClients().size() > 2) {
            System.out.println("\n----- Testing Remove Client -----");
            Client clientToRemove = myBank.getClients().get(2);
            System.out.println("Removing client: " + clientToRemove.getId() + " - " + clientToRemove.getPersonalInfo().getName());
            myBank.removeClient(clientToRemove.getId());
            System.out.println("Client removed successfully");
            System.out.println("Total clients remaining: " + myBank.getClients().size());
            System.out.println("Total accounts remaining: " + myBank.getAccounts().size());
            System.out.println("Total amount after removal: " + myBank.totalAmount());
        }
        
        System.out.println("\n===== End of Testing =====");
    }
    
    // Load clients from file
    // File format: Name,CNIC,PhoneNumber (one client per line)
    private static void loadClientsFromFile(Bank bank, String filename) {
        System.out.println("\n----- Loading Clients from " + filename + " -----");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }
                
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    int cnic = Integer.parseInt(parts[1].trim());
                    int phone = Integer.parseInt(parts[2].trim());
                    
                    Person person = new Person(name, cnic, phone);
                    Client client = bank.addClient(person);
                    System.out.println("Loaded Client: " + client.getId() + " - " + name);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
            System.out.println("Creating sample file...");
            createSampleClientsFile(filename);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numbers in " + filename + ": " + e.getMessage());
        }
    }
    
    // Load accounts from file
    // File format: ClientCNIC,Balance (one account per line)
    private static void loadAccountsFromFile(Bank bank, String filename) {
        System.out.println("\n----- Loading Accounts from " + filename + " -----");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }
                
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int cnic = Integer.parseInt(parts[0].trim());
                    float balance = Float.parseFloat(parts[1].trim());
                    
                    Client client = bank.searchCustomerDetail(cnic);
                    if (client != null) {
                        bank.addAccount("", balance, client);
                        System.out.println("Loaded Account for " + client.getPersonalInfo().getName() + " with balance: " + balance);
                    } else {
                        System.out.println("Warning: Client with CNIC " + cnic + " not found. Skipping account.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
            System.out.println("Creating sample file...");
            createSampleAccountsFile(filename);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numbers in " + filename + ": " + e.getMessage());
        }
    }
    
    // Create sample clients file
    private static void createSampleClientsFile(String filename) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            writer.println("# Client Data: Name,CNIC,PhoneNumber");
            writer.println("Ahmed Ali,12345,300123456");
            writer.println("Sara Khan,67890,300987654");
            writer.println("Hassan Raza,11111,301111111");
            System.out.println("Sample " + filename + " created successfully.");
        } catch (IOException e) {
            System.out.println("Error creating sample file: " + e.getMessage());
        }
    }
    
    // Create sample accounts file
    private static void createSampleAccountsFile(String filename) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            writer.println("# Account Data: ClientCNIC,Balance");
            writer.println("12345,5000.0");
            writer.println("12345,10000.0");
            writer.println("67890,7500.0");
            writer.println("11111,3000.0");
            System.out.println("Sample " + filename + " created successfully.");
        } catch (IOException e) {
            System.out.println("Error creating sample file: " + e.getMessage());
        }
    }
}
