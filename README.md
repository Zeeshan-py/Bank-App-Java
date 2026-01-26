# 🏦 Bank Management System

A comprehensive Java-based Bank Management System with both console and GUI interfaces. This system demonstrates Object-Oriented Programming principles including classes, inheritance, encapsulation, and file I/O operations.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Class Structure](#class-structure)
- [Installation](#installation)
- [Usage](#usage)
- [File Structure](#file-structure)
- [Data Files](#data-files)
- [Technical Details](#technical-details)
- [Screenshots](#screenshots)
- [Future Enhancements](#future-enhancements)

---

## 🎯 Overview

The **Bank Management System** is a Java application designed to manage banking operations including client management, account management, deposits, withdrawals, and balance inquiries. The system provides two interfaces:

1. **Console Interface** (`Main.java`) - Command-line based testing and operations
2. **GUI Interface** (`BankGUI.java`) - Full-featured graphical user interface built with Java Swing

This project demonstrates professional software engineering practices and is ideal for learning Object-Oriented Programming concepts in Java.

---

## ✨ Features

### Core Banking Operations

- **Client Management**
  - Add new clients with personal information (Name, CNIC, Phone Number)
  - Search clients by CNIC number
  - Remove clients (automatically removes associated accounts)
  - View all client details
  - Persistent storage in text files

- **Account Management**
  - Create new accounts linked to clients
  - Automatic account number generation (ACC-XXX format)
  - Multiple accounts per client support
  - Search accounts by account number
  - View account balances and details

- **Transaction Operations**
  - Deposit money into accounts
  - Withdraw money with balance validation
  - Invalid transaction handling
  - Real-time balance updates
  - Transaction history in GUI

- **Reporting & Analytics**
  - Calculate total bank assets
  - View client-wise total balances
  - Dashboard with statistics (GUI)
  - Account and client listings

### GUI Features

- **Modern Dashboard**
  - Total clients and accounts count
  - Total bank assets display
  - Visual statistics with color-coded cards
  - Recent activity summary

- **Client Management Panel**
  - Interactive client table
  - Add, edit, and delete operations
  - Search and filter functionality
  - Detailed client information view

- **Account Management Panel**
  - Account listing with holder details
  - Create new accounts
  - View balances and account details
  - Linked to client information

- **Transaction Panel**
  - Deposit interface
  - Withdrawal interface
  - Transaction validation
  - Account search functionality

- **Professional UI/UX**
  - Modern color scheme
  - Responsive design
  - User-friendly forms
  - Error handling with dialogs

---

## 🏗️ System Architecture

The system follows a layered architecture with clear separation of concerns:

```
┌─────────────────────────────────────┐
│     Presentation Layer              │
│  (Main.java / BankGUI.java)         │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│     Business Logic Layer            │
│  (Bank.java, Client.java)           │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│     Data Layer                      │
│  (Account.java, Person.java)        │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│     Persistence Layer               │
│  (clients.txt, accounts.txt)        │
└─────────────────────────────────────┘
```

---

## 📦 Class Structure

### 1. **Person Class** (`Person.java`)

Represents basic personal information.

**Attributes:**
- `String name` - Person's full name
- `int CNIC` - National ID number
- `int phoneNumber` - Contact number

**Methods:**
- Getters and Setters for all attributes
- `toString()` - Returns formatted personal information

### 2. **Account Class** (`Account.java`)

Manages individual bank accounts.

**Attributes:**
- `String accountNumber` - Unique account identifier (auto-generated)
- `float balance` - Current account balance
- `Client accountHolder` - Reference to the account owner
- `static int accountCount` - Tracks total accounts created

**Key Methods:**
- `deposit(float amount)` - Adds money to the account
- `withdraw(float amount)` - Withdraws money with validation
- `getBalance()` - Returns current balance
- `toString()` - Returns formatted account details

**Business Rules:**
- Deposit amount must be positive
- Withdrawal amount must not exceed balance
- Account number format: "ACC-XXX" (auto-incremented)

### 3. **Client Class** (`Client.java`)

Represents a bank client/customer.

**Attributes:**
- `String id` - Unique client identifier (auto-generated)
- `Person personalInfo` - Client's personal information
- `List<Account> accounts` - List of accounts owned by the client
- `static int count` - Tracks total clients created

**Key Methods:**
- `addAccount(Account account)` - Links an account to the client
- `totalAmount()` - Calculates total balance across all accounts
- `withdraw(String accountNumber, float amount)` - Withdraws from specific account
- `deposit(String accountNumber, float amount)` - Deposits to specific account
- `toString()` - Returns formatted client details with accounts

**Features:**
- Client ID format: "CL-XXX" (auto-incremented)
- Supports multiple accounts per client
- Aggregates balance across all accounts

### 4. **Bank Class** (`Bank.java`)

Central business logic controller for the banking system.

**Attributes:**
- `String name` - Bank name
- `List<Client> clients` - All clients in the bank
- `List<Account> accounts` - All accounts in the bank

**Core Methods:**

| Method | Description | Return Type |
|--------|-------------|-------------|
| `addClient(Person p)` | Creates a new client and saves to file | Client |
| `addAccount(String id, float amount, Client c)` | Creates account for client | Account |
| `searchAccount(String id)` | Finds account by account number | Account |
| `removeClient(String id)` | Removes client and all their accounts | Boolean |
| `totalAmount()` | Calculates total bank assets | float |
| `searchCustomerDetail(int CNIC)` | Finds client by CNIC | Client |
| `saveClientsToFile(String filename)` | Persists client data | void |
| `saveAccountsToFile(String filename)` | Persists account data | void |

**Key Features:**
- Automatic data persistence
- Cascading delete (removing client removes all accounts)
- Search functionality by multiple criteria
- Data validation and error handling

### 5. **Main Class** (`Main.java`)

Console-based application entry point.

**Functionality:**
- Loads data from text files on startup
- Demonstrates all banking operations
- Tests deposit, withdrawal, search operations
- Displays formatted reports
- Handles file I/O errors gracefully

**Workflow:**
1. Initialize bank instance
2. Load clients from `clients.txt`
3. Load accounts from `accounts.txt`
4. Display all client details
5. Perform test operations
6. Generate reports

### 6. **BankGUI Class** (`BankGUI.java`)

Complete GUI implementation using Java Swing.

**Components:**

- **Header Panel**
  - Bank name display
  - Professional branding
  - Color-coded design

- **Dashboard Tab**
  - Statistics cards
  - Total clients count
  - Total accounts count
  - Total assets display
  - Visual indicators

- **Clients Tab**
  - JTable with client data
  - Add client form
  - Edit client functionality
  - Delete client with confirmation
  - Search and filter

- **Accounts Tab**
  - JTable with account data
  - Create account form
  - Link to client selection
  - Balance display
  - Account details

- **Transactions Tab**
  - Deposit interface
  - Withdrawal interface
  - Account search
  - Balance inquiry
  - Transaction validation

**UI Features:**
- Modern color scheme (Blue primary, Green for success, Red for errors)
- Responsive layout with BorderLayout and BoxLayout
- Custom styled buttons and panels
- Input validation with error dialogs
- Confirmation dialogs for destructive operations
- Tabbed interface for easy navigation

---

## 🚀 Installation

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java Runtime Environment (JRE)
- Text editor or IDE (Eclipse, IntelliJ IDEA, VS Code)

### Setup Steps

1. **Clone or Download the Project**
   ```bash
   cd "d:\SEMESTER 3\OOPS Lab\Practice\Lab-07"
   ```

2. **Verify File Structure**
   Ensure you have all required files:
   - `Person.java`
   - `Account.java`
   - `Client.java`
   - `Bank.java`
   - `Main.java`
   - `BankGUI.java`
   - `clients.txt`
   - `accounts.txt`

3. **Compile the Project**
   
   **Option A: Compile All Files**
   ```bash
   javac *.java
   ```
   
   **Option B: Compile with Output Directory**
   ```bash
   javac -d bin *.java
   ```

4. **Verify Compilation**
   Check for `.class` files in the current directory or `bin` folder.

---

## 💻 Usage

### Running the Console Application

```bash
java Main
```

**Expected Output:**
```
===== Bank Management System =====

Bank Created: National Bank

----- Loading Clients from clients.txt -----
Loaded Client: CL-1 - Ahmed Ali
Loaded Client: CL-2 - Fatima Khan
Loaded Client: CL-3 - Hassan Raza

----- Data Loaded from Files -----
Total Clients: 3
Total Accounts: 4

----- All Client Details -----
[Client details with accounts...]

----- Testing Deposit -----
[Deposit transaction details...]

----- Testing Withdraw -----
[Withdrawal transaction details...]

----- Total Bank Amount -----
Total amount in all accounts: 145000.0

===== End of Testing =====
```

### Running the GUI Application

```bash
java BankGUI
```

**GUI Usage:**

1. **Dashboard Tab**
   - View overall statistics
   - Monitor total clients, accounts, and assets

2. **Clients Tab**
   - Click "Add Client" to create new client
   - Fill in Name, CNIC, and Phone Number
   - Select a client row and click "Delete" to remove
   - Double-click to view details

3. **Accounts Tab**
   - Click "Create Account" to add new account
   - Select client from dropdown
   - Enter initial deposit amount
   - View all accounts with holder information

4. **Transactions Tab**
   - **Deposit:**
     - Enter account number
     - Enter deposit amount
     - Click "Deposit" button
   - **Withdraw:**
     - Enter account number
     - Enter withdrawal amount
     - Click "Withdraw" button

---

## 📁 File Structure

```
Lab-07/
│
├── Person.java                # Personal information class
├── Account.java               # Bank account management
├── Client.java               # Client/customer management
├── Bank.java                 # Core banking operations
├── Main.java                 # Console application
├── BankGUI.java              # GUI application
│
├── clients.txt               # Client data storage
├── accounts.txt              # Account data storage
│
├── bin/                      # Compiled .class files
│   ├── Person.class
│   ├── Account.class
│   ├── Client.class
│   ├── Bank.class
│   ├── Main.class
│   └── BankGUI.class
│
└── README.md                 # This file
```

---

## 📄 Data Files

### clients.txt Format

```
# Client Data Format: Name, CNIC, Phone
Ahmed Ali,12345,3001234567
Fatima Khan,67890,3009876543
Hassan Raza,11223,3007654321
```

**Format Specification:**
- Lines starting with `#` are comments (ignored)
- Each line represents one client
- Format: `Name,CNIC,PhoneNumber`
- Comma-separated values
- No spaces around values (automatically trimmed)

### accounts.txt Format

```
# Account Data Format: Client_CNIC, Balance
12345,50000.0
12345,25000.0
67890,40000.0
11223,30000.0
```

**Format Specification:**
- Lines starting with `#` are comments (ignored)
- Each line represents one account
- Format: `ClientCNIC,InitialBalance`
- CNIC must match an existing client
- Balance should be a valid float number

### Data Persistence

- **Automatic Saving:** All modifications (add/remove client, add account) are automatically saved to files
- **Loading on Startup:** Both applications load data from files when starting
- **Error Handling:** Missing files are handled gracefully with appropriate messages
- **Sample Data Creation:** If files are missing, sample data can be created

---

## 🔧 Technical Details

### Design Patterns Used

1. **Singleton-like Pattern**
   - Bank class manages single bank instance per application

2. **Factory Pattern**
   - Bank class acts as factory for creating Clients and Accounts

3. **Data Access Object (DAO)**
   - File I/O operations separated in Bank class

### OOP Principles Demonstrated

1. **Encapsulation**
   - Private attributes with public getters/setters
   - Data hiding and access control

2. **Inheritance**
   - Person class serves as base for client information
   - Potential for extending to different account types

3. **Abstraction**
   - High-level banking operations abstract low-level details
   - Clear interface for banking operations

4. **Composition**
   - Client HAS-A Person
   - Client HAS-MANY Accounts
   - Account HAS-A Client reference

### Key Technologies

- **Java SE** - Core programming language
- **Java Swing** - GUI framework
- **Java AWT** - Graphics and event handling
- **Java I/O** - File operations
- **Java Collections** - ArrayList for data management

### Error Handling

- **Input Validation**
  - Checks for valid amounts (positive numbers)
  - CNIC and phone number format validation
  - Account number existence verification

- **Transaction Validation**
  - Insufficient balance check for withdrawals
  - Negative amount prevention
  - Account existence verification

- **File I/O Error Handling**
  - IOException catching and logging
  - NumberFormatException for parsing errors
  - Graceful fallback for missing files

- **GUI Error Handling**
  - Dialog boxes for error messages
  - Confirmation dialogs for critical operations
  - Input validation before processing

---

## 🎨 Screenshots

### Console Application

```
===== Bank Management System =====

Bank Created: National Bank

----- All Client Details -----

Client ID: CL-1
Name: Ahmed Ali
CNIC: 12345
Phone Number: 3001234567
Accounts:
  - Account Number: ACC-1, Balance: 50000.0
  - Account Number: ACC-2, Balance: 25000.0

----- Testing Deposit -----
Account ACC-1 balance before deposit: 50000.0
Deposited: 2000.0
Account ACC-1 balance after deposit: 52000.0

----- Total Bank Amount -----
Total amount in all accounts: 147000.0
```

### GUI Application

The GUI features:
- **Blue-themed professional design**
- **Four main tabs:** Dashboard, Clients, Accounts, Transactions
- **Modern cards with statistics**
- **Interactive tables with data**
- **User-friendly forms with validation**
- **Color-coded buttons** (Blue for primary, Green for success, Red for danger)

---

## 🔮 Future Enhancements

### Planned Features

1. **Authentication System**
   - User login (customers and staff)
   - Role-based access control
   - Password encryption

2. **Advanced Transaction Features**
   - Fund transfers between accounts
   - Transaction history with timestamps
   - Transaction receipts generation
   - Recurring payments

3. **Database Integration**
   - Replace text files with SQL database
   - Better data integrity
   - Faster search operations
   - Transaction support (ACID properties)

4. **Enhanced Security**
   - PIN/Password for accounts
   - Session management
   - Audit trails
   - Encryption for sensitive data

5. **Reporting Features**
   - Monthly statements
   - Transaction reports
   - PDF export functionality
   - Graphical charts and analytics

6. **Additional Account Types**
   - Savings accounts with interest
   - Current accounts
   - Fixed deposits
   - Loan accounts

7. **Improved GUI Features**
   - Dark mode support
   - Print functionality
   - Export to Excel/CSV
   - Email notifications

8. **Mobile Application**
   - Android/iOS companion app
   - QR code transactions
   - Mobile banking features

### Bug Fixes and Improvements

- [ ] Handle CNIC as Long instead of Int (supports larger numbers)
- [ ] Add date/time stamps to transactions
- [ ] Implement data validation for phone numbers (format checking)
- [ ] Add pagination for large data sets
- [ ] Implement undo/redo functionality
- [ ] Add backup and restore features
- [ ] Improve exception handling with custom exceptions

---

## 👨‍💻 Development Information

**Course:** Object-Oriented Programming Lab  
**Semester:** 3  
**Lab:** 07  
**Language:** Java  
**IDE Support:** Eclipse, IntelliJ IDEA, VS Code, NetBeans  

---

## 📝 Code Examples

### Creating a New Client

```java
// Create a Person object
Person person = new Person("Ahmed Ali", 12345, 3001234567);

// Add client to bank
Bank bank = new Bank("National Bank", new ArrayList<>(), new ArrayList<>());
Client newClient = bank.addClient(person);

System.out.println("Client created: " + newClient.getId());
```

### Creating an Account

```java
// Assuming we have a client
Client client = bank.getClients().get(0);

// Create account with initial deposit
Account newAccount = bank.addAccount("", 10000.0f, client);

System.out.println("Account Number: " + newAccount.getAccountNumber());
System.out.println("Balance: " + newAccount.getBalance());
```

### Performing Transactions

```java
// Deposit
Account account = bank.searchAccount("ACC-1");
account.deposit(5000.0f);
System.out.println("New Balance: " + account.getBalance());

// Withdraw
float newBalance = account.withdraw(2000.0f);
System.out.println("Balance after withdrawal: " + newBalance);
```

### Searching for Clients

```java
// Search by CNIC
Client foundClient = bank.searchCustomerDetail(12345);
if (foundClient != null) {
    System.out.println("Client Found: " + foundClient.getPersonalInfo().getName());
    System.out.println("Total Balance: " + foundClient.totalAmount());
}
```

---

## 🤝 Contributing

This is an academic project, but suggestions and improvements are welcome!

### How to Contribute

1. Fork the project
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request with detailed description

---

## ⚖️ License

This project is created for educational purposes as part of university coursework.

---

## 📧 Support

For questions, issues, or suggestions:
- Review the code comments for implementation details
- Check the console output for debugging information
- Verify data file formats match specifications
- Ensure Java version compatibility

---

## 🙏 Acknowledgments

- **Java Documentation** - Official Java API references
- **Swing Tutorial** - Java GUI development
- **Object-Oriented Programming Principles** - Course materials

---

## 📚 Learning Outcomes

By studying this project, you will learn:

✅ Object-Oriented Programming concepts in Java  
✅ Class design and relationships  
✅ File I/O operations  
✅ Java Swing GUI development  
✅ Event handling in Java  
✅ Data persistence techniques  
✅ Error handling and validation  
✅ Software design patterns  
✅ Code organization and structure  
✅ Professional software documentation  

---

**Last Updated:** January 26, 2026  
**Version:** 1.0  
**Status:** Active Development

---

<div align="center">

### ⭐ If you found this project helpful, please consider starring it! ⭐

**Built with ❤️ using Java**

</div>
