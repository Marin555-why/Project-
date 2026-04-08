package project;
import java.awt.*;
import javax.swing.*;
import project.assets.*;
import project.plugins.*;
import project.user.CashierStaff;
import project.user.Customer;
import project.user.EmployeeStaff;
import project.user.Staff;

public class GUI {

    private final Shop shop;

    private final JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextArea outputArea;
    private Order currentOrder;
    private JPanel catalogPanel;

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
                if (success){
                     Staff loggedIn = shop.getLoggedInUser();
                    outputArea.setText("Logged in as: " + shop.getLoggedInUser().getRole());
                    showStaffUI(loggedIn);
                } else {
                    outputArea.setText("Invalid credentials");
                }
            
        });
                
        backBtn.addActionListener(e -> LoginAs());
        
        
    refreshFrame();
}
private void showStaffUI(Staff staff) {
    frame.getContentPane().removeAll();
    frame.setLayout(new BorderLayout());
    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();

    JButton manageOrdersBtn = new JButton("Manage Orders");
    manageOrdersBtn.addActionListener(e ->{
        if(!staff.can(Shop.MANAGE_ORDERS)){
            outputArea.setText("Access Denied");
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (shop.getOrders().isEmpty()){
            sb.append("No orders.");
        } else {
            for (Order o : shop.getOrders()) sb.append(o.toString()).append("\n");
        }
        outputArea.setText(sb.toString());
    });
    buttonPanel.add(manageOrdersBtn);

    JButton createCustomerBtn = new JButton("Create Customer");
    createCustomerBtn.addActionListener(e->{
        if(!staff.can(shop.CREATE_CUSTOMER)){
            outputArea.setText("Access Denied");
            return;
        }
        String name = JOptionPane.showInputDialog(frame, "Customer Name: ");
        if (name == null || name.isBlank()) return;
            shop.addCustomer(new Customer(name));
            outputArea.setText("Customer created: " + name);
        });
    buttonPanel.add(createCustomerBtn);
    
    JButton createOrderBtn = new JButton("Create Order");
    createOrderBtn.addActionListener(e -> {
        if(!staff.can(Shop.CREATE_ORDER)){
            outputArea.setText("Access Denied");
            return;
        }
        CustomerUI();
    });
    buttonPanel.add(createOrderBtn);

    JButton createEmployeeBtn = new JButton("Create Employee");
    createEmployeeBtn.addActionListener(e->{
        if(!staff.can(shop.CREATE_EMPLOYEE)){
            outputArea.setText("Access Denied");
            return;
        }
        String fullname = JOptionPane.showInputDialog(frame, "Full Name: ");
        String username = JOptionPane.showInputDialog(frame, "Username: ");
        String password = JOptionPane.showInputDialog(frame, "Password: ");
        String role = JOptionPane.showInputDialog(frame, "Role (Cashier/Employee): ");
        if (fullname == null || username == null || password == null || role == null ) return;

        Staff s;
        if (role.equalsIgnoreCase("Cashier")) {
            s = new CashierStaff(username, "N/A", "N/A", fullname, password, "Cashier");
        } else {
            s = new EmployeeStaff(username, "N/A", "N/A", fullname, password, "Employee");
        }
        shop.addStaff(s);
        outputArea.setText("Staff created: " + fullname + " (" + role + ")");
    });
    JButton backBtn = new JButton("Back");
    backBtn.addActionListener(e -> StaffLogin());
    buttonPanel.add(createEmployeeBtn);

     frame.add(buttonPanel, BorderLayout.NORTH);
    frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel();
    bottomPanel.add(backBtn);
    frame.add(bottomPanel, BorderLayout.SOUTH);

    refreshFrame();
}

    // =======================
    // CUSTOMER UI
    // =======================
   private void CustomerUI() {
    frame.getContentPane().removeAll();
    frame.setLayout(new BorderLayout());

    catalogPanel = new JPanel(new GridLayout(0, 2));

    outputArea = new JTextArea(); 
    outputArea.setEditable(false);

    JSplitPane splitPane = new JSplitPane(
        JSplitPane.VERTICAL_SPLIT,
        new JScrollPane(catalogPanel),
        new JScrollPane(outputArea)
    );
    splitPane.setDividerLocation(200);

    frame.add(splitPane, BorderLayout.CENTER);

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
        String customerName = JOptionPane.showInputDialog(frame, "Customer Name: ");
        if (customerName == null || customerName.isBlank()){
            return;
        }

        currentOrder = new Order(customerName);
        outputArea.setText("Order created for: " + customerName + "\n\nSelect items to add to order.");

        catalogPanel.removeAll();

        for (ShopItem item : shop.getItems()) {
            JButton btn = new JButton(item.getName()+ " ($"+item.getPrice()+") ["+ item.getQuantity()+ "]");

            btn.setEnabled(item.getQuantity() > 0);

            btn.addActionListener(btnEvent -> {
                if(item.getQuantity() <= 0){ 
                    JOptionPane.showMessageDialog(frame, "Out of Stock!");
                    return;
                }

                item.setQuantity(item.getQuantity() - 1);
                currentOrder.addItem(item);
                outputArea.setText(currentOrder.toString());

                btn.setText(item.getName()+ " ($" +item.getPrice() + ") [" +item.getQuantity() + "]");
                btn.setEnabled(item.getQuantity() > 0);
            });

            catalogPanel.add(btn);
        }

        catalogPanel.revalidate();
        catalogPanel.repaint();
    });

    // VIEW ORDERS
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
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI(new Shop());
        });
    }
}