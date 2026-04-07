package project;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import project.assets.*;
import project.plugins.*;

public class GUI {

    private final Shop shop;

    private final JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea outputArea;

    public GUI(Shop shop) {
        this.shop = shop;

        frame = new JFrame("Shop");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LoginAs(); // start here
        frame.setVisible(true);
    }

    private void LoginAs() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        JButton customerBtn = new JButton("Customer");
        JButton staffBtn = new JButton("Staff");

        panel.add(customerBtn);
        panel.add(staffBtn);

        frame.add(panel, BorderLayout.CENTER);

        customerBtn.addActionListener(e -> CustomerUI());
        staffBtn.addActionListener(e -> StaffLogin());

        refreshFrame();
    }

    private void StaffLogin() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(3, 2));

        topPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        topPanel.add(usernameField);

        topPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        topPanel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        topPanel.add(loginBtn);
        topPanel.add(backBtn);

        frame.add(topPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean success = shop.login(username, password);

           if (success && shop.getLoggedInUser() !=null){
                outputArea.setText("Logged in as: " + shop.getLoggedInUser().getRole());
            } else {
                outputArea.setText("Invalid credentials");
            }

        // BACK BUTTON
        // backBtn.addActionListener(e -> LoginAs());
        
        });
    refreshFrame();
}


    // =======================
    // CUSTOMER UI
    // =======================
    private void CustomerUI() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel bottomPanel = new JPanel();

        JButton viewItemsBtn = new JButton("View Items");
        JButton orderBtn = new JButton("Create Order");
        JButton viewOrdersBtn = new JButton("View Orders");
        JButton backBtn = new JButton("Back");

        bottomPanel.add(viewItemsBtn);
        bottomPanel.add(orderBtn);
        bottomPanel.add(viewOrdersBtn);
        bottomPanel.add(backBtn);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // VIEW ITEMS
        viewItemsBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Items:\n");

            for (ShopItem item : shop.getItems()) {
                sb.append(item.getName())
                  .append(" - $")
                  .append(item.getPrice())
                  .append("\n");
            }

            outputArea.setText(sb.toString());
        });

        // CREATE ORDER
        orderBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Customer name:");
            String itemsInput = JOptionPane.showInputDialog(
                    "Enter item names (comma separated):");

            if (name == null || itemsInput == null) return;

            List<String> itemNames = Arrays.asList(itemsInput.split(","));

            Order order = shop.createOrder(name, itemNames);
            int numberOfItems = order.getItems().length; 

            outputArea.setText("Order created with " + order.getItems().length + " items:\n");
        });

        // view orders
        viewOrdersBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Orders:\n");

            for (Order o : shop.getOrders()) {
                sb.append(o.toString()).append("\n");
            }

            outputArea.setText(sb.toString());
        });

        backBtn.addActionListener(e -> LoginAs());

        refreshFrame();
    }

    // Refresh
    private void refreshFrame() {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI(new Shop());
        });
    }
}