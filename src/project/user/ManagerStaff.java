package project.user;
public class ManagerStaff extends Staff{

    public ManagerStaff(String staffName ,String staffId, String phoneNumber,String fullName,String password,String role){
        super(staffName ,staffId, phoneNumber, fullName, password, role);
    }

    @Override
    public boolean can(String action) {
        return true;
    }
}
    

    