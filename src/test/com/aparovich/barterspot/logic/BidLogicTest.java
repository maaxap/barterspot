package com.aparovich.barterspot.logic;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.dao.factory.DaoFactory;
import com.aparovich.barterspot.dao.impl.BidDao;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.model.util.ModelType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest({DaoFactory.class})
public class BidLogicTest {

    private static final Bid ANY_BID = new Bid();

    private static final List<Bid> NOT_EMPTY_BIDS_LIST = new ArrayList<>();

    @Mock
    private BidDao bidDao;

    @Before
    public void setUp() throws Exception {
        /*
        We need to set ANY_BID fields id and lot in order to prevent
        comparison ANY_BID and any new empty Bid.class instance. Although,
        in some occasions we need to return any Lot instance.
        */
        ANY_BID.setId(1L);
        ANY_BID.setLot(new Lot());

        NOT_EMPTY_BIDS_LIST.add(ANY_BID);

        PowerMockito.mockStatic(DaoFactory.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
    }

    @Test
    public void findByIdResultTest() throws Exception {
        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(ANY_BID);

        assertEquals(ANY_BID, BidLogic.findById(anyLong()));
    }

    @Test
    public void findByIdDaoMethodCallTest() throws Exception {
        BidLogic.findById(anyLong());

        Mockito.verify(bidDao, Mockito.times(1)).findByPk(anyLong());
    }

    @Test
    public void findByIdThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: BidDao findByPk() method produced DaoException."))
                .when(bidDao)
                .findByPk(anyLong());

        assertNull(BidLogic.findById(anyLong()));
    }

    @Test
    public void findAllResultTest() throws Exception {
        Mockito.when(bidDao.findAll()).thenReturn(NOT_EMPTY_BIDS_LIST);

        assertEquals(NOT_EMPTY_BIDS_LIST, BidLogic.findAll());
    }

    @Test
    public void findAllDaoMethodCallTest() throws Exception {
        BidLogic.findAll();

        Mockito.verify(bidDao, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: BidDao findAll() method produced DaoException."))
                .when(bidDao)
                .findAll();

        assertTrue(BidLogic.findAll().isEmpty());
    }

    @Test
    public void findByUserResultTest() throws Exception {
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(NOT_EMPTY_BIDS_LIST);

        assertEquals(NOT_EMPTY_BIDS_LIST, BidLogic.findByUser(any(User.class)));
    }

    @Test
    public void findByUserDaoMethodCallTest() throws Exception {
        BidLogic.findByUser(any(User.class));

        Mockito.verify(bidDao, Mockito.times(1)).findByUser(any(User.class));
    }

    @Test
    public void findByUserThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: BidDao findByUser() method produced DaoException."))
                .when(bidDao)
                .findByUser(any(User.class));

        assertTrue(BidLogic.findByUser(any(User.class)).isEmpty());
    }

    @Test
    public void findByLotResultTest() throws Exception {
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(NOT_EMPTY_BIDS_LIST);

        assertEquals(NOT_EMPTY_BIDS_LIST, BidLogic.findByLot(any(Lot.class)));
    }

    @Test
    public void findByLotDaoMethodCallTest() throws Exception {
        BidLogic.findByLot(any(Lot.class));

        Mockito.verify(bidDao, Mockito.times(1)).findByLot(any(Lot.class));
    }

    @Test
    public void findByLotThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: BidDao findByLot() method produced DaoException."))
                .when(bidDao)
                .findByLot(any(Lot.class));

        assertTrue(BidLogic.findByLot(any(Lot.class)).isEmpty());
    }

    @Test
    public void createFirstResultTest() throws Exception {
        List<Bid> emptyBidsList = Mockito.mock(List.class);
        Mockito.when(emptyBidsList.isEmpty()).thenReturn(true);

        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(emptyBidsList);
        Mockito.when(bidDao.create(any(Bid.class))).thenReturn(ANY_BID);

        assertEquals(ANY_BID, BidLogic.create(ANY_BID));
    }

    @Test
    public void createNotFirstResultTest() throws Exception {
        BigDecimal mockedValue = Mockito.mock(BigDecimal.class);
        Mockito.when(mockedValue.compareTo(any(BigDecimal.class))).thenReturn(-1);

        Bid mockedBid = Mockito.mock(Bid.class);
        Mockito.when(mockedBid.getBid()).thenReturn(mockedValue);

        Iterator<Bid> mockedIterator = Mockito.mock(Iterator.class);
        Mockito.when(mockedIterator.next()).thenReturn(mockedBid);

        List<Bid> emptyBidsList = Mockito.mock(List.class);
        Mockito.when(emptyBidsList.isEmpty()).thenReturn(false);
        Mockito.when(emptyBidsList.iterator()).thenReturn(mockedIterator);

        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(emptyBidsList);
        Mockito.when(bidDao.create(any(Bid.class))).thenReturn(ANY_BID);

        assertEquals(ANY_BID, BidLogic.create(ANY_BID));
    }

    @Test
    public void createFirstDaoMethodCallTest() throws Exception {
        List<Bid> emptyBidsList = Mockito.mock(List.class);
        Mockito.when(emptyBidsList.isEmpty()).thenReturn(true);

        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(emptyBidsList);
        Mockito.when(bidDao.create(any(Bid.class))).thenReturn(ANY_BID);

        BidLogic.create(ANY_BID);

        Mockito.verify(bidDao, Mockito.times(1)).findByLot(any(Lot.class));
        Mockito.verify(bidDao, Mockito.times(1)).create(any(Bid.class));
    }

    @Test
    public void createNotFirstDaoMethodCallTest() throws Exception {
        BigDecimal mockedValue = Mockito.mock(BigDecimal.class);
        Mockito.when(mockedValue.compareTo(any(BigDecimal.class))).thenReturn(-1);

        Bid mockedBid = Mockito.mock(Bid.class);
        Mockito.when(mockedBid.getBid()).thenReturn(mockedValue);

        Iterator<Bid> mockedIterator = Mockito.mock(Iterator.class);
        Mockito.when(mockedIterator.next()).thenReturn(mockedBid);

        List<Bid> emptyBidsList = Mockito.mock(List.class);
        Mockito.when(emptyBidsList.isEmpty()).thenReturn(false);
        Mockito.when(emptyBidsList.iterator()).thenReturn(mockedIterator);

        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(emptyBidsList);
        Mockito.when(bidDao.create(any(Bid.class))).thenReturn(ANY_BID);

        BidLogic.create(ANY_BID);

        Mockito.verify(bidDao, Mockito.times(1)).findByLot(any(Lot.class));
        Mockito.verify(bidDao, Mockito.times(1)).create(any(Bid.class));
    }

    @Test
    public void createThrowException() throws Exception {
        Mockito.when(bidDao.create(ANY_BID)).thenThrow(new DaoException("Test: BidDao create() method produced DaoException."));

        assertNull(BidLogic.create(ANY_BID));
    }

    @Test
    public void updateDaoMethodCallTest() throws Exception {
        BidLogic.update(any(Bid.class));

        Mockito.verify(bidDao, Mockito.times(1)).update(any(Bid.class));
    }

    @Test
    public void updateThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: BidDao update() method produced DaoException."))
                .when(bidDao)
                .update(any(Bid.class));

        BidLogic.update(any(Bid.class));
    }

    @Test
    public void deleteDaoMethodCallTest() throws Exception {
        BidLogic.delete(ANY_BID);

        Mockito.verify(bidDao, Mockito.times(1)).findByPk(anyLong());
        Mockito.verify(bidDao, Mockito.times(1)).update(any(Bid.class));
    }

    @Test
    public void deleteThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: BidDao update() method produced DaoException."))
                .when(bidDao)
                .update(any(Bid.class));

        BidLogic.delete(ANY_BID);
    }

    @Test
    public void deleteAllDaoMethodCallTest() throws Exception {
        BidLogic.deleteAll(NOT_EMPTY_BIDS_LIST);

        Mockito.verify(bidDao, Mockito.times(NOT_EMPTY_BIDS_LIST.size())).findByPk(anyLong());
        Mockito.verify(bidDao, Mockito.times(NOT_EMPTY_BIDS_LIST.size())).update(any(Bid.class));
    }

    @Test
    public void deleteAllThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: BidDao update() method produced DaoException."))
                .when(bidDao)
                .update(any(Bid.class));

        BidLogic.deleteAll(NOT_EMPTY_BIDS_LIST);
    }
}