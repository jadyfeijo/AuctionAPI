package auction.domain;

public class Bid {
    private String id;
    private String bidderId;
    private String bidderName;
    private String auctionId;
    private double bid;
    private boolean possibleBuyer;
    private boolean buyer;

    public Bid() {
    }

    public Bid(String bidderId, String bidderName, String auctionId, double bid) {
        this.bidderId = bidderId;
        this.bidderName = bidderName;
        this.auctionId = auctionId;
        this.bid = bid;
    }

    public Bid(String id, String bidderId, String bidderName, String auctionId, double bid,boolean possibleBuyer) {
        this.id = id;
        this.bidderId = bidderId;
        this.bidderName = bidderName;
        this.auctionId = auctionId;
        this.bid = bid;
        this.possibleBuyer =possibleBuyer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public boolean isPossibleBuyer() {
        return possibleBuyer;
    }

    public void setPossibleBuyer(boolean possibleBuyer) {
        this.possibleBuyer = possibleBuyer;
    }

    public boolean isBuyer() {
        return buyer;
    }

    public void setBuyer(boolean buyer) {
        this.buyer = buyer;
    }
}
