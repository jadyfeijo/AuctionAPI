package auction.service;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.domain.enums.Status;
import auction.repository.AuctionRepository;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.validation.constraints.AssertTrue;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
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


    @Test
    public void createAuction_shouldPass_whenReturnedAuctionHasOpenStatus() {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        BidService bidService = mock(BidService.class);
        AuctionService service = new AuctionService(auctionRepository, bidService);

        service.createAuction("item");

        verify(auctionRepository, times(1)).save(any(Auction.class));

    }

    @Test
    public void generateAuction_shouldPass_whenReturnedAuctionHasOpenStatusAndItemEqualsItemParameter() {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        BidService bidService = mock(BidService.class);
        AuctionService service = new AuctionService(auctionRepository, bidService);

        Auction auction = service.generateAuction("ITEM NAME");

        assertEquals(auction.getStatus(), Status.OPEN);
        assertEquals(auction.getItem(), "ITEM NAME");
        assertEquals(auction.getHighestOffer(), 0.0);

    }

    @Test
    public void getByStatus_shouldPass_ifStatusParameterIsOpenThanAllAuctionReturnedHasOpenStatus() {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        BidService bidService = mock(BidService.class);
        AuctionService auctionService = new AuctionService(auctionRepository, bidService);


    }
}
