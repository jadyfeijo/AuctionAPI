package auction.service;

import auction.domain.Auction;
import auction.domain.enums.Status;
import auction.repository.AuctionRepository;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


public class AuctionServiceTest {


    @Test
    public void createAuction_shouldPass_whenReturnedAuctionHasOpenStatus() {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        AuctionService service = new AuctionService(auctionRepository);

        service.createAuction("item");

        verify(auctionRepository, times(1)).save(any(Auction.class));

    }

    @Test
    public void generateAuction_shouldPass_whenReturnedAuctionHasOpenStatusAndItemEqualsItemParameter() {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        AuctionService service = new AuctionService(auctionRepository);

        Auction auction = service.generateAuction("ITEM NAME");

        assertEquals(auction.getStatus(), Status.OPEN);
        assertEquals(auction.getItem(), "ITEM NAME");
        assertEquals(auction.getHighestOffer(), 0.0);

    }

    @Test
    public void getByStatus_shouldPass_ifStatusParameterIsOpenAndTheAuctionInicialDateplus15minIsAfterNowDateThenRepoShouldSaveAnAuction() throws ParseException {
        AuctionRepository auctionRepository = mock(AuctionRepository.class);
        AuctionService auctionService = new AuctionService(auctionRepository);

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
        AuctionService auctionService = new AuctionService(auctionRepository);

        Status status = Status.OPEN;

        List<Auction> auctions=new ArrayList<>();

        auctions.add(new Auction("000","item1",200,Status.OPEN,new Date()));

        when(auctionRepository.getByStatus(status)).thenReturn(auctions);

        auctionService.getByStatus(status);

        then(auctionRepository).should().getByStatus(status);

        then(auctionRepository).should(times(0)).save(any(Auction.class));
    }


}
