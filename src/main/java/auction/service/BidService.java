package auction.service;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.domain.enums.Status;
import auction.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {
    private BidRepository repo;
    private AuctionService auctionService;

    @Autowired
    public BidService(BidRepository repo, AuctionService auctionService) {
        this.repo = repo;
        this.auctionService = auctionService;
    }

    public Bid get(String id) {
        return repo.get(id);
    }

    public List<Bid> getAll() {
        return repo.getAll();
    }

    public Bid create(Bid bid) {
        return repo.save(bid);
    }

    public List<Bid> getByAuction(String auctionId) {
        return repo.getByAuction(auctionId);
    }

    public Auction addBid(Bid newBid) {
        String auctionId = newBid.getAuctionId();
        Auction auction = auctionService.get(auctionId);

        if (auction.isOpen()) {

            if (auction.getHighestOffer() < newBid.getBid()) {
                repo.save(newBid);
                auction.setHighestOffer(newBid.getBid());

                return auctionService.save(auction);
            } else
                throw new RuntimeException("Your bid value is less than the highest Offer in this Auction");
        } else {
            auction.setStatus(Status.CLOSED);
            auctionService.save(auction);
            throw new RuntimeException("This Auction cant receive any Bid");
        }
    }

    public Bid confirm(String auctionId, String bidderId) {

        Bid highestBid = getHighestOffer(auctionId);
        Auction auction = auctionService.get(auctionId);

    if(auction.getStatus()!=Status.CONFIRMED)
        if (!auction.isOpen()) {
            if (highestBid.getBidderId().equals(bidderId)) {
                highestBid.setBuyer(true);
                auction.setStatus(Status.CONFIRMED);
                auctionService.save(auction);
                return repo.save(highestBid);
            } else {
                throw new RuntimeException("Your bid is not the highest");
            }
        } else
            throw new RuntimeException("This Auction is not closed yet");
        else
            throw new RuntimeException("This Auction is already confirmed");

    }

    public Bid recuse(String auctionId, String bidderId) {

        Bid recusedBid = getLastBid(auctionId, bidderId);

        if(auctionService.get(auctionId).getStatus()!=Status.CONFIRMED) {

            recusedBid.setPossibleBuyer(false);
            repo.save(recusedBid);

            Bid newHighestBid = repo.getHighestOffer(auctionId);
            double highestOffer = newHighestBid.getBid();
            auctionService.changeHighestOffer(auctionId, highestOffer);

            return recusedBid;
        }

        else
            throw new RuntimeException("This Auction is already confirmed");

    }

    public Bid getLastBid(String auctionId, String bidderId) {
        Bid bid = repo.getLastBid(auctionId, bidderId);

        if (bid.getId() != null)
            return bid;

        else
            throw new RuntimeException("This Auction/Bidder has no Bid yet");

    }

    public Bid getHighestOffer(String auctionId) {
        Bid bid = repo.getHighestOffer(auctionId);

        if (bid.getId() != null)
            return bid;

        else
            throw new RuntimeException("This Auction has no Bid");

    }
}
