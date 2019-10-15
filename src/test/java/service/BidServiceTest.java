package service;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.repository.AuctionRepository;
import auction.repository.BidRepository;
import auction.service.AuctionService;
import auction.service.BidService;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class BidServiceTest {


    @Test(expected = RuntimeException.class)
    public void addBid_willPass_whenBidValueIsLessThanAuctioHighestOffer() {

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
    public void addBid_willPass_whenAuctionHighestOfferrReceiveBidValue() {
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

    @Test(expected = RuntimeException.class)
    public void addBid_willPass_whenAuctionIsNotOpen() {
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
    public void confirm_shouldPass_whenBidderIdIsEqualHighestOfferBidderId() {

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

    @Test (expected = RuntimeException.class)
    public void confirm_shouldPass_whenAuctionIsNotClosed(){

        AuctionService auctionService = mock(AuctionService.class);
        BidRepository bidRepository = mock(BidRepository.class);
        BidService service = new BidService(bidRepository,auctionService);

        String auctionId = "0003";
        String bidderId = "111";

        Bid bid = mock(Bid.class);
        when(bid.getBidderId()).thenReturn("111");
        when(bidRepository.getHighestOffer("0003")).thenReturn(bid);


        Auction auction = mock(Auction.class);
        when(auction.isOpen()).thenReturn(true);
        when(auctionService.get(auctionId)).thenReturn(auction);
        
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
        when(bid.getId()).thenReturn("1234");
        when(bidRepository.getLastBid(auctionId, bidderId)).thenReturn(bid);

        Bid newHighestBid = mock(Bid.class);
        when(newHighestBid.getBid()).thenReturn(1000.0);
        when(bidRepository.getHighestOffer(auctionId)).thenReturn(newHighestBid);

        service.recuse(auctionId,bidderId);
        verify(bid).setPossibleBuyer(false);
        verify(auctionService).changeHighestOffer(auctionId,newHighestBid.getBid());
        
    }
}