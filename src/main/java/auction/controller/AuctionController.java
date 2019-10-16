package auction.controller;

import auction.domain.Auction;
import auction.domain.enums.Status;
import auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("api/Auctions")
class AuctionController {

    @Autowired
    private  AuctionService auctionService;

    @GetMapping ("/{status}")
    public List<Auction> getAuctions(@PathVariable Status status){
        return auctionService.getByStatus(status);
    }

    @PostMapping("/create")
    public Auction createAuction(@RequestBody AuctionRequest auction){
        return auctionService.createAuction(auction.item);
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