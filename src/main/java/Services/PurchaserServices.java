package Services;

import Entities.Purchaser;
import MyConnection.PostgresConnection;
import REPOSITORIES.PurchaserRepositories;

import java.sql.SQLException;

public class PurchaserServices {
    Purchaser loggedInPurchaser;
    PurchaserRepositories purchaserRepositories=new PurchaserRepositories(PostgresConnection.getInstance().getConnection());
public void register(String userName,String password) {
    purchaserRepositories.register(userName,password);
}
public boolean login(String userName,String password)  {

    loggedInPurchaser=purchaserRepositories.login(userName, password);
    if(loggedInPurchaser!=null){
        return true;
    }
    return false;
}
public  void logout(){
    loggedInPurchaser=null;
}
public void reserve(Integer filmId,Integer quantity)  {
    purchaserRepositories.reserve(filmId,quantity,loggedInPurchaser);
}
public void showingShopBasket()  {
purchaserRepositories.showingShopBasket(loggedInPurchaser);
}
}
