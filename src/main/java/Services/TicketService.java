package Services;

import Entities.Ticket;
import MyConnection.PostgresConnection;
import REPOSITORIES.TicketRepositories;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class TicketService {
TicketRepositories ticketRepositories=new TicketRepositories(PostgresConnection.getInstance().getConnection());
    public void register(String filmName,String cinemaName, String dateOfPerformance, int number, int price) throws SQLException, ParseException {
ticketRepositories.register(filmName,cinemaName,dateOfPerformance,number,price);
    }
    public void showingTickets() throws SQLException {
        List<Ticket> tickets=ticketRepositories.showingTickets();
        for (Ticket ticket:tickets
             ) {
            System.out.println(ticket.getId()+"."+"film name:"+ticket.getFilmName()+"  cinema:"+ticket.getCinemaName()+
                    "  date of performance:"+ticket.getDateOfPerformance()+" quantity:"+ticket.getQuantity()+" price:"+ticket.getPrice()+"$");

        }
    }
    public void searchTicket(String filmName,String date) throws SQLException, ParseException {
List<Ticket> tickets=ticketRepositories.searchTicket(filmName,date);
if(tickets.size()>0) {
    for (Ticket ticket : tickets
    ) {
        System.out.println("flim name:" + ticket.getFilmName() + " cinema name:" + ticket.getCinemaName() +
                " date " + ticket.getDateOfPerformance() + " quantity" + ticket.getQuantity() + " price:" + ticket.getPrice() + "$");
    }
}
    }

}
