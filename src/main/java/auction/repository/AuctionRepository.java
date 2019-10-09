package auction.repository;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.domain.enums.Status;

import java.util.ArrayList;
import java.util.List;

public class AuctionRepository {

    private List<Auction> auctions = new ArrayList<>();

    public AuctionRepository() {
        auctions.add(new Auction("0001", "Davi", Status.OPEN));
        auctions.add(new Auction("0002", "Monalisa",Status.OPEN));
        auctions.add(new Auction("0003", "Iphone",1000,Status.IN_PROGRESS));

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

    public void create(String item, Status status) {
        Auction auction  = new Auction(String.valueOf(Math.random()),item,status);

        save(auction);
    }

    public Auction save(Auction auction){
        if(auction.getId()!=null){
            edit(auction);
            return  auction;
        }
        auctions.add(auction);
        return auction;

    }

    public void edit(Auction auction){
        Auction auctionToBeEdited = get(auction.getId());

        auctionToBeEdited.setStatus(auction.getStatus());
        auctionToBeEdited.setHighestOffer(auction.getHighestOffer());
        auctionToBeEdited.setItem(auction.getId());
    }
}
