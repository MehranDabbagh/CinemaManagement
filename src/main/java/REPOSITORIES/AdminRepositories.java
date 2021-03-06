package REPOSITORIES;

import Entities.Admin;
import MyConnection.PostgresConnection;

import java.sql.*;
import java.util.Objects;

public class AdminRepositories {

Connection connection;
    PreparedStatement preparedStatement;
    public AdminRepositories(Connection connection) {
        this.connection = connection;
    }
    public void register(String userName, String password) {
        try {
            String sql = "insert into admins(username,password) values(?,?)";
            preparedStatement = connection.prepareStatement(sql);
            if (userName != null && password != null) {
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
            } else System.out.println("please try again,something is null!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Admin login(String userName,String password){
        try{
        String sql="select * from admins";
        preparedStatement=connection.prepareStatement(sql);
        ResultSet adminResult = preparedStatement.executeQuery();
        while(adminResult.next()){
            if(Objects.equals(adminResult.getString("username"), userName) && Objects.equals(adminResult.getString("password"), password)){
                Admin admin =new Admin(userName,password);
               return admin;
            }
        }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public int verification(String cinema_name) {
        try{
        preparedStatement=connection.prepareStatement("select * from cinema where cinema_name=? and verification=? ");
        preparedStatement.setString(1,cinema_name);
        preparedStatement.setString(2,"false");
        ResultSet resultSet1=preparedStatement.executeQuery();
        if(resultSet1.next()){
            preparedStatement=connection.prepareStatement("update cinema set verification=? where cinema_name=?");
            preparedStatement.setString(1,"true");
            preparedStatement.setString(2,cinema_name);
            return  preparedStatement.executeUpdate();
        }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

}
