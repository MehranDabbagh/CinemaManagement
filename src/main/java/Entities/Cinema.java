package Entities;

public class Cinema {
    private String cinemaName;
    private String userName;
    private String password;
    private String verification;

    public Cinema(String cinemaName, String userName, String password) {
        this.cinemaName = cinemaName;
        this.userName = userName;
        this.password = password;
        this.verification="false";
    }

    public Cinema() {
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
}
