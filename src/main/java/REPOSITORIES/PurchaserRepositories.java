package REPOSITORIES;

import Entities.Purchaser;
import MyConnection.PostgresConnection;

import java.sql.*;
import java.util.Objects;

public class PurchaserRepositories {
Connection connection= PostgresConnection.getInstance().getConnection();
PreparedStatement preparedStatement;
Statement statement;

    public PurchaserRepositories(Connection connection) {
        this.connection = connection;
    }

    public void register(String userName, String password) throws SQLException {
    String sql="insert into purchaser(username,password) values(?,?)";
    preparedStatement=connection.prepareStatement(sql);
    if( userName!=null && password!=null){
        preparedStatement.setString(1,userName);
        preparedStatement.setString(2,password);
        preparedStatement.executeUpdate();
    }else System.out.println("please try again,something is null!");
}
public Purchaser login(String userName,String password) throws SQLException {
    String purchase="select * from purchaser ";
    preparedStatement=connection.prepareStatement(purchase);
    ResultSet purchaseResult = preparedStatement.executeQuery();
    while(purchaseResult.next()){
        if(Objects.equals(purchaseResult.getString("username"), userName) && Objects.equals(purchaseResult.getString("password"), password)){
            Purchaser purchaser=new Purchaser(userName, password);
            return purchaser ;
        }
    }
    return null;
}
public void reserve(int filmId,int quantity,Purchaser loggedInPurchase) throws SQLException {
    preparedStatement= connection.prepareStatement("select * from ticket where id=? ");
    preparedStatement.setInt(1,filmId);
    ResultSet resultSet=preparedStatement.executeQuery();
    if(resultSet.next()) {
        if (resultSet.getInt("quantity") >= quantity) {

            preparedStatement = connection.prepareStatement("insert into reserve(user_name,ticket_id,quantity) values (?,?,?)");
            preparedStatement.setString(1,loggedInPurchase.getUserName() );
            preparedStatement.setInt(2, resultSet.getInt("id"));
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("update ticket set quantity=? where id=?");
            preparedStatement.setInt(1, resultSet.getInt("quantity") - quantity);
            preparedStatement.setInt(2, resultSet.getInt("id"));
            preparedStatement.executeUpdate();
        } else System.out.println("there is not enough quantity please try again with less number");
    }else System.out.println("there is no film with this id");
}
public void showingShopBasket(Purchaser loggedInPurchaser) throws SQLException {
    preparedStatement=connection.prepareStatement("select * from reserve where user_name=?");
    preparedStatement.setString(1, loggedInPurchaser.getUserName());
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
}
