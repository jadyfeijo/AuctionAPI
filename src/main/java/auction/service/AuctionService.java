package auction.service;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.domain.enums.Status;
import auction.repository.AuctionRepository;

import java.util.List;

public class AuctionService {

    private final AuctionRepository repo = new AuctionRepository();
    private final BidService bidService = new BidService();


    public Auction get(String id){
        return repo.get(id);
    }

    public List<Auction> getAll(){
        return repo.getAll();
    }

    public List<Auction> getByStatus(Status status) {
        return repo.getByStatus(status);
    }

    public Auction createAuction(String item) {
        Auction auction = new Auction(null,item,Status.OPEN);
        return repo.save(auction);
    }

    public Auction addBid(Bid newBid) {
        Auction auction = repo.get(newBid.getAuctionId());

        if(auction.getHighestOffer()<newBid.getBid() && auction.getStatus().equals(Status.IN_PROGRESS)){
            bidService.addBid(newBid);
            auction.setHighestOffer(newBid.getBid());

            return  repo.save(auction);
        }

        else
            throw new RuntimeException("Your bid value is less than the highest Offer in this Auction");
    }
}
