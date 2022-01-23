package Entities;

import java.util.Date;

public class Ticket {
    private Integer id;
private String filmName;
private String cinemaName;
private Date dateOfPerformance;
private int quantity;
private int price;

    public Ticket(String filmName, String cinemaName, Date dateOfPerformance, int quantity, int price) {
        this.filmName = filmName;
        this.cinemaName = cinemaName;
        this.dateOfPerformance = dateOfPerformance;
        this.quantity = quantity;
        this.price = price;
    }
    public Ticket(Integer id, String filmName, String cinemaName, Date dateOfPerformance, int quantity, int price) {
        this.id = id;
        this.filmName = filmName;
        this.cinemaName = cinemaName;
        this.dateOfPerformance = dateOfPerformance;
        this.quantity = quantity;
        this.price = price;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateOfPerformance() {
        return dateOfPerformance;
    }

    public void setDateOfPerformance(Date dateOfPerformance) {
        this.dateOfPerformance = dateOfPerformance;
    }
}
