class User{
    String role;
    boolean canPurchase(){
        return role.equals("member");
    }
    
    }