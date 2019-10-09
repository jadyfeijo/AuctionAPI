package auction.service;

import auction.domain.Auction;
import auction.domain.enums.Status;
import auction.repository.AuctionRepository;

import java.util.List;

public class AuctionService {

    private final AuctionRepository repo = new AuctionRepository();

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
        return repo.create(item,Status.OPEN);
    }
}
