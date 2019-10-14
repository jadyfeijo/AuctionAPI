package auction.service;

import auction.domain.Auction;
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

    @Test
    public void recuse_shouldPass_whenIsPossibleBuyerWasSettedToFalseAndAuctionHighestOfferWasChanged(){

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String bidderId = "111";
        String auctionId = "0003";

        Bid bid = mock(Bid.class);
        when(bid.getBid()).thenReturn(1500.0);
        when(bidRepository.getLastBid(auctionId, bidderId)).thenReturn(bid);

        Bid newHighestBid = mock(Bid.class);
        when(newHighestBid.getBid()).thenReturn(1000.0);
        when(bidRepository.getHighestOffer(auctionId)).thenReturn(newHighestBid);

        service.recuse(auctionId,bidderId);
        verify(bid).setPossibleBuyer(false);
        verify(auctionService).changeHighestOffer(auctionId,bid.getBid(),newHighestBid.getBid());
        
    }
}