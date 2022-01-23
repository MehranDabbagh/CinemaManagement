import java.sql.*;
import java.text.ParseException;
import java.util.Objects;
import java.util.Scanner;

public class MAIN {
    static Scanner input = new Scanner(System.in);
    private static REPOSITORIES repositories;
    public MAIN() throws SQLException, ClassNotFoundException {
        repositories = new REPOSITORIES();
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        repositories = new REPOSITORIES();
        Boolean condition = true;
        String menu = "undefined";
        while (condition) {
            System.out.println("please enter 1 for login 2 for register 3 for exit");
            int operator = input.nextInt();
            switch (operator) {
                case 1: {
                    menu = repositories.login();
                    if (Objects.equals(menu, "admin")) {
                        adminMenu();
                    } else if (Objects.equals(menu, "cinema")) {
                        cinemaMenu();
                    } else if (Objects.equals(menu, "purchase")) {
                        purchaseMenu();
                    } else System.out.println("there is no user with this username and password,try again!");
                    break;
                }
                case 2:
                    registerMenu();
                    break;
                case 3:
                    condition = false;
                    break;
            }

        }


    }
    public static void adminMenu() throws SQLException {
        Boolean condition=true;
        while(condition){
            System.out.println("please enter 0 for register new admin and 1 for verification cinema accounts and 2 for logout");
      int operator=input.nextInt();
      switch (operator){
          case 0:
              repositories.registerAdmin();break;
          case 1:
              repositories.verification();break;
          case 2:
              repositories.logout();condition=false;break;
      }
        }
    }
    public static void cinemaMenu() throws SQLException, ParseException {
        Boolean condition=true;
        while(condition){
            if(repositories.checkCinemaVerification()){
                System.out.println("please enter 0 for register new ticket and 1 for canceling a ticket and  2 for showing your total sales and 3 for logout");
                int operator=input.nextInt();
                switch (operator){
                    case 0:
                        repositories.registerTickets();break;
                    case 1:
                        repositories.cancellation();break;
                    case 2:
                        repositories.showingCinemaSales();break;
                    case 3:
                        repositories.logout();condition=false;break;
                }
            }else {
                System.out.println("your are not verified yet, please wait until admin verifies you!");
condition=false;
            }
        }
    }
    public static void purchaseMenu() throws SQLException, ParseException {
        Boolean condition=true;
        while(condition){
            System.out.println("please enter 0 for showing tickets and 1 for reserving ticket 2 for showing your shop basket and 3 for search and 4 for logout" );
            int operator=input.nextInt();
            switch (operator){
                case 0:
                    repositories.showingTickets();break;
                case 1:
                    repositories.reservingTicket();break;
                case 2:
                    repositories.showingShopBasket();break;
                case 3:
                    repositories.searchTicket();break;
                case 4:
                    repositories.logout();condition=false;break;
            }
        }
    }
    public static void registerMenu() throws SQLException {
        boolean condition = true;
        while (condition) {
            System.out.println("please enter 0 for register cinema acc and 1 for costumer and 2 for exit");
            int operator = input.nextInt();
            switch (operator) {
                case 0:
                    repositories.registerCinema();
                    break;
                case 1:
                    repositories.registerPurchaser();
                    break;
                case 2:
                    condition = false;
            }
        }
    }
}

