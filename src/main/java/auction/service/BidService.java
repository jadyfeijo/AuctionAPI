package auction.service;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BidService {
    @Autowired
    private BidRepository repo;
    @Autowired
    private AuctionService auctionService;

    public BidService(BidRepository repo){

    }


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
        Auction auction = auctionService.get(auctionId);



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
