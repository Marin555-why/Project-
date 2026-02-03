import java.util.ArrayList;
import java.util.Scanner;

class Login {
    ArrayList<Member> members = new ArrayList<>();
    User currentUser;
    Scanner sc = new Scanner(System.in);

    void startLogin() {
        System.out.println("1. Guest");
        System.out.println("2. Member");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            loginGuest();
        } else {
            loginMember();
        }
    }

    void loginGuest() {
        currentUser = new User();
        currentUser.role = "GUEST";
        System.out.println("Logged in as Guest (view only)");
    }

    void loginMember() {
        System.out.println("1. Login");
        System.out.println("2. Create account");
        int option = sc.nextInt();
        sc.nextLine();

        if (option == 1) {
            authenticateMember();
        } else {
            createMember();
        }
    }

    void authenticateMember() {
        System.out.print("Username / Email / ID: ");
        String input = sc.nextLine();

        for (Member m : members) {
            if (m.username.equals(input) ||
                m.email.equals(input) ||
                String.valueOf(m.ID).equals(input)) {

                currentUser = m;
                System.out.println("Welcome, " + m.username);
                return;
            }
        }
        System.out.println("Member not found.");
    }

    void createMember() {
        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        int id = members.size() + 1001;
        Member m = new Member(username, email, id);
        members.add(m);
        currentUser = m;

        System.out.println("Account created. ID: " + id);
    }

    void logout() {
        currentUser = null;
        System.out.println("Logged out.");
        startLogin();
    }
}

