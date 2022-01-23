package REPOSITORIES;

import Entities.Cinema;
import MyConnection.PostgresConnection;

import java.sql.*;
import java.util.Objects;

public class CinemaRepositories {
    Connection connection= PostgresConnection.getInstance().getConnection();
    PreparedStatement preparedStatement;
    Statement statement;

    public CinemaRepositories(Connection connection) {
        this.connection = connection;
    }

    public void register(String cinemaName, String userName, String password) throws SQLException {
        String sql="INSERT INTO cinema VALUES (?,?,?,?)";
        preparedStatement=connection.prepareStatement(sql);
        if(cinemaName!=null && userName!=null && password!=null){
            preparedStatement.setString(1,cinemaName);
            preparedStatement.setString(2,userName);
            preparedStatement.setString(3,password);
            preparedStatement.setString(4,"false");
            preparedStatement.executeUpdate();

        } else System.out.println("please try again,something is null!");

    }
public void showingUnVerifiedCinemas() throws SQLException {
    preparedStatement=connection.prepareStatement("select * from cinema where verification=?");
    preparedStatement.setString(1,"false");
    ResultSet resultSet=preparedStatement.executeQuery();
    while(resultSet.next()){
        System.out.println("cinema name:"+resultSet.getString("cinema_name"));
    }

}
public Cinema login(String userName,String password) throws SQLException {
    String sql="select * from cinema ";
    preparedStatement= connection.prepareStatement(sql);
    ResultSet cinemaResult = preparedStatement.executeQuery();
    while(cinemaResult.next()){
        if(Objects.equals(cinemaResult.getString("username"), userName) && Objects.equals(cinemaResult.getString("password"), password)){
           Cinema cinema=new Cinema(cinemaResult.getString("cinema_name"),userName,password);
           cinema.setVerification(cinemaResult.getString("verification"));
            return cinema;
        }
    }
    return null;
}
public String convertUsernameToCinemaName(String userName) throws SQLException {
    preparedStatement=connection.prepareStatement("select * from  cinema where username=?");
    preparedStatement.setString(1, userName);
    ResultSet resultSet=preparedStatement.executeQuery();
    resultSet.next();
    return resultSet.getString("cinema_name");
}
public void canceling(int id,String cinemaName ) throws SQLException {
    preparedStatement=connection.prepareStatement("select * from  ticket where cinema_name=? and id=? and dateOfPerformance>?");
    java.util.Date date=java.util.Calendar.getInstance().getTime();
    java.sql.Date date1=new  java.sql.Date(date.getTime());
    preparedStatement.setString(1,cinemaName);
    preparedStatement.setInt(2,id);
    preparedStatement.setDate(3,date1);
    ResultSet resultSet=preparedStatement.executeQuery();
    if(resultSet.next()){
        preparedStatement=connection.prepareStatement("DELETE FROM ticket WHERE cinema_name=? and id=?");
        preparedStatement.setString(1,cinemaName);
        preparedStatement.setInt(2,id);
        preparedStatement.executeUpdate();
    } else System.out.println("there is no ticket with this conditions or the time have been passed!");
}
public void showingSales(String cinemaName) throws SQLException {
    int sum=0;
    preparedStatement=connection.prepareStatement("select (B.C)D from  (select (ticket.id)A,(ticket.price)C from ticket  join cinema on ticket.cinema_name=cinema.cinema_name where ticket.cinema_name=?)B inner join reserve  ON cast(B.A as int)=cast(reserve.ticket_id as int)   ");
    preparedStatement.setString(1,cinemaName);
    ResultSet resultSet=preparedStatement.executeQuery();
    preparedStatement=connection.prepareStatement("select (reserve.quantity)E from  (select (ticket.id)A,(ticket.price)C from ticket  join cinema on ticket.cinema_name=cinema.cinema_name where ticket.cinema_name=?)B inner join reserve  ON cast(B.A as int)=cast(reserve.ticket_id as int)  ");
    preparedStatement.setString(1,cinemaName);
    ResultSet resultSet1=preparedStatement.executeQuery();
    while(resultSet.next() ){
        if(resultSet1.next()){
            sum=sum+ (resultSet.getInt("D")*resultSet1.getInt("E"));
        }}
    System.out.println("your total sale is :"+sum); ;
}
public boolean checkingCinemaVerification(String cinemaName ) throws SQLException {
    String sql="select * from cinema where username=?";
    preparedStatement=connection.prepareStatement(sql);
    preparedStatement.setString(1,cinemaName);
    ResultSet result =preparedStatement.executeQuery() ;
    result.next();
    if(Objects.equals(result.getString("verification"), "true")){
        return true;
    } else return false;
}
}
