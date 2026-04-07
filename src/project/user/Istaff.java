package project.user;
public interface Istaff{
    String getStaffId();
    String getStaffName();
    String getStaffPassword();
    String getRole();
    boolean can(String action);
}
