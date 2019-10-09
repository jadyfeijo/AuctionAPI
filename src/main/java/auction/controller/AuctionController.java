package auction.controller;

import auction.domain.Auction;
import auction.domain.enums.Status;
import auction.service.AuctionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/Auctions")
class AuctionController {

    private final AuctionService service = new AuctionService();

    @GetMapping ("/get/{status}")
    public List<Auction> getAuctions(@PathVariable Status status){
        return service.getByStatus(status);
    }

    @PutMapping("/create")
    public Auction createAuction(@RequestBody AuctionRequest auction){
        return service.createAuction(auction.item);
    }

}

class AuctionRequest{
    public String item;

}