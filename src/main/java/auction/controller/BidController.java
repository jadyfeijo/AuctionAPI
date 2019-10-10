package auction.controller;

import auction.domain.Bid;
import auction.service.BidService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/Bidds")
public class BidController{


    private final static BidService service = new BidService();

        @GetMapping("/{auctionId}/queue")
        public List<Bid> getQueue(@PathVariable String auctionId){
            return service.getByAuction(auctionId);
        }

        @PutMapping("{auctionId}/confirm/{bidderId}")
        public Bid confirm(@PathVariable String auctionId, @PathVariable String bidderId){
            return service.confirm(auctionId,bidderId);
        }

        @PutMapping("{auctionId}/recuse/{bidderId}")
        public Bid recuse (@PathVariable String auctionId, @PathVariable String bidderId){
            return service.recuse(auctionId,bidderId);
        }

}

