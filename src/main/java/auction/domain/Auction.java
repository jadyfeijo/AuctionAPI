package auction.domain;

import auction.domain.enums.Status;

import java.util.Calendar;
import java.util.Date;


public class Auction {

    private String id;
    private String item;
    private double highestOffer;
    private Status status;
    private Date inicialDate;

    public Auction(String id, String item, Status status) {
        this.id = id;
        this.item = item;
        this.status = status;
    }

    public Auction(String id, String item, double highestOffer, Status status) {
        this.id = id;
        this.item = item;
        this.highestOffer = highestOffer;
        this.status = status;
        this.inicialDate = new Date();
    }

    public Auction(String id, String item, double highestOffer, Status status, Date inicialDate) {
        this.id = id;
        this.item = item;
        this.highestOffer = highestOffer;
        this.status = status;
        this.inicialDate = inicialDate;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getHighestOffer() {
        return highestOffer;
    }

    public void setHighestOffer(double highestOffer) {
        this.highestOffer = highestOffer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getInicialDate() {
        return inicialDate;
    }

    public void setInicialDate(Date inicialDate) {
        this.inicialDate = inicialDate;
    }

    public boolean isOpen() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getInicialDate());

        calendar.add(Calendar.MINUTE, 15);
        if (calendar.getTime().after(new Date())) {
            return true;
        } else {
            this.setStatus(Status.CLOSED);
            return false;
        }
    }
}
