package auction.repository;

import auction.domain.Auction;
import auction.domain.enums.Status;

import java.util.ArrayList;
import java.util.List;

public class AuctionRepository {

    private List<Auction> auctions = new ArrayList<>();

    public AuctionRepository() {
        auctions.add(new Auction("0001", "Davi", Status.OPEN));
        auctions.add(new Auction("0002", "Monalisa",Status.OPEN));
    }

    public List<Auction> getAll(){
        return auctions;
    }

    public Auction get(String id){
        for(Auction auction:auctions){
            if(auction.getId()==id)
                return auction;
        }
        return null;
    }

    public List<Auction> getByStatus(Status status) {

        List<Auction> auctionsStatus = new ArrayList<>();

        for(Auction auction:auctions){
            if(auction.getStatus()==status)
                auctionsStatus.add(auction);
        }
        return auctionsStatus;
    }

    public Auction create(String item, Status status) {
        Auction auction  = new Auction(String.valueOf(Math.random()),item,status);

        auctions.add(auction);

        return auction;
    }
}
