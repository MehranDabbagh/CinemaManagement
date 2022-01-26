import CustomExceptions.DateFormatException;
import CustomExceptions.OutOfRangeInputException;
import Services.AdminServices;
import Services.CinemaService;
import Services.PurchaserServices;
import Services.TicketService;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    private static AdminServices adminServices=new AdminServices();
    private static CinemaService cinemaService=new CinemaService();
    private  static PurchaserServices purchaserServices=new PurchaserServices();
    private  static TicketService ticketService=new TicketService();
    public static void main(String[] args)  {
        Boolean condition = true;
        String menu = "undefined";
        while (condition) {
            try {
                System.out.println("please enter 1 for admin login 2 for cinema login 3 for purchaser login 4 for register 5 for exit");
                int operator = input.nextInt();
                if(operator<1 || operator >5){
                    throw new OutOfRangeInputException("please enter something in range!");
                }
                switch (operator) {
                    case 1:
                        System.out.println("please enter your username");
                        String adminUserName=input.next();
                        System.out.println("please enter your password");
                        String adminPassword=input.next();
                        if(adminServices.login(adminUserName,adminPassword)){
                            adminMenu();
                        }else System.out.println("wrong!");
                        break;
                    case 2:
                        System.out.println("please enter your username");
                        String cinemaUserName=input.next();
                        System.out.println("please enter your password");
                        String cinemaPassword=input.next();
                        if(cinemaService.login(cinemaUserName,cinemaPassword)){
                            cinemaMenu();
                        }else System.out.println("wrong!");
                        break;
                    case 3:   System.out.println("please enter your username");
                        String purchaserUserName=input.next();
                        System.out.println("please enter your password");
                        String purchaserPassword=input.next();
                        if(purchaserServices.login(purchaserUserName,purchaserPassword)){
                            purchaseMenu();
                        }else System.out.println("wrong!");
                        break;
                    case 4:registerMenu();break;
                    case 5:condition=false;break;
                }

            }catch (OutOfRangeInputException e){
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                System.out.println("please enter a number!");
                input.nextLine();
            }

        }


    }
    public static void adminMenu()  {
        Boolean condition=true;
        while(condition){
            try {
                System.out.println("please enter 0 for register new admin and 1 for verification cinema accounts and 2 for logout");
                int operator = input.nextInt();
                if(operator<0 || operator>2){
                    throw new OutOfRangeInputException("please enter something in range!");
                }
                switch (operator) {
                    case 0:
                        System.out.println("please enter your username");
                        String adminUserName = input.next();
                        System.out.println("please enter your password");
                        String adminPassword = input.next();
                        adminServices.register(adminUserName, adminPassword);
                        break;
                    case 1:
                        cinemaService.showingUnVerifiedCinemas();
                        System.out.println("please enter name of the cinema you want to verify");
                        String cinemaName = input.next();
                        adminServices.verification(cinemaName);
                        break;
                    case 2:
                        adminServices.logout();
                        condition = false;
                        break;
                }
            }catch (OutOfRangeInputException e){
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                System.out.println("please enter a number!");
                input.nextLine();
            }
        }
    }
    public static void cinemaMenu() {
        Boolean condition=true;
        while(condition){
            try {
                if (cinemaService.checkingCinemaVerification()) {
                    System.out.println("please enter 0 for register new ticket and 1 for canceling a ticket and  2 for showing your total sales and 3 for logout");
                    int operator = input.nextInt();
                    if(operator<0 || operator>3){
                        throw new OutOfRangeInputException("please enter something in range!");
                    }
                    switch (operator) {
                        case 0:
                            System.out.println("please enter your film name");
                            String film_name = input.next();
                            System.out.println("please enter your date of performance (DD/MM/YYYY)");
                            String date = input.next();
                         String[] dates=date.split("/");
                         if(date.length()!=10 || Integer.valueOf(dates[0])>30 || Integer.valueOf(dates[0])>12 ||  Integer.valueOf(dates[0])>2022 ){
                             throw new DateFormatException( "wrong date format!");
                         }
                            System.out.println("please enter number of tickets");
                            int quantity = input.nextInt();
                            System.out.println("please enter the price of your film");
                            int price = input.nextInt();
                            cinemaService.addingTicket(film_name, date, quantity, price);
                            break;
                        case 1:
                            System.out.println("please enter id of the film you want to cancel");
                            int filmId = input.nextInt();
                            cinemaService.canceling(filmId);
                            break;
                        case 2:
                            cinemaService.showingSales();
                            break;
                        case 3:
                            cinemaService.logout();
                            condition = false;
                            break;
                    }
                } else {
                    System.out.println("your are not verified yet, please wait until admin verifies you!");
                    condition = false;
                }
            }catch (OutOfRangeInputException e){
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                System.out.println("please enter a number!");
                input.nextLine();
            }
        }
    }
    public static void purchaseMenu()  {
        Boolean condition=true;
        while(condition){
            try {
                System.out.println("please enter 0 for showing tickets and 1 for reserving ticket 2 for showing your shop basket and 3 for search and 4 for logout");
                int operator = input.nextInt();
                if (operator < 0 || operator > 4) {
                    throw new OutOfRangeInputException("please enter something in range!");
                }
                switch (operator) {
                    case 0:
                        ticketService.showingTickets();
                        break;
                    case 1:
                        System.out.println("please enter id of the film");
                        int filmId = input.nextInt();
                        System.out.println("please enter quantity");
                        int quantity = input.nextInt();
                        purchaserServices.reserve(filmId, quantity);
                        break;
                    case 2:
                        purchaserServices.showingShopBasket();
                        break;
                    case 3:
                        System.out.println("please enter name of the film");
                        String filmName = input.next();
                        System.out.println("please date of performance");
                        String date = input.next();
                        String[] dates=date.split("/");
                        if(date.length()!=10 || Integer.valueOf(dates[0])>30 || Integer.valueOf(dates[0])>12 ||  Integer.valueOf(dates[0])>2022 ){
                            throw new DateFormatException( "wrong date format!");
                        }
                        ticketService.searchTicket(filmName, date);
                        break;
                    case 4:
                        purchaserServices.logout();
                        condition = false;
                        break;
                }
            }catch (OutOfRangeInputException e){
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                System.out.println("please enter a number!");
                input.nextLine();
            }catch (DateFormatException e){
                System.out.println(e.getMessage());

            }
        }
    }
    public static void registerMenu()  {
        boolean condition = true;
        while (condition) {
            try {
                System.out.println("please enter 0 for register cinema acc and 1 for costumer and 2 for exit");
                int operator = input.nextInt();
                if (operator < 0 || operator > 4) {
                    throw new OutOfRangeInputException("please enter something in range!");
                }
                switch (operator) {
                    case 0:
                        System.out.println("please enter name of the cinema");
                        String cinemaName = input.next();
                        System.out.println("please enter username of the cinema");
                        String cinemaUsername = input.next();
                        System.out.println("please enter password of the cinema");
                        String cinemaPassword = input.next();
                        cinemaService.register(cinemaName, cinemaUsername, cinemaPassword);
                        break;
                    case 1:
                        System.out.println("please enter username of the cinema");
                        String purchaserUsername = input.next();
                        System.out.println("please enter password of the cinema");
                        String purchaserPassword = input.next();
                        purchaserServices.register(purchaserUsername, purchaserPassword);
                        break;
                    case 2:
                        condition = false;
                }
            }catch (OutOfRangeInputException e){
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                System.out.println("please enter a number!");
                input.nextLine();
            }
            }
    }

}