package auction.service;

import auction.domain.Bid;
import auction.repository.BidRepository;

import java.util.List;

public class BidService {

    private final static  BidRepository repo = new BidRepository();

    public Bid get(String id){
        return repo.get(id);
    }

    public List<Bid> getAll(){
        return repo.getAll();
    }

    public Bid addBid (Bid bid){
        return repo.addBid(bid);
    }

    public List<Bid> getByAuction(String auctionId) {
        return repo.getByAuction(auctionId);
    }
}
