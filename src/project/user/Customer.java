package project.user;
public class Customer{
    private String username;
    private int id;
    private String password;
    private String email;
    private String phoneNumber;
    private boolean active;
    private Staff createdBy;

        
    
    public Customer(String username, int id, String password, String email, String phoneNumber, Staff createdBy) {
                this.username=username;
                this.id= id;
                this.password=password;
                this.email=email;
                this.phoneNumber=phoneNumber;
                this.active=true;
                this.createdBy=createdBy;

    }
    public Customer(String username) {
        this.username = username;
        this.active = true;
    }
}



    