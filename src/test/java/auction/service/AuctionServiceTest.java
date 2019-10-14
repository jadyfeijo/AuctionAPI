package auction.service;

import auction.domain.Auction;
import auction.domain.Bid;
import auction.domain.enums.Status;
import auction.repository.AuctionRepository;
import auction.repository.BidRepository;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.validation.constraints.AssertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


public class AuctionServiceTest {


    @Test(expected = RuntimeException.class)
    public void addBid_willPass_whenBidValueIsLessThanAuctioHighestOffer() {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        BidRepository bidRepository = mock(BidRepository.class);
        AuctionService service = new AuctionService(auctionRepository, bidRepository);

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
        BidRepository bidRepository = mock(BidRepository.class);
        AuctionService service = new AuctionService(auctionRepository, bidRepository);

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
        BidRepository bidRepository = mock(BidRepository.class);
        AuctionService service = new AuctionService(auctionRepository, bidRepository);

        service.createAuction("item");

        verify(auctionRepository, times(1)).save(any(Auction.class));

    }

    @Test
    public void generateAuction_shouldPass_whenReturnedAuctionHasOpenStatusAndItemEqualsItemParameter() {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        BidRepository bidRepository = mock(BidRepository.class);
        AuctionService service = new AuctionService(auctionRepository, bidRepository);

        Auction auction = service.generateAuction("ITEM NAME");

        assertEquals(auction.getStatus(), Status.OPEN);
        assertEquals(auction.getItem(), "ITEM NAME");
        assertEquals(auction.getHighestOffer(), 0.0);

    }

    @Test
    public void getByStatus_shouldPass_ifStatusParameterIsOpenAndTheAuctionInicialDateplus15minIsAfterNowDateThenRepoShouldSaveAnAuction() throws ParseException {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        BidRepository bidRepository = mock(BidRepository.class);
        AuctionService auctionService = new AuctionService(auctionRepository, bidRepository);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Status status = Status.OPEN;

        List<Auction> auctions=new ArrayList<>();

        auctions.add(new Auction("000","item1",200,Status.OPEN,sdf.parse("14/10/2019 17:30")));

        when(auctionRepository.getByStatus(status)).thenReturn(auctions);

        auctionService.getByStatus(status);

        then(auctionRepository).should().getByStatus(status);

        then(auctionRepository).should(times(1)).save(any(Auction.class));
    }

    @Test
    public void getByStatus_shouldPass_ifStatusParameterIsOpenAndTheAuctionInicialDateIsAfterNowThenRepoShoulNotSave(){
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        BidRepository bidRepository = mock(BidRepository.class);
        AuctionService auctionService = new AuctionService(auctionRepository, bidRepository);

        Status status = Status.OPEN;

        List<Auction> auctions=new ArrayList<>();

        auctions.add(new Auction("000","item1",200,Status.OPEN,new Date()));

        when(auctionRepository.getByStatus(status)).thenReturn(auctions);

        auctionService.getByStatus(status);

        then(auctionRepository).should().getByStatus(status);

        then(auctionRepository).should(times(0)).save(any(Auction.class));
    }


}
