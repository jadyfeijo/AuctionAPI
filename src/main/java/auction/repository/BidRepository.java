package auction.repository;

import auction.domain.Bid;

import java.util.ArrayList;
import java.util.List;

public class BidRepository {

    private List<Bid> bidds = new ArrayList<>();

    public BidRepository(){
        bidds.add(new Bid ("111","Joao","0003",1000));
    }

    public Bid get(String bidId){
        for(Bid bid: bidds)
        {
            if(bid.getId()==bidId)
                return  bid;
        }

        return null;
    }

    public Bid addBid(Bid bid){
        bid.setId(String.valueOf(Math.random()));
        bidds.add(bid);
        return bid;
    }

    public List<Bid> getAll() {
        return bidds;
    }

    public List<Bid> getByAuction(String auctionId) {

        List<Bid> biddsAuction = new ArrayList<>();

        for(Bid bid: bidds){
            if(bid.getAuctionId().equals(auctionId))
                biddsAuction.add(bid);
        }

        return biddsAuction;
    }
}
