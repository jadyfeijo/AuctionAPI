package auction.service;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.repository.AuctionRepository;
import org.junit.Test;

import static org.mockito.Mockito.*;


public class AuctionServiceTest {


    @Test(expected = RuntimeException.class)
    public void addBid_willPass_whenBidValueIsLessThanAuctioHighestOffer() {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        BidService bidService = mock(BidService.class);
        AuctionService service = new AuctionService(auctionRepository, bidService);

        Bid newBid = new Bid();
        newBid.setAuctionId("0001");
        newBid.setBid(0.0);

        Auction auction = mock(Auction.class);
        when(auction.isOpen()).thenReturn(true);
        when(auctionRepository.get("0001")).thenReturn(auction);
        when(auction.getHighestOffer()).thenReturn(2.0);

        service.addBid(newBid);

    }

    @Test
    public void addBid_willPass_whenAuctionHighestOfferrReceiveBidValue() {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        BidService bidService = mock(BidService.class);
        AuctionService service = new AuctionService(auctionRepository, bidService);

        Bid newBid = new Bid();
        newBid.setAuctionId("0001");
        newBid.setBid(3.0);

        Auction auction = mock(Auction.class);
        when(auction.isOpen()).thenReturn(true);
        when(auctionRepository.get("0001")).thenReturn(auction);
        when(auction.getHighestOffer()).thenReturn(2.0);

        service.addBid(newBid);

        verify(auction).setHighestOffer(3.0);

    }


}
