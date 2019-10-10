package auction.service;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.domain.enums.Status;
import auction.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository repo;
    @Autowired
    private BidService bidService;


    public Auction get(String id){
        return repo.get(id);
    }

    public List<Auction> getAll(){
        return repo.getAll();
    }

    public List<Auction> getByStatus(Status status) {

        List<Auction> auctions = repo.getByStatus(status);

        if(status==Status.OPEN){

            List<Auction> openAuctions = new ArrayList<>();
            for(Auction auction:auctions){
                if(isOpen(auction.getId()))
                    openAuctions.add(auction);

            }
            return openAuctions;
        }
        return repo.getByStatus(status);
    }

    public Auction createAuction(String item) {
        Auction auction = new Auction(null,item,Status.OPEN);
        return repo.save(auction);
    }

    public Auction addBid(Bid newBid) {
        Auction auction = get(newBid.getAuctionId());

       if(isOpen(auction.getId())) {

           if (auction.getHighestOffer() < newBid.getBid()) {
               bidService.addBid(newBid);
               auction.setHighestOffer(newBid.getBid());

               return save(auction);
           } else
               throw new RuntimeException("Your bid value is less than the highest Offer in this Auction");
       }

       else {
           auction.setStatus(Status.CLOSED);
           save(auction);
           throw new RuntimeException("This Auction cant receive any Bid");
       }
    }

    public Auction save(Auction auction){
        return repo.save(auction);
    }

    public boolean isOpen(String auctionId){
        Auction auction = get(auctionId);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(auction.getInicialDate());

        calendar.add(Calendar.MINUTE,15);
        if(calendar.getTime().after(new Date())) {
            return true;
        }
        else
        {
            auction.setStatus(Status.CLOSED);
            return false;
        }
    }
}
