package auction.service;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.repository.BidRepository;

import java.util.List;

public class BidService {

    private final static  BidRepository repo = new BidRepository();
    private final static AuctionService auctionService = new AuctionService();

    public Bid get(String id){
        return repo.get(id);
    }

    public List<Bid> getAll(){
        return repo.getAll();
    }

    public Bid addBid (Bid bid){
        return repo.save(bid);
    }

    public List<Bid> getByAuction(String auctionId) {
        return repo.getByAuction(auctionId);
    }

    public Bid confirm(String auctionId,String bidderId) {

        Bid highestBid = repo.getHighestOffer(auctionId);

        if(highestBid.getBidderId().equals(bidderId)){
            highestBid.setBuyer(true);
            return repo.save(highestBid);
        }
        else{
            throw new RuntimeException("Your bid is not the highest");
        }




    }

    public Bid recuse(String auctionId, String bidderId) {

        Bid recusedBid = getLastBid(auctionId,bidderId);

        recusedBid.setPossibleBuyer(false);
        repo.save(recusedBid);

        Auction auction = auctionService.get(auctionId);

        if(auction.getHighestOffer()==recusedBid.getBid()){
            auction.setHighestOffer(repo.getHighestOffer(auction.getId()).getBid());
            auctionService.save(auction);
        }

        return recusedBid;
    }

    public Bid getLastBid(String auctionId,String bidderId){
        Bid bid = repo.getLastBid(auctionId,bidderId);

        if(bid.getId()!=null)
            return bid;

        else
            throw new RuntimeException("This Auction/Bidder has no Bid yet");

    }

    public  Bid getHighestOffer(String auctionId){
        Bid bid = repo.getHighestOffer(auctionId);

        if(bid.getId()!=null)
            return bid;

        else
            throw new RuntimeException("This Auction has no Bid yet");

    }
}
