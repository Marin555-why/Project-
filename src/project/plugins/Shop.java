package project.plugins;

import project.assets.Order;
import project.assets.ShopItem;
import project.assets.ShopSet;

import project.user.Customer;
import project.user.EmployeeStaff;
import project.user.Istaff;
import project.user.ManagerStaff;
import project.user.Staff;
import project.user.CashierStaff;

import java.util.*;
import java.util.function.Predicate;

public class Shop {

    public static final String CREATE_EMPLOYEE = "CREATE_EMPLOYEE";
    public static final String MANAGE_ORDERS = "MANAGE_ORDERS";
    public static final String CREATE_CUSTOMER = "CREATE_CUSTOMER";
    public static final String CREATE_ORDER = "CREATE_ORDER";

    private ArrayList<Staff> staffs;
    private ArrayList<Order> orders;
    public ArrayList<Customer> customers = new ArrayList<>();
    public Staff loggedInUser;
    private ArrayList<ShopItem> items;
    public Shop() {
        items = new ArrayList<>();
        orders = new ArrayList<>();
        staffs = new ArrayList<>();
        // sample items(newly added included)
            items.add(new ShopItem("Brick", 100.0, 10, false));
            items.add(new ShopItem("BB Gun", 150.0, 5, false));
            items.add(new ShopItem("Duck Chair", 50.0, 20, false));
            items.add(new ShopItem("Sofa", 200.0, 3, false));
            items.add(new ShopItem("Action Figure", 500.0, 2, true));
            items.add(new ShopItem("Plushie", 300.0, 4, true));
        // sample sets
        ShopSet set = new ShopSet("Kids Set");
        set.addItem(items.get(0)); // Brick
        set.addItem(items.get(2)); // Duck Chair
        staffs.add(new ManagerStaff("admin", "001", "000", "Admin", "admin123", "Manager"));
        // sample staff
        staffs.add(new ManagerStaff("John", "002", "017539098", "John Pork", "p0rkch0ps", "Manager"));
        staffs.add(new CashierStaff("Meal", "101", "012823475", "Meal Armstrong", "f00d", "Cashier"));
        staffs.add(new EmployeeStaff("Buzz", "102", "017283293", "Buzz Heavyear", "mothersip", "Employee"));
    }


    
    public void validatePassword(String password) throws LoginException {
        if (password.length() > 8 || !password.matches(".*[.,?/].*") || password.matches(".*[0-9].*")) {
            throw new LoginException("Password can't contain more than 8 characters or symbols like . , ? /");
        }
    }
   
    
    public boolean login(String username, String password) {
    Optional<Staff> found = staffs.stream()
            .filter(s -> s.getStaffName().equals(username)
                    && s.checkPassword(password))
            .findFirst();

    if (found.isPresent()) {
        loggedInUser = found.get();
        System.out.println("Logged in as: " + loggedInUser.getRole());
        return true;
    } else {
        System.out.println("Invalid credentials (continuing as customer)");
        return false;
    }
}
    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void customerMenu(Scanner sc) {
        System.out.println("\n=== CUSTOMER MENU ===");

        displayItems();

        ArrayList<ShopItem> cart = new ArrayList<>();

        while (true) {
            System.out.println("Enter item name to order (or 'done'):");
            String name = sc.nextLine();

            if (name.equalsIgnoreCase("done")) break;

            boolean found = false;

            for (ShopItem item : items) {
                if (item.getName().equalsIgnoreCase(name)) {
                    cart.add(item);
                    System.out.println("Added: " + item.getName());
                    found = true;
                    break;
                }
            }

            if (!found) System.out.println("Item not found");
        }

        System.out.println("Enter your name:");
        String customerName = sc.nextLine();

        Customer c = new Customer(customerName,0,"","", "", loggedInUser);
        Order order = new Order(customerName);
        for(ShopItem item:cart){
            order.addItem(item);
        }
        orders.add(order);

        System.out.println("Order placed successfully!");
    }
    public void addStaff(Staff staff){
        staffs.add(staff);
    }

    // Login Methods

    public void staffLogin(Scanner sc) {
        try {
            System.out.println("Username:");
            String username = sc.nextLine();

            System.out.println("Password:");
            String password = sc.nextLine();

            validatePassword(password);
            Optional<Staff> found = staffs.stream()
                    .filter(s -> s.getStaffName().equals(username) && s.checkPassword(password))
                    .findFirst();

            if (found.isPresent()) {
                loggedInUser = found.get();
                System.out.println("Logged in as: " + loggedInUser.getRole());
                staffMenu(sc);
            } else {
                System.out.println("Invalid credentials");
            }

        } catch (LoginException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void staffMenu(Scanner sc) {
        while (true) {
            System.out.println("\n=== STAFF MENU ===");
            System.out.println("1. Create Employee");
            System.out.println("2. View Orders");
            System.out.println("3. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> createStaff(sc);
                case 2 -> manageOrders();
                case 3 -> {
                    logout();
                    return;
                }
            }
        }
    }

    private boolean requirePermission(String action) {
        if (loggedInUser == null) {
            System.out.println("Login first.");
            return false;
        }

        if (!loggedInUser.can(action)) {
            System.out.println("Access denied for: " + loggedInUser.getRole());
            return false;
        }
        return true;
    }

    public void createStaff(Scanner sc) {
        if (!requirePermission(CREATE_EMPLOYEE)) return;

        System.out.println("Enter name:");
        String fullname = sc.nextLine();

        System.out.println("Enter ID:");
        String id = sc.nextLine();

        System.out.println("Enter username:");
        String username = sc.nextLine();

        System.out.println("Enter password:");
        String password = sc.nextLine();

        System.out.println("Role (Cashier/Employee):");
        String role = sc.nextLine();

        Staff s;
        if (role.equalsIgnoreCase("Cashier")) {
            s = new CashierStaff(username, id, "", fullname, password, "Cashier");
        } else {
            s = new EmployeeStaff(username, id, "", fullname, password, "Employee");
        }

        staffs.add(s);
        System.out.println("Staff created!");
    }

    public void manageOrders() {
        if (!requirePermission(MANAGE_ORDERS)) return;

        if (orders.isEmpty()) {
            System.out.println("No orders.");
            return;
        }

        orders.forEach(System.out::println);
        Predicate<Order> bigOrder = new Predicate<>() {
            @Override
            public boolean test(Order o) {
                return o.getTotalItemCount() > 2;
            }
        };

        System.out.println("\nBig Orders:");
        orders.stream()
                .filter(bigOrder)
                .forEach(System.out::println);
    }

    public void displayItems() {
        System.out.println("\nAvailable Items:");
        for (ShopItem item : items) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }

    public void logout() {
        loggedInUser = null;
        System.out.println("Logged out.");
    }
    public Staff getLoggedInUser(){
        return loggedInUser;
    }
    public List<ShopItem> getItems() {
        return items;
    }
    public Order createOrder(String name, List<String> itemNames){
        Order order = new Order(name); 
        for(String n:itemNames){
            for (ShopItem item : items){
                if(item.getName().equalsIgnoreCase(n.trim())){
                    order.addItem(item);
                }
            }
        }
        orders.add(order);
        return order;
    }
    
    public ArrayList<Order> getOrders(){
        return orders;
    }
    public ArrayList<Customer> getCustomers(){
        return customers;
    }
}
