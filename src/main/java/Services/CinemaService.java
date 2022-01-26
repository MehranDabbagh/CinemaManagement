package Services;

import Entities.Cinema;
import MyConnection.PostgresConnection;
import REPOSITORIES.CinemaRepositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public class CinemaService {
    Cinema loggedInCinema=new Cinema();
    CinemaRepositories cinemaRepositories=new CinemaRepositories(PostgresConnection.getInstance().getConnection());
    TicketService ticketService=new TicketService();
    public void register(String cinemaName,String userName,String password) {
        cinemaRepositories.register(cinemaName,userName,password);
    }
    public boolean login(String userName,String password) {
        loggedInCinema=cinemaRepositories.login(userName,password);
        if(loggedInCinema!=null){
            return true;
        }
        return false;
    }
    public void logout(){
        loggedInCinema=null;
    }
    public String convertCinemaNameToUserName()  {
       return cinemaRepositories.convertUsernameToCinemaName(loggedInCinema.getUserName());
    }
    public void canceling(int id) {
     cinemaRepositories.canceling(id,loggedInCinema.getCinemaName());
    }
    public void addingTicket(String filmName, String dateOfPerformance,int number,int price)  {
ticketService.register(filmName,loggedInCinema.getCinemaName(),dateOfPerformance,number,price);
    }
    public void showingSales() {
cinemaRepositories.showingSales(loggedInCinema.getCinemaName());
}
    public boolean checkingCinemaVerification()  {
 return cinemaRepositories.checkingCinemaVerification(loggedInCinema.getCinemaName());
    }
    public void showingUnVerifiedCinemas()  {
        cinemaRepositories.showingUnVerifiedCinemas();
    }
}
