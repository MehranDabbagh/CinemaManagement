package Services;

import Entities.Admin;
import MyConnection.PostgresConnection;
import REPOSITORIES.AdminRepositories;
import REPOSITORIES.CinemaRepositories;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminServices {
    Connection connection;
    private AdminRepositories adminRepositories=new AdminRepositories(PostgresConnection.getInstance().getConnection());
    private CinemaRepositories cinemaRepositories=new CinemaRepositories(PostgresConnection.getInstance().getConnection());
    private Admin loggedInAdmin;
    public void register(String userName,String password) {
        adminRepositories.register(userName,password);
    }
    public Boolean login(String userName,String password)  {
      loggedInAdmin=  adminRepositories.login(userName, password);
      if(loggedInAdmin==null){
          return false;
      }
      return true;
    }
    public void verification(String cinema_name)  {
        cinemaRepositories.showingUnVerifiedCinemas();
      int a= adminRepositories.verification(cinema_name);
if(a>0){
    System.out.println("done");
}else System.out.println("there is no cinema with this name!");
    }
    public void logout(){
        loggedInAdmin=null;
    }
}
