package REPOSITORIES;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Scanner;

public class REPOSITORIES {
    PreparedStatement preparedStatement;
    Statement statement;
    Connection connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","postgres");
    Scanner input=new Scanner(System.in);
    private String currentUser;
    public REPOSITORIES() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String createTableAdmin="";
        String createTableCinema="";
        String createTableTicket="";
        String createTableUser="";
        String createTableReserve="";

        statement=connection.createStatement();
        statement.execute(createTableAdmin);
        statement.execute(createTableCinema);
        statement.execute(createTableTicket);
        statement.execute(createTableUser);
        statement.execute(createTableReserve);
        preparedStatement=connection.prepareStatement("");
        preparedStatement.setString(1,"admin");
        preparedStatement.setString(2,"admin");
        preparedStatement.executeUpdate();
        currentUser ="";
    }
    public void registerAdmin() throws SQLException {


    }
    public void registerCinema() throws SQLException{

    }
    public void registerPurchaser() throws SQLException{

    }
  /*  public void registerTickets() throws SQLException, ParseException {


        String cinema_name=convertUsernameToCinemaName();
        if (film_name!=null && quantity>0 && date1 !=null){
            String sql="insert into ticket(film_name,cinema_name,dateOfPerformance,quantity,price) values (?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,film_name);
            preparedStatement.setString(2,cinema_name);
            preparedStatement.setDate(3,date1);
            preparedStatement.setInt(4,quantity);
            preparedStatement.setInt(5,price);
            preparedStatement.executeUpdate();
        } else System.out.println("please try again,something is null!");
    }*/
    public boolean checkCinemaVerification() throws SQLException {return true;}
    public String login() throws SQLException{
        return "undefined";
    }
    public void logout(){
        currentUser ="";
    }
    public String convertUsernameToCinemaName() throws SQLException {return "";}
    public void cancellation() throws SQLException {

    }
    public void showingTickets() throws SQLException {

    }
    public void reservingTicket() throws SQLException{
        System.out.println("please enter id of the film you want to reserve");
        int id=input.nextInt();
        System.out.println("please enter quantity of ticket you want to reserve");
        int quantity=input.nextInt();

    }
    public void searchTicket() throws SQLException, ParseException {

    }
    public void showingShopBasket() throws SQLException{

    }
    public void verification() throws SQLException{

    }
    public void showingCinemaSales() throws SQLException{

    }
}