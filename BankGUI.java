import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class BankGUI extends JFrame {
    private Bank bank;
    private JTabbedPane tabbedPane;
    private DefaultTableModel clientTableModel;
    private DefaultTableModel accountTableModel;
    private JTable clientTable;
    private JTable accountTable;
    
    // Enhanced color scheme
    private final Color PRIMARY_COLOR = new Color(67, 97, 238);
    private final Color SECONDARY_COLOR = new Color(76, 110, 245);
    private final Color SUCCESS_COLOR = new Color(52, 199, 89);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BACKGROUND_COLOR = new Color(241, 243, 247);
    private final Color PANEL_COLOR = Color.WHITE;
    private final Color TEXT_PRIMARY = new Color(44, 62, 80);
    private final Color TEXT_SECONDARY = new Color(142, 142, 147);
    private final Color ORANGE_COLOR = new Color(255, 159, 10);
    private final Color PURPLE_COLOR = new Color(175, 82, 222);
    
    public BankGUI() {
        bank = new Bank("National Bank of Pakistan", new ArrayList<>(), new ArrayList<>());
        loadDataFromFiles();
        
        setTitle("National Bank of Pakistan");
        setSize(1200, 700);
        setMinimumSize(new Dimension(900, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        createMainPanel();
        
        setVisible(true);
    }
    
    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        JPanel headerPanel = createHeaderPanel();
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        tabbedPane.addTab("   Dashboard  ", createDashboardPanel());
        tabbedPane.addTab("   Clients  ", createClientsPanel());
        tabbedPane.addTab("   Accounts  ", createAccountsPanel());
        tabbedPane.addTab("   Transactions  ", createTransactionsPanel());
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(0, 80));
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(PRIMARY_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        JLabel iconLabel = new JLabel("");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(bank.getName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("SECURE BANKING SYSTEM");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(200, 210, 255));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(3));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(2));
        contentPanel.add(subtitleLabel);
        
        headerPanel.add(contentPanel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout(15, 15));
        dashboardPanel.setBackground(BACKGROUND_COLOR);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 25, 0));
        statsPanel.setBackground(BACKGROUND_COLOR);
        statsPanel.setPreferredSize(new Dimension(0, 160));
        
        JPanel clientsCard = createStatCard("Total Clients", String.valueOf(bank.getClients().size()), 
                                            new Color(52, 152, 219));
        JPanel accountsCard = createStatCard("Total Accounts", String.valueOf(bank.getAccounts().size()), 
                                             new Color(175, 82, 222));
        JPanel totalCard = createStatCard("Total Balance", String.format("$%.2f", bank.totalAmount()), 
                                         new Color(52, 199, 89));
        
        statsPanel.add(clientsCard);
        statsPanel.add(accountsCard);
        statsPanel.add(totalCard);
        
        JPanel activityPanel = new JPanel(new BorderLayout(15, 15));
        activityPanel.setBackground(PANEL_COLOR);
        activityPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 235), 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        JLabel activityLabel = new JLabel(" Quick Actions");
        activityLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        activityLabel.setForeground(TEXT_PRIMARY);
        
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonsPanel.setBackground(PANEL_COLOR);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));
        
        JButton addClientBtn = createActionButton(" Add New Client", SUCCESS_COLOR);
        JButton addAccountBtn = createActionButton(" Create Account", SECONDARY_COLOR);
        JButton searchClientBtn = createActionButton(" Search Client", ORANGE_COLOR);
        JButton searchAccountBtn = createActionButton(" Search Account", PURPLE_COLOR);
                
        addClientBtn.addActionListener(e -> showAddClientDialog());
        addAccountBtn.addActionListener(e -> showAddAccountDialog());
        searchClientBtn.addActionListener(e -> showSearchClientDialog());
        searchAccountBtn.addActionListener(e -> showSearchAccountDialog());
        
        buttonsPanel.add(addClientBtn);
        buttonsPanel.add(addAccountBtn);
        buttonsPanel.add(searchClientBtn);
        buttonsPanel.add(searchAccountBtn);
        
        activityPanel.add(activityLabel, BorderLayout.NORTH);
        activityPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        dashboardPanel.add(statsPanel, BorderLayout.NORTH);
        dashboardPanel.add(activityPanel, BorderLayout.CENTER);
        
        return dashboardPanel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(PANEL_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 235), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(PANEL_COLOR);
        
        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(TEXT_SECONDARY);
        
        JLabel iconLabel = new JLabel("●");
        iconLabel.setFont(new Font("Arial", Font.BOLD, 20));
        iconLabel.setForeground(color);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(iconLabel, BorderLayout.EAST);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        valueLabel.setForeground(TEXT_PRIMARY);
        
        JPanel colorBar = new JPanel();
        colorBar.setBackground(color);
        colorBar.setPreferredSize(new Dimension(0, 4));
        
        card.add(topPanel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(colorBar, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 60));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 0),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private JPanel createClientsPanel() {
        JPanel clientsPanel = new JPanel(new BorderLayout(15, 15));
        clientsPanel.setBackground(BACKGROUND_COLOR);
        clientsPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        String[] columnNames = {"Client ID", "Name", "CNIC", "Phone", "Accounts", "Total Balance"};
        clientTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        clientTable = new JTable(clientTableModel);
        styleTable(clientTable);
        loadClientsTable();
        
        JScrollPane scrollPane = new JScrollPane(clientTable);
        scrollPane.setBorder(new LineBorder(new Color(220, 220, 220), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonsPanel.setBackground(BACKGROUND_COLOR);
        buttonsPanel.setPreferredSize(new Dimension(0, 60));
        
        JButton addBtn = createStyledButton("Add Client", SUCCESS_COLOR);
        JButton searchBtn = createStyledButton("Search by CNIC", new Color(243, 156, 18));
        JButton viewBtn = createStyledButton("View Details", SECONDARY_COLOR);
        JButton removeBtn = createStyledButton("Remove Client", DANGER_COLOR);
        JButton refreshBtn = createStyledButton("Refresh", new Color(149, 165, 166));
        
        addBtn.addActionListener(e -> showAddClientDialog());
        searchBtn.addActionListener(e -> showSearchClientDialog());
        viewBtn.addActionListener(e -> viewClientDetails());
        removeBtn.addActionListener(e -> removeClient());
        refreshBtn.addActionListener(e -> loadClientsTable());
        
        buttonsPanel.add(addBtn);
        buttonsPanel.add(searchBtn);
        buttonsPanel.add(viewBtn);
        buttonsPanel.add(removeBtn);
        buttonsPanel.add(refreshBtn);
        
        clientsPanel.add(scrollPane, BorderLayout.CENTER);
        clientsPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return clientsPanel;
    }
    
    private JPanel createAccountsPanel() {
        JPanel accountsPanel = new JPanel(new BorderLayout(15, 15));
        accountsPanel.setBackground(BACKGROUND_COLOR);
        accountsPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        String[] columnNames = {"Account Number", "Client ID", "Client Name", "Balance"};
        accountTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        accountTable = new JTable(accountTableModel);
        styleTable(accountTable);
        loadAccountsTable();
        
        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.setBorder(new LineBorder(new Color(220, 220, 220), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonsPanel.setBackground(BACKGROUND_COLOR);
        buttonsPanel.setPreferredSize(new Dimension(0, 60));
        
        JButton addBtn = createStyledButton("Create Account", SUCCESS_COLOR);
        JButton searchBtn = createStyledButton("Search by Account #", new Color(142, 68, 173));
        JButton viewBtn = createStyledButton("View Details", SECONDARY_COLOR);
        JButton refreshBtn = createStyledButton("Refresh", new Color(149, 165, 166));
        
        addBtn.addActionListener(e -> showAddAccountDialog());
        searchBtn.addActionListener(e -> showSearchAccountDialog());
        viewBtn.addActionListener(e -> viewAccountDetails());
        refreshBtn.addActionListener(e -> loadAccountsTable());
        
        buttonsPanel.add(addBtn);
        buttonsPanel.add(searchBtn);
        buttonsPanel.add(viewBtn);
        buttonsPanel.add(refreshBtn);
        
        accountsPanel.add(scrollPane, BorderLayout.CENTER);
        accountsPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return accountsPanel;
    }
    
    private JPanel createClientAccountsPanel() {
        JPanel accountsPanel = new JPanel(new BorderLayout(15, 15));
        accountsPanel.setBackground(BACKGROUND_COLOR);
        accountsPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        String[] columnNames = {"Account Number", "Balance", "Status"};
        DefaultTableModel clientAccountTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable clientAccountTable = new JTable(clientAccountTableModel);
        styleTable(clientAccountTable);
        
        // Load only current client's accounts
        Client currentClient = null;
        for (Client client : bank.getClients()) {
            if (client.getId().equals(currentUserId)) {
                currentClient = client;
                break;
            }
        }
        
        if (currentClient != null && currentClient.getAccounts() != null) {
            for (Account account : currentClient.getAccounts()) {
                Object[] row = {
                    account.getAccountNumber(),
                    String.format("$%.2f", account.getBalance()),
                    "Active"
                };
                clientAccountTableModel.addRow(row);
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(clientAccountTable);
        scrollPane.setBorder(new LineBorder(new Color(220, 220, 220), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(PANEL_COLOR);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 235), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        infoPanel.setPreferredSize(new Dimension(0, 80));
        
        if (currentClient != null) {
            JLabel infoLabel = new JLabel(String.format(
                "<html><b>Client Information:</b> %s | <b>Total Balance:</b> $%.2f</html>",
                currentClient.getPersonalInfo().getName(),
                currentClient.totalAmount()
            ));
            infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            infoLabel.setForeground(TEXT_PRIMARY);
            infoPanel.add(infoLabel, BorderLayout.WEST);
        }
        
        accountsPanel.add(infoPanel, BorderLayout.NORTH);
        accountsPanel.add(scrollPane, BorderLayout.CENTER);
        
        return accountsPanel;
    }
    
    private JPanel createTransactionsPanel() {
        JPanel transactionsPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        transactionsPanel.setBackground(BACKGROUND_COLOR);
        transactionsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel depositPanel = createTransactionPanel("Deposit", SUCCESS_COLOR, true);
        JPanel withdrawPanel = createTransactionPanel("Withdraw", DANGER_COLOR, false);
        
        transactionsPanel.add(depositPanel);
        transactionsPanel.add(withdrawPanel);
        
        return transactionsPanel;
    }
    
    private JPanel createTransactionPanel(String title, Color color, boolean isDeposit) {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(PANEL_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 235), 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        // Header with icon and title
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        headerPanel.setBackground(PANEL_COLOR);
        
        String icon = isDeposit ? "↓" : "↑";
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        iconLabel.setForeground(color);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(color);
        
        headerPanel.add(iconLabel);
        headerPanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(PANEL_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        // Account Number field
        JLabel accountLabel = new JLabel("Account Number");
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        accountLabel.setForeground(TEXT_SECONDARY);
        accountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField accountField = new JTextField();
        accountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        accountField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        accountField.setForeground(TEXT_SECONDARY);
        accountField.setText("Enter account number");
        accountField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        accountField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        accountField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (accountField.getText().equals(" Enter account number")) {
                    accountField.setText("");
                    accountField.setForeground(TEXT_PRIMARY);
                }
            }
            public void focusLost(FocusEvent e) {
                if (accountField.getText().isEmpty()) {
                    accountField.setText(" Enter account number");
                    accountField.setForeground(TEXT_SECONDARY);
                }
            }
        });

        // Amount field
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        amountLabel.setForeground(TEXT_SECONDARY);
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField amountField = new JTextField();
        amountField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        amountField.setForeground(TEXT_SECONDARY);
        amountField.setText("$ 0.00");
        amountField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        amountField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        amountField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (amountField.getText().equals("$ 0.00")) {
                    amountField.setText("");
                    amountField.setForeground(TEXT_PRIMARY);
                }
            }
            public void focusLost(FocusEvent e) {
                if (amountField.getText().isEmpty()) {
                    amountField.setText("$ 0.00");
                    amountField.setForeground(TEXT_SECONDARY);
                }
            }
        });
        
        formPanel.add(accountLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(accountField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(amountLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(amountField);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBackground(PANEL_COLOR);
        
        JButton submitBtn = new JButton("⊕ " + title);
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitBtn.setBackground(color);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setBorderPainted(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.setPreferredSize(new Dimension(130, 42));
        
        submitBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                submitBtn.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent e) {
                submitBtn.setBackground(color);
            }
        });
        
        submitBtn.addActionListener(e -> {
            String accountNum = accountField.getText().trim();
            String amountStr = amountField.getText().trim();
            
            if (accountNum.isEmpty() || accountNum.equals(" Enter account number")) {
                showMessage("Please enter account number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (amountStr.isEmpty() || amountStr.equals("$ 0.00")) {
                showMessage("Please enter amount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                // Remove $ and parse
                amountStr = amountStr.replace("$", "").replace(",", "").trim();
                float amount = Float.parseFloat(amountStr);
                
                if (amount <= 0) {
                    showMessage("Amount must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Account account = bank.searchAccount(accountNum);
                
                if (account == null) {
                    showMessage("Account not found", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // If client, verify they own this account
                if (currentUserRole.equals("client")) {
                    if (!account.getAccountHolder().getId().equals(currentUserId)) {
                        showMessage("You can only perform transactions on your own accounts!", 
                                  "Access Denied", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                if (isDeposit) {
                    account.deposit(amount);
                    bank.saveAccountsToFile("accounts.txt");
                    showMessage(String.format("Successfully deposited $%.2f", amount), 
                              "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (amount <= account.getBalance()) {
                        account.withdraw(amount);
                        bank.saveAccountsToFile("accounts.txt");
                        showMessage(String.format("Successfully withdrew $%.2f", amount), 
                                  "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        showMessage("Insufficient balance", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                accountField.setText(" Enter account number");
                accountField.setForeground(TEXT_SECONDARY);
                amountField.setText("$ 0.00");
                amountField.setForeground(TEXT_SECONDARY);
                refreshData();
                
            } catch (NumberFormatException ex) {
                showMessage("Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(submitBtn);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setRowHeight(45);
        table.setSelectionBackground(SECONDARY_COLOR);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        // Configure header
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.getTableHeader().setReorderingAllowed(false);
        
        // Create custom header renderer to ensure visibility
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setOpaque(true);
        headerRenderer.setBackground(PRIMARY_COLOR);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 15));
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        // Apply header renderer to all columns
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        // Configure cell renderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 2, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 42));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void loadDataFromFiles() {
        loadClientsFromFile("clients.txt");
        loadAccountsFromFile("accounts.txt");
    }
    
    private void loadClientsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    try {
                        Person p = new Person(parts[0].trim(), 
                                            Integer.parseInt(parts[1].trim()), 
                                            Integer.parseInt(parts[2].trim()));
                        bank.addClient(p);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid data format in line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading clients: " + e.getMessage());
        }
    }
    
    private void loadAccountsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    try {
                        int clientCNIC = Integer.parseInt(parts[0].trim());
                        float balance = Float.parseFloat(parts[1].trim());
                        
                        Client client = bank.searchCustomerDetail(clientCNIC);
                        
                        if (client != null) {
                            bank.addAccount("", balance, client);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid data format in line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
    }
    
    private void loadClientsTable() {
        clientTableModel.setRowCount(0);
        for (Client client : bank.getClients()) {
            float totalBalance = 0;
            int accountCount = 0;
            if (client.getAccounts() != null) {
                accountCount = client.getAccounts().size();
                for (Account acc : client.getAccounts()) {
                    totalBalance += acc.getBalance();
                }
            }
            
            Object[] row = {
                client.getId(),
                client.getPersonalInfo().getName(),
                client.getPersonalInfo().getCNIC(),
                client.getPersonalInfo().getPhoneNumber(),
                accountCount,
                String.format("$%.2f", totalBalance)
            };
            clientTableModel.addRow(row);
        }
    }
    
    private void loadAccountsTable() {
        accountTableModel.setRowCount(0);
        for (Account account : bank.getAccounts()) {
            Object[] row = {
                account.getAccountNumber(),
                account.getAccountHolder().getId(),
                account.getAccountHolder().getPersonalInfo().getName(),
                String.format("$%.2f", account.getBalance())
            };
            accountTableModel.addRow(row);
        }
    }
    
    private void showAddClientDialog() {
        JDialog dialog = new JDialog(this, "Add New Client", true);
        dialog.setSize(550, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(15, 15));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        formPanel.setBackground(PANEL_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(300, 40));
        styleTextField(nameField);
        
        JLabel cnicLabel = new JLabel("CNIC:");
        cnicLabel.setFont(new Font("Arial", Font.BOLD, 15));
        JTextField cnicField = new JTextField();
        cnicField.setPreferredSize(new Dimension(300, 40));
        styleTextField(cnicField);
        
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 15));
        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(300, 40));
        styleTextField(phoneField);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(cnicLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(cnicField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(phoneField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(PANEL_COLOR);
        
        JButton saveBtn = createStyledButton("Save", SUCCESS_COLOR);
        JButton cancelBtn = createStyledButton("Cancel", new Color(149, 165, 166));
        
        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String cnic = cnicField.getText().trim();
            String phone = phoneField.getText().trim();
            
            if (name.isEmpty() || cnic.isEmpty() || phone.isEmpty()) {
                showMessage("Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                Person p = new Person(name, Integer.parseInt(cnic), Integer.parseInt(phone));
                bank.addClient(p);
                loadClientsTable();
                refreshData();
                dialog.dispose();
                showMessage("Client added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                showMessage("Invalid CNIC or Phone number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    private void showAddAccountDialog() {
        if (bank.getClients().isEmpty()) {
            showMessage("Please add clients first", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JDialog dialog = new JDialog(this, "Create New Account", true);
        dialog.setSize(550, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(15, 15));
        
        JPanel formPanel = new JPanel();
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        formPanel.setBackground(PANEL_COLOR);
        
        JLabel clientLabel = new JLabel("Client ID:");
        clientLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        String[] clientIds = bank.getClients().stream()
            .map(Client::getId)
            .toArray(String[]::new);
        JComboBox<String> clientCombo = new JComboBox<>(clientIds);
        clientCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel balanceLabel = new JLabel("Initial Balance:");
        balanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField balanceField = new JTextField();
        styleTextField(balanceField);
        
        JPanel clientPanel = new JPanel(new BorderLayout(10, 0));
        clientPanel.setBackground(PANEL_COLOR);
        clientPanel.add(clientLabel, BorderLayout.WEST);
        clientPanel.add(clientCombo, BorderLayout.CENTER);
        
        JPanel balancePanel = new JPanel(new BorderLayout(10, 0));
        balancePanel.setBackground(PANEL_COLOR);
        balancePanel.add(balanceLabel, BorderLayout.WEST);
        balancePanel.add(balanceField, BorderLayout.CENTER);
        
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.add(clientPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(balancePanel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(PANEL_COLOR);
        
        JButton saveBtn = createStyledButton("Create", SUCCESS_COLOR);
        JButton cancelBtn = createStyledButton("Cancel", new Color(149, 165, 166));
        
        saveBtn.addActionListener(e -> {
            String clientId = (String) clientCombo.getSelectedItem();
            String balanceStr = balanceField.getText().trim();
            
            if (balanceStr.isEmpty()) {
                showMessage("Please enter initial balance", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                float balance = Float.parseFloat(balanceStr);
                Client client = null;
                for (Client c : bank.getClients()) {
                    if (c.getId().equals(clientId)) {
                        client = c;
                        break;
                    }
                }
                
                if (client != null) {
                    bank.addAccount("", balance, client);
                    loadAccountsTable();
                    refreshData();
                    dialog.dispose();
                    showMessage("Account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                showMessage("Invalid balance amount", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    private void showSearchClientDialog() {
        String cnicStr = JOptionPane.showInputDialog(this, "Enter Client CNIC:", 
                                                      "Search Client", JOptionPane.QUESTION_MESSAGE);
        if (cnicStr != null && !cnicStr.trim().isEmpty()) {
            try {
                int cnic = Integer.parseInt(cnicStr.trim());
                Client client = bank.searchCustomerDetail(cnic);
                
                if (client != null) {
                    showClientDetailsDialog(client);
                } else {
                    showMessage("Client not found", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                showMessage("Invalid CNIC", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showSearchAccountDialog() {
        String accountNum = JOptionPane.showInputDialog(this, "Enter Account Number:", 
                                                         "Search Account", JOptionPane.QUESTION_MESSAGE);
        if (accountNum != null && !accountNum.trim().isEmpty()) {
            Account account = bank.searchAccount(accountNum.trim());
            
            if (account != null) {
                showAccountDetailsDialog(account);
            } else {
                showMessage("Account not found", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void viewClientDetails() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select a client", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String clientId = (String) clientTableModel.getValueAt(selectedRow, 0);
        Client client = null;
        for (Client c : bank.getClients()) {
            if (c.getId().equals(clientId)) {
                client = c;
                break;
            }
        }
        
        if (client != null) {
            showClientDetailsDialog(client);
        }
    }
    
    private void showClientDetailsDialog(Client client) {
        JDialog dialog = new JDialog(this, "Client Details", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        infoPanel.setBackground(PANEL_COLOR);
        
        addInfoRow(infoPanel, "Client ID:", client.getId());
        addInfoRow(infoPanel, "Name:", client.getPersonalInfo().getName());
        addInfoRow(infoPanel, "CNIC:", String.valueOf(client.getPersonalInfo().getCNIC()));
        addInfoRow(infoPanel, "Phone:", String.valueOf(client.getPersonalInfo().getPhoneNumber()));
        
        float totalBalance = 0;
        if (client.getAccounts() != null) {
            for (Account acc : client.getAccounts()) {
                totalBalance += acc.getBalance();
            }
        }
        addInfoRow(infoPanel, "Total Balance:", String.format("$%.2f", totalBalance));
        
        // Accounts list
        JPanel accountsPanel = new JPanel(new BorderLayout(5, 5));
        accountsPanel.setBackground(PANEL_COLOR);
        accountsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        JLabel accountsLabel = new JLabel("Accounts:");
        accountsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        if (client.getAccounts() != null) {
            for (Account acc : client.getAccounts()) {
                listModel.addElement(String.format("%s - $%.2f", 
                                                   acc.getAccountNumber(), 
                                                   acc.getBalance()));
            }
        }
        
        JList<String> accountsList = new JList<>(listModel);
        accountsList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(accountsList);
        
        accountsPanel.add(accountsLabel, BorderLayout.NORTH);
        accountsPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton closeBtn = createStyledButton("Close", new Color(149, 165, 166));
        closeBtn.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(PANEL_COLOR);
        buttonPanel.add(closeBtn);
        
        dialog.add(infoPanel, BorderLayout.NORTH);
        dialog.add(accountsPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    private void viewAccountDetails() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select an account", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String accountNum = (String) accountTableModel.getValueAt(selectedRow, 0);
        Account account = bank.searchAccount(accountNum);
        
        if (account != null) {
            showAccountDetailsDialog(account);
        }
    }
    
    private void showAccountDetailsDialog(Account account) {
        JDialog dialog = new JDialog(this, "Account Details", true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        infoPanel.setBackground(PANEL_COLOR);
        
        addInfoRow(infoPanel, "Account Number:", account.getAccountNumber());
        addInfoRow(infoPanel, "Balance:", String.format("$%.2f", account.getBalance()));
        addInfoRow(infoPanel, "Client ID:", account.getAccountHolder().getId());
        addInfoRow(infoPanel, "Client Name:", account.getAccountHolder().getPersonalInfo().getName());
        addInfoRow(infoPanel, "CNIC:", String.valueOf(account.getAccountHolder().getPersonalInfo().getCNIC()));
        
        JButton closeBtn = createStyledButton("Close", new Color(149, 165, 166));
        closeBtn.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(PANEL_COLOR);
        buttonPanel.add(closeBtn);
        
        dialog.add(infoPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    private void addInfoRow(JPanel panel, String label, String value) {
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        panel.add(labelComp);
        panel.add(valueComp);
    }
    
    private void removeClient() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select a client to remove", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String clientId = (String) clientTableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to remove this client and all associated accounts?", 
            "Confirm Removal", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            bank.removeClient(clientId);
            loadClientsTable();
            loadAccountsTable();
            refreshData();
            showMessage("Client removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void refreshData() {
        loadClientsTable();
        loadAccountsTable();
        
        // Update dashboard stats
        Component dashboard = tabbedPane.getComponentAt(0);
        if (dashboard instanceof JPanel) {
            updateDashboardStats((JPanel) dashboard);
        }
    }
    
    private void updateDashboardStats(JPanel dashboardPanel) {
        Component[] components = dashboardPanel.getComponents();
        if (components.length > 0 && components[0] instanceof JPanel) {
            JPanel statsPanel = (JPanel) components[0];
            Component[] cards = statsPanel.getComponents();
            
            if (cards.length >= 3) {
                updateStatCard((JPanel) cards[0], String.valueOf(bank.getClients().size()));
                updateStatCard((JPanel) cards[1], String.valueOf(bank.getAccounts().size()));
                updateStatCard((JPanel) cards[2], String.format("$%.2f", bank.totalAmount()));
            }
        }
    }
    
    private void updateStatCard(JPanel card, String newValue) {
        Component[] components = card.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getFont().getSize() == 42) {
                    label.setText(newValue);
                    break;
                }
            }
        }
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, 
            "Bank Management System\nVersion 1.0\n\n" +
            "A professional banking application\nbuilt with Java Swing\n\n" +
            "© 2026 All Rights Reserved", 
            "About", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new BankGUI());
    }
}