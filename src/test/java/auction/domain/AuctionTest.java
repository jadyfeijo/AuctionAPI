package auction.domain;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AuctionTest {

    @Test
    public void isOpen_shouldPass_WhenAuctionIsOpenThenNowDateIsLessThanAuctionInitialDatePlus15min() {

        Auction auction = new Auction();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,-5);

        auction.setInicialDate(calendar.getTime());

        assertEquals(auction.isOpen(),true);
    }

    @Test
    public void isOpen_shouldPass_WhenAuctionIsNotThenOpenNowDateIsMoreThanAuctionInitialDatePlus15min() {

        Auction auction = new Auction();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,-20);

        auction.setInicialDate(calendar.getTime());

        assertEquals(auction.isOpen(),false);
    }
}