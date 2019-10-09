package auction.domain;

import auction.domain.enums.Status;

public class Auction {

    private String id;
    private String item;
    private double highestOffer;
    private Status status;

    public Auction(String id, String item, Status status) {
        this.id = id;
        this.item = item;
        this.status = status;
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
}
