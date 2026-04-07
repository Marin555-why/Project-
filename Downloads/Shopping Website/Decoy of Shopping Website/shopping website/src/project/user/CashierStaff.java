package project.user;
import project.plugins.Shop;
public class CashierStaff extends Staff {

    public CashierStaff(String staffName ,String staffId, String phoneNumber,String fullName,String password,String role){
        super(staffName ,staffId, phoneNumber, fullName, password, role);
    }

    @Override
    public boolean can(String action) {
        if (action == null) return false;
        String a= action.trim().toUpperCase();
        return a.equals(Shop.CREATE_ORDER)||a.equals(Shop.MANAGE_ORDERS);
    }
}