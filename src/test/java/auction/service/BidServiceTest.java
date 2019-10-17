package auction.service;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.domain.enums.Status;
import auction.repository.BidRepository;
import org.junit.Test;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class BidServiceTest {


    @Test(expected = Exception.class)
    public void addBid_willPass_whenBidValueIsLessThanAuctioHighestOffer() throws Exception {

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        Bid newBid = new Bid();
        newBid.setAuctionId("0001");
        newBid.setBid(0.0);

        Auction auction = mock(Auction.class);
        when(auction.isOpen()).thenReturn(true);
        when(auctionService.get("0001")).thenReturn(auction);
        when(auction.getHighestOffer()).thenReturn(2.0);

        service.addBid(newBid);

    }

    @Test
    public void addBid_willPass_whenAuctionHighestOfferrReceiveBidValue() throws Exception {
        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        Bid newBid = new Bid();
        newBid.setAuctionId("0001");
        newBid.setBid(3.0);

        Auction auction = mock(Auction.class);
        when(auction.isOpen()).thenReturn(true);
        when(auctionService.get("0001")).thenReturn(auction);
        when(auction.getHighestOffer()).thenReturn(2.0);

        service.addBid(newBid);

        verify(auction).setHighestOffer(3.0);

    }

    @Test(expected = Exception.class)
    public void addBid_willPass_whenAuctionIsNotOpen() throws Exception {
        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        Bid newBid = new Bid();
        newBid.setAuctionId("0001");
        newBid.setBid(3.0);

        Auction auction = mock(Auction.class);
        when(auction.isOpen()).thenReturn(false);
        when(auctionService.get("0001")).thenReturn(auction);


        service.addBid(newBid);

        then(auctionService).should().save(auction);
    }


    @Test
    public void confirm_shouldPass_whenBidderIdIsEqualHighestOfferBidderId() throws Exception {

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String auctionId = "0003";
        String bidderId = "111";

        Bid bid = mock(Bid.class);
        when(bid.getId()).thenReturn("123");
        when(bid.getBidderId()).thenReturn("111");
        when(bidRepository.getHighestOffer("0003")).thenReturn(bid);


        Auction auction = mock(Auction.class);
        when(auctionService.get(auctionId)).thenReturn(auction);


        service.confirm(auctionId,bidderId);

        verify(bid).setBuyer(true);
        verify(auctionService).save(auction);

    }
    @Test (expected = Exception.class)
    public void confirm_shouldPass_whenBidderIdIsNotEqualHighestOfferBidderId() throws Exception {

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String auctionId = "0003";
        String bidderId = "222";

        Bid bid = mock(Bid.class);
        when(bid.getId()).thenReturn("123");
        when(bid.getBidderId()).thenReturn("111");
        when(bidRepository.getHighestOffer("0003")).thenReturn(bid);

        service.confirm(auctionId,bidderId);
    }

    @Test (expected = Exception.class)
    public void confirm_shouldPass_whenAuctionIsNotClosed() throws Exception {

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String auctionId = "0003";
        String bidderId = "111";

        Bid bid = mock(Bid.class);
        when(bid.getId()).thenReturn("123");
        when(bid.getBidderId()).thenReturn("111");
        when(bidRepository.getHighestOffer("0003")).thenReturn(bid);


        Auction auction = mock(Auction.class);
        when(auction.isOpen()).thenReturn(true);
        when(auctionService.get(auctionId)).thenReturn(auction);
        
        service.confirm(auctionId,bidderId);
    }

    @Test (expected = Exception.class)
    public void confirm_shouldPass_whenAuctionStatusIsAlreadyConfirmed() throws Exception {

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String auctionId = "0003";
        String bidderId = "111";

        Bid bid = mock(Bid.class);
        when(bid.getId()).thenReturn("123");
        when(bid.getBidderId()).thenReturn("111");
        when(bidRepository.getHighestOffer("0003")).thenReturn(bid);


        Auction auction = mock(Auction.class);
        when(auction.isOpen()).thenReturn(false);
        when(auction.getStatus()).thenReturn(Status.CONFIRMED);
        when(auctionService.get(auctionId)).thenReturn(auction);

        service.confirm(auctionId,bidderId);
    }


    @Test
    public void recuse_shouldPass_whenIsPossibleBuyerWasSettedToFalseAndAuctionHighestOfferWasChanged() throws Exception {

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String bidderId = "111";
        String auctionId = "0003";

        Bid bid = mock(Bid.class);
        when(bid.getId()).thenReturn("1234");
        when(bidRepository.getLastBid(auctionId, bidderId)).thenReturn(bid);

        Bid newHighestBid = mock(Bid.class);
        when(newHighestBid.getBid()).thenReturn(1000.0);
        when(bidRepository.getHighestOffer(auctionId)).thenReturn(newHighestBid);

        Auction auction = mock(Auction.class);
        when(auction.getStatus()).thenReturn(Status.OPEN);
        when(auctionService.get(auctionId)).thenReturn(auction);

        service.recuse(auctionId,bidderId);
        verify(bid).setPossibleBuyer(false);
        verify(auctionService).changeHighestOffer(auctionId,newHighestBid.getBid());
        
    }

    @Test (expected = Exception.class)
    public void recuse_shouldPass_whenAuctionStatusIsConfirmed() throws Exception {

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String bidderId = "111";
        String auctionId = "0003";

        Bid bid = mock(Bid.class);
        when(bid.getId()).thenReturn("1234");
        when(bidRepository.getLastBid(auctionId, bidderId)).thenReturn(bid);

        Auction auction = mock(Auction.class);
        when(auction.getStatus()).thenReturn(Status.CONFIRMED);
        when(auctionService.get(auctionId)).thenReturn(auction);

        service.recuse(auctionId,bidderId);
    }
    @Test (expected = Exception.class)
    public void getLastBid_shouldPass_whenBidIdIsNull() throws Exception {
        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String bidderId = "111";
        String auctionId = "0003";


        Bid bid = mock(Bid.class);
        when(bid.getId()).thenReturn(null);
        when(service.getLastBid(auctionId,bidderId)).thenReturn(bid);

    }

    @Test (expected = Exception.class)
    public void getHighestOffer_shouldPass_whenBidIdIsNull() throws Exception {
        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String auctionId = "0003";

        Bid bid = mock(Bid.class);
        when(bid.getId()).thenReturn(null);
        when(service.getHighestOffer(auctionId)).thenReturn(bid);

    }

}