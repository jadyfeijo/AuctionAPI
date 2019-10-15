package auction.controller;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("api/Bidds")
public class BidController {

    @Autowired
    private BidService service;

    @GetMapping("/{auctionId}/queue")
    public List<Bid> getQueue(@PathVariable String auctionId) {
        return service.getByAuction(auctionId);
    }

    @PutMapping("{auctionId}/confirm/{bidderId}")
    public Bid confirm(@PathVariable String auctionId, @PathVariable String bidderId) {
        return service.confirm(auctionId, bidderId);
    }

    @PutMapping("{auctionId}/recuse/{bidderId}")
    public Bid recuse(@PathVariable String auctionId, @PathVariable String bidderId) {
        return service.recuse(auctionId, bidderId);
    }

    @PostMapping("/{auctionId}/newBid")
    public Auction newBid(@PathVariable String auctionId, @RequestBody BidRequest bid) {

        Bid newBid = new Bid(bid.bidderId, bid.name, auctionId, bid.bid);

        return service.addBid(newBid);
    }

}

