package auction.service;

import auction.domain.Bid;
import auction.repository.BidRepository;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BidServiceTest {

    @Test
    public void confirm_shouldPass_whenBidderIdIsEqualHighestOfferBidderId() {

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String auctionId = "0003";
        String bidderId = "111";

        Bid bid = mock(Bid.class);
        when(bid.getBidderId()).thenReturn("111");
        when(bidRepository.getHighestOffer("0003")).thenReturn(bid);

        service.confirm(auctionId,bidderId);

        verify(bid).setBuyer(true);

    }
    @Test (expected = RuntimeException.class)
    public void confirm_shouldPass_whenBidderIdIsNotEqualHighestOfferBidderId(){

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String auctionId = "0003";
        String bidderId = "222";

        Bid bid = mock(Bid.class);
        when(bid.getBidderId()).thenReturn("111");
        when(bidRepository.getHighestOffer("0003")).thenReturn(bid);

        service.confirm(auctionId,bidderId);
    }
}