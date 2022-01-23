package Services;

import Entities.Purchaser;
import MyConnection.PostgresConnection;
import REPOSITORIES.PurchaserRepositories;

import java.sql.SQLException;

public class PurchaserServices {
    Purchaser loggedInPurchaser;
    PurchaserRepositories purchaserRepositories=new PurchaserRepositories(PostgresConnection.getInstance().getConnection());
public void register(String userName,String password) throws SQLException {
    purchaserRepositories.register(userName,password);
}
public boolean login(String userName,String password) throws SQLException {

    loggedInPurchaser=purchaserRepositories.login(userName, password);
    if(loggedInPurchaser!=null){
        return true;
    }
    return false;
}
public  void logout(){
    loggedInPurchaser=null;
}
public void reserve(Integer filmId,Integer quantity) throws SQLException {
    purchaserRepositories.reserve(filmId,quantity,loggedInPurchaser);
}
public void showingShopBasket() throws SQLException {
purchaserRepositories.showingShopBasket(loggedInPurchaser);
}
}
