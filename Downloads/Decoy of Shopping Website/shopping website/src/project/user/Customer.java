package project.user;
public class Customer{
    private String Username;
    private int id;
    private String password;
    private String email;
    private String phoneNumber;
    private boolean active;
    private Istaff createdBy;

        
    
    public Customer(String Username, int id, String password, String email, String phoneNumber, Staff createdBy) {
                this.Username= Username;
                this.id= id;
                this.password=password;
                this.email=email;
                this.phoneNumber=phoneNumber;
                this.active=true;
                this.createdBy=createdBy;

    }
    }



    