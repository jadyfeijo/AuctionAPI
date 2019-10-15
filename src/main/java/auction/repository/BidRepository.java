package auction.repository;

import auction.domain.Bid;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class BidRepository {

    private List<Bid> bidds = new ArrayList<>();

    public BidRepository(){
        bidds.add(new Bid ("10101","111","Joao","0003",1000,true));

    }

    public Bid get(String bidId){
        for(Bid bid: bidds)
        {
            if(bid.getId()==bidId)
                return  bid;
        }

        return null;
    }

    public Bid save(Bid bid){
        if(bid.getId()!=null){
            edit(bid);
            return bid;
        }

        bid.setId(String.valueOf(Math.random()));
        bid.setPossibleBuyer(true);
        bidds.add(bid);

        return bid;

    }

    public void edit(Bid bid){
        Bid bidToEdit = get(bid.getId());

        bidToEdit.setAuctionId(bid.getAuctionId());
        bidToEdit.setBid(bid.getBid());
        bidToEdit.setBidderId(bid.getBidderId());
        bidToEdit.setPossibleBuyer(bid.isPossibleBuyer());
        bidToEdit.setBidderName(bid.getBidderName());

    }

    public List<Bid> getAll() {
        return bidds;
    }

    public List<Bid> getByAuction(String auctionId) {

        List<Bid> biddsAuction = new ArrayList<>();

        for(Bid bid: bidds){
            if(bid.getAuctionId().equals(auctionId)&& bid.isPossibleBuyer())
                biddsAuction.add(bid);
        }

        return biddsAuction;
    }

    public Bid getHighestOffer(String auctionId){

        List<Bid> biddsAuction = getByAuction(auctionId);

        Bid highestBid = new Bid();

         for (Bid bid : biddsAuction) {
                if (bid.getBid() > highestBid.getBid() && bid.isPossibleBuyer()) {
                    highestBid = bid;
                }
            }
            return highestBid;

    }

    public Bid getLastBid(String auctionId, String bidderId) {

      Bid lastBid=new Bid();

        for(Bid bid: bidds){
            if(bid.getBidderId().equals(bidderId) && bid.getAuctionId().equals(auctionId) && bid.isPossibleBuyer() )
                lastBid=bid;
        }
        return lastBid;
    }
}
