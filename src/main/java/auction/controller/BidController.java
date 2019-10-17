package auction.controller;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

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
    public ResponseEntity confirm(@PathVariable String auctionId, @PathVariable String bidderId) {
        try {
            Bid bid = service.confirm(auctionId, bidderId);
            return ok(bid);
        } catch (Exception e) {
              return  badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{auctionId}/recuse/{bidderId}")
    public ResponseEntity recuse(@PathVariable String auctionId, @PathVariable String bidderId) {
        try {
            Bid bid= service.recuse(auctionId, bidderId);
            return ok(bid);
        } catch (Exception e) {
            return  badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{auctionId}/newBid")
    public  ResponseEntity newBid(@PathVariable String auctionId, @RequestBody BidRequest bid) {

        Bid newBid = new Bid(bid.bidderId, bid.name, auctionId, bid.bid);

        try {
            Auction auction= service.addBid(newBid);
            return ok(bid);
        } catch (Exception e) {
            return  badRequest().body(e.getMessage());
        }
    }

}

