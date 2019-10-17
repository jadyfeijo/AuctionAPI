package auction.service;

import auction.domain.Auction;
import auction.domain.enums.Status;
import auction.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuctionService {

    private AuctionRepository repo;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository) {
        this.repo = auctionRepository;
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
                if (!auction.isOpen()) {
                    auction.setStatus(Status.CLOSED);
                    repo.save(auction);
                }
                else
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
        auction.setInicialDate(new Date());
        auction.setItem(item);
        return auction;
    }

    public Auction save(Auction auction) {
        return repo.save(auction);
    }

    public Auction changeHighestOffer(String auctionId, double highestOffer) {

        Auction auction = repo.get(auctionId);

        auction.setHighestOffer(highestOffer);

        return repo.save(auction);

    }
}
