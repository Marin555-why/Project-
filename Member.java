class Member extends User {
    String username;
    String email;
    int ID;
    // Consructor
    Member(String username, String email, int ID) {
        this.username = username;
        this.email = email;
        this.ID = ID;
        this.role = "member";
        
    }  // end of constructor
    
}
