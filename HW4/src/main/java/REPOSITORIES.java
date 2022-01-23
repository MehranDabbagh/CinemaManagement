import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
         String createTableAdmin="CREATE TABLE IF not exists admin(username varchar(50) not null,password varchar(50) not null)";
        String createTableCinema="CREATE TABLE IF not exists cinema(cinema_name varchar(50) not null,username varchar(50) not null,password varchar(50) not null,verification varchar (50) not null)";
        String createTableTicket="CREATE TABLE IF not exists ticket(id bigserial,film_name varchar(50) not null,cinema_name varchar(50) not null,dateOfPerformance date not null,quantity int not null,price int not null )";
        String createTableUser="CREATE TABLE IF not exists purchaser(username varchar(50) not null,password varchar(50) not null)";
        String createTableReserve="CREATE TABLE IF not exists reserve(user_name varchar(50) not null,ticket_id varchar(50) not null,quantity int )";

        statement=connection.createStatement();
  statement.execute(createTableAdmin);
  statement.execute(createTableCinema);
  statement.execute(createTableTicket);
  statement.execute(createTableUser);
  statement.execute(createTableReserve);
        preparedStatement=connection.prepareStatement("INSERT INTO admin(username,password) VALUES (?,?)");
        preparedStatement.setString(1,"admin");
        preparedStatement.setString(2,"admin");
        preparedStatement.executeUpdate();
        currentUser ="";
    }
    public void registerAdmin() throws SQLException {
        String sql="insert into admin(username,password) values(?,?)";
        preparedStatement=connection.prepareStatement(sql);
        System.out.println("please enter your username");
        String username=input.next();
        System.out.println("please enter your password");
        String password=input.next();
        if( username!=null && password!=null){
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.executeUpdate();
        }else System.out.println("please try again,something is null!");

    }
    public void registerCinema() throws SQLException{
        String sql="INSERT INTO cinema VALUES (?,?,?,?)";
        preparedStatement=connection.prepareStatement(sql);
        System.out.println("please enter your cinema name");
        String cinemaName=input.next();
        System.out.println("please enter your username");
        String username=input.next();
        System.out.println("please enter your password");
        String password=input.next();
        if(cinemaName!=null && username!=null && password!=null){
            preparedStatement.setString(1,cinemaName);
            preparedStatement.setString(2,username);
            preparedStatement.setString(3,password);
            preparedStatement.setString(4,"false");
            preparedStatement.executeUpdate();

        } else System.out.println("please try again,something is null!");

    }
    public void registerPurchaser() throws SQLException{
        String sql="insert into purchaser(username,password) values(?,?)";
        preparedStatement=connection.prepareStatement(sql);
        System.out.println("please enter your username");
        String username=input.next();
        System.out.println("please enter your password");
        String password=input.next();
        if( username!=null && password!=null){
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.executeUpdate();
        }else System.out.println("please try again,something is null!");
    }
    public void registerTickets() throws SQLException, ParseException {

        System.out.println("please enter your film name");
        String film_name=input.next();
        System.out.println("please enter your date of performance (DD/MM/YYYY)");
        String date=input.next();
        Date performanceDate=new SimpleDateFormat("dd/MM/yyyy").parse(date);
        java.sql.Date date1=new  java.sql.Date(performanceDate.getTime());
        System.out.println("please enter number of tickets");
        int quantity=input.nextInt();
        System.out.println("please enter the price of your film");
 int price=input.nextInt();
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
    }
    public Boolean checkCinemaVerification() throws SQLException {
        String cinemaName=convertUsernameToCinemaName();
        String sql="select * from cinema where username=?";
        preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,cinemaName);
        ResultSet result =preparedStatement.executeQuery() ;
        result.next();
        if(Objects.equals(result.getString("verification"), "true")){
            return true;
        } else return false;
    }
    public String login() throws SQLException{
        System.out.println("please enter your username");
        String username=input.next();
        System.out.println("please enter your password");
        String password=input.next();
        if(username!=null && password!=null){
            String admin="select * from admin";
            ResultSet adminResult = statement.executeQuery(admin);
          while(adminResult.next()){
            if(Objects.equals(adminResult.getString("username"), username) && Objects.equals(adminResult.getString("password"), password)){
                currentUser =username;
                return "admin";
            }
            }
            String cinema="select * from cinema ";
            ResultSet cinemaResult = statement.executeQuery(cinema);
            while(cinemaResult.next()){
                if(Objects.equals(cinemaResult.getString("username"), username) && Objects.equals(cinemaResult.getString("password"), password)){
                    currentUser =username;
                    return "cinema";
                }
            }

            String purchase="select * from purchaser ";
            ResultSet purchaseResult = statement.executeQuery(purchase);
            while(purchaseResult.next()){
                if(Objects.equals(purchaseResult.getString("username"), username) && Objects.equals(purchaseResult.getString("password"), password)){
                    currentUser =username;
                    return "purchase";
                }
            }

            }else System.out.println("something is null please try again!");
            return "undefined";
        }
    public void logout(){
        currentUser ="";
    }
    public String convertUsernameToCinemaName() throws SQLException {
        preparedStatement=connection.prepareStatement("select * from  cinema where username=?");
        preparedStatement.setString(1, currentUser);
        ResultSet resultSet=preparedStatement.executeQuery();
        resultSet.next();
           return resultSet.getString("cinema_name");
    }
    public void cancellation() throws SQLException {
        String cinemaName=convertUsernameToCinemaName();
        System.out.println("please enter id of the film that you want to cancel");
        int id=input.nextInt();
        preparedStatement=connection.prepareStatement("select * from  ticket where cinema_name=? and id=? and dateOfPerformance>?");
        Date date=java.util.Calendar.getInstance().getTime();
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
    public void showingTickets() throws SQLException {
        preparedStatement=connection.prepareStatement("select * from ticket");
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getInt("id")+"."+"film name:"+resultSet.getString("film_name")+"  cinema:"+resultSet.getString("cinema_name")+
                    "  date of performance:"+ resultSet.getDate("dateOfPerformance")+" quantity:"+resultSet.getInt("quantity")+" price:"+resultSet.getInt("price")+"$");
        }
    }
    public void reservingTicket() throws SQLException{
        System.out.println("please enter id of the film you want to reserve");
        int id=input.nextInt();
        System.out.println("please enter quantity of ticket you want to reserve");
        int quantity=input.nextInt();
       preparedStatement= connection.prepareStatement("select * from ticket where id=? and quantity>?");
       preparedStatement.setInt(1,id);
       preparedStatement.setInt(2,quantity);
       ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()) {
    if (resultSet.getInt("quantity") >= quantity) {

            preparedStatement = connection.prepareStatement("insert into reserve(user_name,ticket_id,quantity) values (?,?,?)");
            preparedStatement.setString(1, currentUser);
            preparedStatement.setInt(2, resultSet.getInt("id"));
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("update ticket set quantity=? where id=?");
            preparedStatement.setInt(1, resultSet.getInt("quantity") - quantity);
            preparedStatement.setInt(2, resultSet.getInt("id"));
            preparedStatement.executeUpdate();


    } else System.out.println("there is not enough quantity please try again with less number");
}
    }
    public void searchTicket() throws SQLException, ParseException {
        System.out.println("please enter name of the film");
        String filmName =input.next();
        System.out.println("please enter date of the film(DD/MM/YYYY)");
        String date=input.next();
        Date performanceDate=new SimpleDateFormat("dd/MM/yyyy").parse(date);
        java.sql.Date date1=new  java.sql.Date(performanceDate.getTime());
       preparedStatement=connection.prepareStatement("select * from ticket where film_name=? and dateOfPerformance=? ");
      preparedStatement.setString(1, filmName);
      preparedStatement.setDate(2,date1);
       ResultSet resultSet1=preparedStatement.executeQuery();
       boolean condition=false;

           if(resultSet1.next()) {

               System.out.println("flim name:" + resultSet1.getString("film_name") + " cinema name:" + resultSet1.getString("cinema_name") +
                       " date " + resultSet1.getString("dateOfPerformance") + " quantity" + resultSet1.getString("quantity"));
               condition = true;
               resultSet1 = null;
           }else resultSet1.next();
       if(condition){
           return;
       }else System.out.println("there is no ticket with this condition");
       return;
    }
    public void showingShopBasket() throws SQLException{
      preparedStatement=connection.prepareStatement("select * from reserve where user_name=?");
      preparedStatement.setString(1, currentUser);
      ResultSet resultSet=preparedStatement.executeQuery();
       int sum=0;
        System.out.println("you shop basket is :");
        while(resultSet.next()) {
            preparedStatement = connection.prepareStatement("select * from ticket where id=?");
            preparedStatement.setInt(1, resultSet.getInt("ticket_id"));
            ResultSet resultSet1 = preparedStatement.executeQuery();
            if (resultSet1.next()) {
                sum = sum + (resultSet1.getInt("price")*resultSet.getInt("quantity"));
                System.out.println("film name:" + resultSet1.getString("film_name") + " quantity:"+ resultSet.getInt("quantity")+" price:" + resultSet1.getInt("price")+"$");
            }

        }
        System.out.println("total price: "+sum+"$");
    }
    public void verification() throws SQLException{
        String current="false";
        String current1="true";
        preparedStatement=connection.prepareStatement("select * from cinema where verification=?");
        preparedStatement.setString(1,current);
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println("cinema name:"+resultSet.getString("cinema_name"));
        }
        System.out.println("please enter name of the cinema you want to make active ");
        String cinema=input.next();
        preparedStatement=connection.prepareStatement("select * from cinema where cinema_name=?");
        preparedStatement.setString(1,cinema);

        ResultSet resultSet1=preparedStatement.executeQuery();
        if(resultSet1.next()){
        preparedStatement=connection.prepareStatement("update cinema set verification=?");
        preparedStatement.setString(1,current1);
        preparedStatement.executeUpdate();
        }
    }
    public void showingCinemaSales() throws SQLException{
        int sum=0;
        preparedStatement=connection.prepareStatement("select (B.C)D from  (select (ticket.id)A,(ticket.price)C from ticket  join cinema on ticket.cinema_name=cinema.cinema_name)B inner join reserve  ON cast(B.A as int)=cast(reserve.ticket_id as int)   ");
        ResultSet resultSet=preparedStatement.executeQuery();
        preparedStatement=connection.prepareStatement("select (reserve.quantity)E from  (select (ticket.id)A,(ticket.price)C from ticket  join cinema on ticket.cinema_name=cinema.cinema_name)B inner join reserve  ON cast(B.A as int)=cast(reserve.ticket_id as int)   ");
        ResultSet resultSet1=preparedStatement.executeQuery();
        while(resultSet.next() ){
if(resultSet1.next()){
           sum=sum+ (resultSet.getInt("D")*resultSet1.getInt("E"));
        }}
        System.out.println("your total sale is :"+sum); ;
    }
}
