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

    private AuctionRepository repo;
    private BidService bidService;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository, BidService bidService) {
        this.repo = auctionRepository;
        this.bidService = bidService;
    }

    public Auction get(String id) {
        return repo.get(id);
    }

    public List<Auction> getAll() {
        return repo.getAll();
    }

    public List<Auction> getByStatus(Status status) {

        List<Auction> auctions = repo.getByStatus(status);

        if (status == Status.OPEN) {

            List<Auction> openAuctions = new ArrayList<>();
            for (Auction auction : auctions) {
                if (auction.isOpen()){
                    if(auction.getStatus()!=Status.OPEN)
                        auction.setStatus(Status.OPEN);
                        auction=save(auction);
                    }

                    openAuctions.add(auction);

            }
            return openAuctions;
        }
        return repo.getByStatus(status);
    }

    public Auction createAuction(String item) {
        Auction auction = generateAuction(item);
        return repo.save(auction);
    }

    public Auction generateAuction(String item) {
        Auction auction = new Auction();
        auction.setStatus(Status.OPEN);
        auction.setItem(item);
        return auction;
    }

    public Auction addBid(Bid newBid) {
        String auctionId = newBid.getAuctionId();
        Auction auction = repo.get(auctionId);

        if (auction.isOpen()) {

            if (auction.getHighestOffer() < newBid.getBid()) {
                bidService.addBid(newBid);
                auction.setHighestOffer(newBid.getBid());

                return save(auction);
            } else
                throw new RuntimeException("Your bid value is less than the highest Offer in this Auction");
        } else {
            auction.setStatus(Status.CLOSED);
            save(auction);
            throw new RuntimeException("This Auction cant receive any Bid");
        }
    }

    public Auction save(Auction auction) {
        return repo.save(auction);
    }

}
