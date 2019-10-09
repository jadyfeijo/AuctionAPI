package auction.controller;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.domain.enums.Status;
import auction.service.AuctionService;
import auction.service.BidService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Auctions")
class AuctionController {

    private final AuctionService auctionService = new AuctionService();
    private final BidService bidService = new BidService();

    @GetMapping ("/get/{status}")
    public List<Auction> getAuctions(@PathVariable Status status){
        return auctionService.getByStatus(status);
    }

    @PutMapping("/create")
    public Auction createAuction(@RequestBody AuctionRequest auction){
        return auctionService.createAuction(auction.item);
    }

    @PostMapping("/{auctionId}/newBid")
    public Auction newBid(@PathVariable String auctionId,@RequestBody BidRequest bid){

        Bid newBid = new Bid(bid.bidderId,bid.name,auctionId,bid.bid);

        return auctionService.addBid(newBid);
    }

    @GetMapping("/{auctionId}/queue")
    public List<Bid> getQueue(@PathVariable String auctionId){
        return bidService.getByAuction(auctionId);
    }
}

class AuctionRequest{
    public String item;

}

class BidRequest{
    public String bidderId;
    public String name;
    public double bid;
}