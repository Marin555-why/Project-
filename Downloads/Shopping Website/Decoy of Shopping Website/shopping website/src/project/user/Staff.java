package project.user;
public abstract class Staff implements Istaff{
    private String staffName;
    private String staffId;
    private String phoneNumber;
    private String fullName;
    private String password;
    private String role;
    private boolean active;

    public Staff(String staffName, String staffId, String phoneNumber, String fullName,String password, String role){
        setStaffName(staffName);
        setStaffId(staffId);
        setPhoneNumber(phoneNumber);
        setFullName(fullName);
        setPassword(password);
        this.role = role;
        this.active=true;
    }
    protected String getPassword(){
        return password;
    }

    public String getstaffName() {return staffName;}
    public String getstaffId() {return staffId;}
    public String getphoneNumber() {return phoneNumber;}
    public String getfullName() {return fullName;}
    public String getRole() {return role;}
    public boolean isActive() {return active;}
    public boolean checkPassword(String input){
        return password != null && password.equals(input);
    }
    public void setStaffName(String staffName){
        if (isBlank(staffName)) this.staffName ="Error";
        else this.staffName = staffName.trim();
    }
    public void setStaffId(String staffId) {
        if (isBlank(staffId)) this.staffId = "UNKNOWN";
        else this.staffId = staffId.trim();
    }

    public void setFullName(String fullName) {
        if (isBlank(fullName)) this.fullName = "No Name";
        else this.fullName = fullName.trim();
    }

    public void setPhoneNumber(String phoneNumber) {
        String p = (phoneNumber == null) ? "" : phoneNumber.trim();
        
        if (!isDigits(p) || p.length() < 8 || p.length() > 15) this.phoneNumber = "00000000";
        else this.phoneNumber = p;
    }

    public void setPassword(String password) {
        String pw = (password == null) ? "" : password;
        
        if (pw.length() < 4) this.password = "0000";
        else this.password = pw;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRole(String role){
        this.role = role;
    }

    
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean isDigits(String s) {
        if (isBlank(s)) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    // ====== toString ======
    @Override
    public String toString() {
        return "S{" +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phoneNumber + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Staff s1 = (Staff) obj;
        return phoneNumber.equals(s1.phoneNumber);
    }
    @Override
    public abstract boolean can(String action);
    
    @Override
    public String getStaffId() {
        // TODO Auto-generated method stub
        return staffId;
    }
    @Override
    public String getStaffName() {
        // TODO Auto-generated method stub
        return staffName;
    }
    @Override
    public String getStaffPassword() {
        // TODO Auto-generated method stub
        return password;
    }

    
}