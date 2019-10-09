package auction.service;

import auction.domain.Bid;
import auction.repository.BidRepository;

import java.util.List;

public class BidService {

    private final BidRepository repo = new BidRepository();

    public Bid get(String id){
        return repo.get(id);
    }

    public List<Bid> getAll(){
        return repo.getAll();
    }
}
