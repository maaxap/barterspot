package com.aparovich.barterspot.logic;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.dao.factory.DaoFactory;
import com.aparovich.barterspot.dao.impl.BidDao;
import com.aparovich.barterspot.dao.impl.LotDao;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Category;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.model.util.LotStateType;
import com.aparovich.barterspot.model.util.ModelType;
import com.sun.deploy.security.MozillaMyKeyStore;
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
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest({DaoFactory.class})
public class LotLogicTest {

    private static final Lot ANY_LOT = new Lot();
    private static final ArrayList<Lot> NOT_EMPTY_LOTS_LIST = new ArrayList<>();

    @Mock
    private LotDao lotDao;

    @Before
    public void setUp() throws Exception {
        ANY_LOT.setId(1L);
        ANY_LOT.setCategory(new Category());
        ANY_LOT.setUser(new User());
        ANY_LOT.setDefaultPrice(BigDecimal.ONE);
        ANY_LOT.setFinishing(LocalDateTime.now().plusDays(1));
        NOT_EMPTY_LOTS_LIST.add(ANY_LOT);

        PowerMockito.mockStatic(DaoFactory.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);
    }

    @Test
    public void findByIdResultTest() throws Exception {
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(ANY_LOT);

        assertEquals(ANY_LOT, LotLogic.findById(anyLong()));
    }

    @Test
    public void findByIdDaoMethodCallTest() throws Exception {
        LotLogic.findById(anyLong());

        Mockito.verify(lotDao, Mockito.times(1)).findByPk(anyLong());
    }

    @Test
    public void findByIdThrowException() throws Exception {
        Mockito .doThrow(new DaoException("Test: LotDao findByPk() method produced DaoException."))
                .when(lotDao)
                .findByPk(anyLong());

        assertNull(LotLogic.findById(anyLong()));
    }

    @Test
    public void findAllResultTest() throws Exception {
        Mockito.when(lotDao.findAll()).thenReturn(NOT_EMPTY_LOTS_LIST);

        assertEquals(NOT_EMPTY_LOTS_LIST, LotLogic.findAll());
    }

    @Test
    public void findAllDaoMethodTest() throws Exception {
        LotLogic.findAll();

        Mockito.verify(lotDao, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: LotDao findAll() method produced DaoException."))
                .when(lotDao)
                .findAll();

        assertTrue(LotLogic.findAll().isEmpty());
    }

    @Test
    public void findByUserReadyResultTest() throws Exception {
        Mockito.when(lotDao.findReadyByUser(any(User.class))).thenReturn(NOT_EMPTY_LOTS_LIST);

        assertEquals(NOT_EMPTY_LOTS_LIST, LotLogic.findByUser(any(User.class), LotStateType.READY));
    }

    @Test
    public void findByUserReadyDaoMethodCallTest() throws Exception {
        LotLogic.findByUser(any(User.class), LotStateType.READY);

        Mockito.verify(lotDao, Mockito.times(1)).findReadyByUser(any(User.class));
    }

    @Test
    public void findByUserReadyThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: LotDao findReadyByUser() method produced DaoException."))
                .when(lotDao)
                .findReadyByUser(any(User.class));

        assertTrue(LotLogic.findByUser(any(User.class), LotStateType.READY).isEmpty());
    }

    @Test
    public void findByUserSellingOutResultTest() throws Exception {
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(NOT_EMPTY_LOTS_LIST);

        assertEquals(NOT_EMPTY_LOTS_LIST, LotLogic.findByUser(any(User.class), LotStateType.SELLING_OUT));
    }

    @Test
    public void findByUserSellingOutDaoMethodCallTest() throws Exception {
        LotLogic.findByUser(any(User.class), LotStateType.SELLING_OUT);

        Mockito.verify(lotDao, Mockito.times(1)).findSellingOutByUser(any(User.class));
    }

    @Test
    public void findByUserSellingOutThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: LotDao findSellingOutByUser() method produced DaoException."))
                .when(lotDao)
                .findSellingOutByUser(any(User.class));

        assertTrue(LotLogic.findByUser(any(User.class), LotStateType.SELLING_OUT).isEmpty());
    }

    @Test
    public void findByUserPurchasedResultTest() throws Exception {
        Mockito.when(lotDao.findPurchasedByUser(any(User.class))).thenReturn(NOT_EMPTY_LOTS_LIST);

        assertEquals(NOT_EMPTY_LOTS_LIST, LotLogic.findByUser(any(User.class), LotStateType.PURCHASED));
    }

    @Test
    public void findByUserPurchasedDaoMethodCallTest() throws Exception {
        LotLogic.findByUser(any(User.class), LotStateType.PURCHASED);

        Mockito.verify(lotDao, Mockito.times(1)).findPurchasedByUser(any(User.class));
    }

    @Test
    public void findByUserPurchasedThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: LotDao findPurchasedByUser() method produced DaoException."))
                .when(lotDao)
                .findPurchasedByUser(any(User.class));

        assertTrue(LotLogic.findByUser(any(User.class), LotStateType.PURCHASED).isEmpty());
    }

    @Test
    public void findByUserFinishedResultTest() throws Exception {
        Mockito.when(lotDao.findFinishedByUser(any(User.class))).thenReturn(NOT_EMPTY_LOTS_LIST);

        assertEquals(NOT_EMPTY_LOTS_LIST, LotLogic.findByUser(any(User.class), LotStateType.FINISHED));
    }

    @Test
    public void findByUserFinishedDaoMethodCallTest() throws Exception {
        LotLogic.findByUser(any(User.class), LotStateType.FINISHED);

        Mockito.verify(lotDao, Mockito.times(1)).findFinishedByUser(any(User.class));
    }

    @Test
    public void findByUserFinishedThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: LotDao findFinishedByUser() method produced DaoException."))
                .when(lotDao)
                .findFinishedByUser(any(User.class));

        assertTrue(LotLogic.findByUser(any(User.class), LotStateType.FINISHED).isEmpty());
    }

    @Test
    public void findByCategoryResultTest() throws Exception {
        Mockito.when(lotDao.findByCategory(any(Category.class))).thenReturn(NOT_EMPTY_LOTS_LIST);

        assertEquals(NOT_EMPTY_LOTS_LIST, LotLogic.findByCategory(any(Category.class)));
    }

    @Test
    public void findByCategoryDaoMethodCallTest() throws Exception {
        LotLogic.findByCategory(any(Category.class));

        Mockito.verify(lotDao, Mockito.times(1)).findByCategory(any(Category.class));
    }

    @Test
    public void findByCategoryThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: LotDao findByCategory() method produced DaoException."))
                .when(lotDao)
                .findByCategory(any(Category.class));

        assertTrue(LotLogic.findByCategory(any(Category.class)).isEmpty());
    }

    @Test
    public void createResultTest() throws Exception {
        Mockito.when(lotDao.create(any(Lot.class))).thenReturn(ANY_LOT);

        assertEquals(ANY_LOT, LotLogic.create(any(Lot.class)));
    }

    @Test
    public void createDaoMethodCallTest() throws Exception {
        LotLogic.create(any(Lot.class));

        Mockito.verify(lotDao, Mockito.times(1)).create(any(Lot.class));
    }

    @Test
    public void createThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: LotDao create() method produced DaoException."))
                .when(lotDao)
                .create(any(Lot.class));

        assertNull(LotLogic.create(any(Lot.class)));
    }

    @Test
    public void updateDaoMethodCallTest() throws Exception {
        LotLogic.update(any(Lot.class));

        Mockito.verify(lotDao, Mockito.times(1)).update(any(Lot.class));
    }

    @Test
    public void updateThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: LotDao update() method produced DaoException."))
                .when(lotDao)
                .update(any(Lot.class));

        LotLogic.update(any(Lot.class));
    }


    @Test
    public void deleteDaoMethodCallTest() throws Exception {
        Bid bid = new Bid();
        bid.setId(1L);
        ArrayList<Bid> bids = new ArrayList<>();
        bids.add(bid);

        BidDao bidDao = Mockito.mock(BidDao.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);

        LotLogic.delete(ANY_LOT);

        Mockito.verify(bidDao, Mockito.times(1)).findByLot(any(Lot.class));
        Mockito.verify(bidDao, Mockito.times(bids.size())).update(any(Bid.class));
        Mockito.verify(bidDao, Mockito.times(bids.size())).findByPk(anyLong());
        Mockito.verify(lotDao, Mockito.times(1)).update(any(Lot.class));
        Mockito.verify(lotDao, Mockito.times(1)).findByPk(anyLong());
    }

    @Test
    public void deleteFindByLotThrowExceptionTest() throws Exception {
        Bid bid = new Bid();
        bid.setId(1L);
        ArrayList<Bid> bids = new ArrayList<>();
        bids.add(bid);

        BidDao bidDao = Mockito.mock(BidDao.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: BidDao findByLot() method produced DaoException."))
                .when(bidDao)
                .findByLot(any(Lot.class));

        LotLogic.delete(ANY_LOT);
    }

    @Test
    public void deleteBidUpdateThrowExceptionTest() throws Exception {
        Bid bid = new Bid();
        bid.setId(1L);
        ArrayList<Bid> bids = new ArrayList<>();
        bids.add(bid);

        BidDao bidDao = Mockito.mock(BidDao.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: BidDao update() method produced DaoException."))
                .when(bidDao)
                .update(any(Bid.class));

        LotLogic.delete(ANY_LOT);
    }

    @Test
    public void deleteBidFindByPkThrowExceptionTest() throws Exception {
        Bid bid = new Bid();
        bid.setId(1L);
        ArrayList<Bid> bids = new ArrayList<>();
        bids.add(bid);

        BidDao bidDao = Mockito.mock(BidDao.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: BidDao findByPk() method produced DaoException."))
                .when(bidDao)
                .findByPk(anyLong());

        LotLogic.delete(ANY_LOT);
    }

    @Test
    public void deleteLotUpdateThrowExceptionTest() throws Exception {
        Bid bid = new Bid();
        bid.setId(1L);
        ArrayList<Bid> bids = new ArrayList<>();
        bids.add(bid);

        BidDao bidDao = Mockito.mock(BidDao.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: LotDao update() method produced DaoException."))
                .when(lotDao)
                .update(any(Lot.class));

        LotLogic.delete(ANY_LOT);
    }

    @Test
    public void deleteLotFindByPkThrowExceptionTest() throws Exception {
        Bid bid = new Bid();
        bid.setId(1L);
        ArrayList<Bid> bids = new ArrayList<>();
        bids.add(bid);

        BidDao bidDao = Mockito.mock(BidDao.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: LotDao findByPk() method produced DaoException."))
                .when(lotDao)
                .findByPk(anyLong());

        LotLogic.delete(ANY_LOT);
    }


    @Test
    public void validateResultTest() throws Exception {
        assertTrue(LotLogic.validate(ANY_LOT));
    }
}