package REPOSITORIES;

import Entities.Ticket;
import MyConnection.PostgresConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketRepositories {
    PreparedStatement preparedStatement;
    Connection connection;

    public TicketRepositories(Connection connection) {
        this.connection = connection;
    }

    public void register(String filmName, String cinemaName, String dateOfPerformance, int number, int price) {
        try{
        java.util.Date performanceDate=new SimpleDateFormat("dd/MM/yyyy").parse(dateOfPerformance);
        java.sql.Date date1=new  java.sql.Date(performanceDate.getTime());
        if (filmName!=null && number>0 && date1 !=null){
            String sql="insert into ticket(film_name,cinema_name,dateOfPerformance,quantity,price) values (?,?,?,?,?)";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,filmName);
            preparedStatement.setString(2,cinemaName);
            preparedStatement.setDate(3,date1);
            preparedStatement.setInt(4,number);
            preparedStatement.setInt(5,price);
            preparedStatement.executeUpdate();
        } else System.out.println("please try again,something is null!");
        } catch (NullPointerException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
   public List<Ticket> showingTickets() {
        try{
     preparedStatement=connection.prepareStatement("select * from ticket");
     ResultSet resultSet=preparedStatement.executeQuery();
     List<Ticket> tickets=new ArrayList<>();
     while(resultSet.next()){
         java.util.Date newDate = new Date(resultSet.getDate("dateofperformance").getTime());
         Ticket ticket=new Ticket(resultSet.getInt("id"),resultSet.getString("film_name"),resultSet.getString("cinema_name"),newDate,resultSet.getInt("quantity"),resultSet.getInt("price"));

         tickets.add(ticket);
     }  return tickets;
        } catch (SQLException e){
            e.printStackTrace();
        }
   return null;
 }
 public List<Ticket> searchTicket(String filmName,String date)  {
        try {
            java.util.Date performanceDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            java.sql.Date date1 = new java.sql.Date(performanceDate.getTime());
            preparedStatement = connection.prepareStatement("select * from ticket where film_name=? and dateOfPerformance=? ");
            preparedStatement.setString(1, filmName);
            preparedStatement.setDate(2, date1);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                java.util.Date newDate = new Date(resultSet.getDate("dateofperformance").getTime());
                Ticket ticket = new Ticket(resultSet.getInt("id"), resultSet.getString("film_name"), resultSet.getString("cinema_name"), newDate, resultSet.getInt("quantity"), resultSet.getInt("price"));
                tickets.add(ticket);
            }
            return tickets;
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
 }

}
