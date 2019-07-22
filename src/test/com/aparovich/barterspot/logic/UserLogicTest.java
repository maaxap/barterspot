package com.aparovich.barterspot.logic;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.dao.factory.DaoFactory;
import com.aparovich.barterspot.dao.impl.*;
import com.aparovich.barterspot.mailer.SSLMailer;
import com.aparovich.barterspot.mailer.exception.MailerException;
import com.aparovich.barterspot.model.bean.*;
import com.aparovich.barterspot.model.util.ModelType;
import com.aparovich.barterspot.model.util.RoleType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest({DaoFactory.class, SSLMailer.class})
public class UserLogicTest {

    private static final String EMPTY_STRING = "";
    private static final User ANY_USER = new User();
    private static final Info ANY_INFO = new Info();
    private static final Settings ANY_SETTINGS = new Settings();

    private static final List<User> NOT_EMPTY_USERS_LIST = new ArrayList<>();

    @Mock
    private UserDao userDao;

    @Mock
    private InfoDao infoDao;

    @Mock
    private SettingsDao settingsDao;

    @Before
    public void setUp() throws Exception {
        ANY_INFO.setId(1L);
        ANY_INFO.setName("TestName");
        ANY_INFO.setSurname("TestSurname");
        ANY_INFO.setPhoneNumber("+375297777777");
        ANY_INFO.setBirthDate(LocalDate.now().minusYears(18));
        ANY_INFO.setAddress("Akademika kuprevicha street, 1/1");
        ANY_INFO.setPostCode("2200010");

        ANY_SETTINGS.setId(1L);
        ANY_SETTINGS.setLocale("en_EN");

        ANY_USER.setId(1L);
        ANY_USER.setEmail("test@epam.com");
        ANY_USER.setPassword("dcf578d55b82f42226a19515a96df10d");
        ANY_USER.setSettings(ANY_SETTINGS);
        ANY_USER.setInfo(ANY_INFO);

        NOT_EMPTY_USERS_LIST.add(ANY_USER);


        PowerMockito.mockStatic(DaoFactory.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.USER)).thenReturn(userDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.INFO)).thenReturn(infoDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.SETTINGS)).thenReturn(settingsDao);
    }

    @Test
    public void encrypt() throws Exception {
        String expected = "dcf578d55b82f42226a19515a96df10d";
        System.out.println(UserLogic.encrypt("blocked"));
        String actual = UserLogic.encrypt("test");

        assertEquals(expected, actual);
    }

    @Test
    public void validate() throws Exception {
        assertTrue(UserLogic.validate(ANY_USER));
    }

    @Test
    public void authoriseResultTest() throws Exception {
        Mockito.when(userDao.findByEmail(anyString())).thenReturn(ANY_USER);

        assertEquals(ANY_USER, UserLogic.authorise(ANY_USER.getEmail(), ANY_USER.getPassword()));
    }

    @Test
    public void authoriseDaoMethodCallTest() throws Exception {
        UserLogic.authorise(anyString(), ANY_USER.getPassword());

        Mockito.verify(userDao, Mockito.times(1)).findByEmail(anyString());
    }

    @Test
    public void authoriseThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: UserDao findByEmail() method produced DaoException."))
                .when(userDao)
                .findByEmail(anyString());

        assertNull(UserLogic.authorise(anyString(), ANY_USER.getPassword()));
    }

    @Test
    public void findByIdResultTest() throws Exception {
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(ANY_USER);

        assertEquals(ANY_USER, UserLogic.findById(anyLong()));
    }

    @Test
    public void findByIdDaoMethodCallTest() throws Exception {
        UserLogic.findById(anyLong());

        Mockito.verify(userDao, Mockito.times(1)).findByPk(anyLong());
    }

    @Test
    public void findByIdThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: UserDao findByPk() method produced DaoException."))
                .when(userDao)
                .findByPk(anyLong());

        assertNull(UserLogic.findById(anyLong()));
    }

    @Test
    public void findAllResultTest() throws Exception {
        Mockito.when(userDao.findAll()).thenReturn(NOT_EMPTY_USERS_LIST);

        assertEquals(NOT_EMPTY_USERS_LIST, UserLogic.findAll());
    }

    @Test
    public void findAllDaoMethodCallTest() throws Exception {
        UserLogic.findAll();

        Mockito.verify(userDao, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: UserDao findAll() method produced DaoException."))
                .when(userDao)
                .findAll();

        assertTrue(UserLogic.findAll().isEmpty());
    }

    @Test
    public void findByRoleAdminResultTest() throws Exception {
        Mockito.when(userDao.findByRole(RoleType.ADMIN)).thenReturn(NOT_EMPTY_USERS_LIST);

        assertEquals(NOT_EMPTY_USERS_LIST, UserLogic.findByRole(RoleType.ADMIN));
    }

    @Test
    public void findByRoleAdminDaoMethodCallTest() throws Exception {
        UserLogic.findByRole(RoleType.ADMIN);

        Mockito.verify(userDao, Mockito.times(1)).findByRole(RoleType.ADMIN);
    }

    @Test
    public void findByRoleAdminThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: UserDao findByRole() method produced DaoException."))
                .when(userDao)
                .findByRole(RoleType.ADMIN);

        assertTrue(UserLogic.findByRole(RoleType.ADMIN).isEmpty());
    }

    @Test
    public void findByRoleClientResultTest() throws Exception {
        Mockito.when(userDao.findByRole(RoleType.CLIENT)).thenReturn(NOT_EMPTY_USERS_LIST);

        assertEquals(NOT_EMPTY_USERS_LIST, UserLogic.findByRole(RoleType.CLIENT));
    }

    @Test
    public void findByRoleClientDaoMethodCallTest() throws Exception {
        UserLogic.findByRole(RoleType.CLIENT);

        Mockito.verify(userDao, Mockito.times(1)).findByRole(RoleType.CLIENT);
    }

    @Test
    public void findByRoleClientThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: UserDao findByRole() method produced DaoException."))
                .when(userDao)
                .findByRole(RoleType.CLIENT);

        assertTrue(UserLogic.findByRole(RoleType.CLIENT).isEmpty());
    }

    @Test
    public void createResultTest() throws Exception {
        Mockito.when(settingsDao.create(any(Settings.class))).thenReturn(ANY_SETTINGS);
        Mockito.when(infoDao.create(any(Info.class))).thenReturn(ANY_INFO);
        Mockito.when(userDao.create(any(User.class))).thenReturn(ANY_USER);

        assertEquals(ANY_USER, UserLogic.create(ANY_USER));
    }

    @Test
    public void createDaoMethodCallTest() throws Exception {
        UserLogic.create(ANY_USER);

        Mockito.verify(userDao, Mockito.times(1)).create(any(User.class));
        Mockito.verify(infoDao, Mockito.times(1)).create(any(Info.class));
        Mockito.verify(settingsDao, Mockito.times(1)).create(any(Settings.class));
    }

    @Test
    public void createUserDaoThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: UserDao create() method produced DaoException."))
                .when(userDao)
                .create(any(User.class));

        assertNull(UserLogic.create(ANY_USER));
    }

    @Test
    public void createInfoDaoThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: InfoDao create() method produced DaoException."))
                .when(infoDao)
                .create(any(Info.class));

        assertNull(UserLogic.create(ANY_USER));
    }

    @Test
    public void createSettingsDaoThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: SettingsDao create() method produced DaoException."))
                .when(settingsDao)
                .create(any(Settings.class));

        assertNull(UserLogic.create(ANY_USER));
    }

    @Test
    public void updateUserDaoMethodCallTest() throws Exception {
        UserLogic.update(ANY_USER);

        Mockito.verify(userDao, Mockito.times(1)).update(any(User.class));
        Mockito.verify(infoDao, Mockito.times(1)).update(any(Info.class));
        Mockito.verify(settingsDao, Mockito.times(1)).update(any(Settings.class));
    }

    @Test
    public void updateUserDaoThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: UserDao update() method produced DaoException."))
                .when(userDao)
                .update(any(User.class));

        UserLogic.update(ANY_USER);
    }

    @Test
    public void updateInfoDaoThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: InfoDao update() method produced DaoException."))
                .when(infoDao)
                .update(any(Info.class));

        UserLogic.update(ANY_USER);
    }

    @Test
    public void updateSettingsDaoThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: SettingsDao update() method produced DaoException."))
                .when(settingsDao)
                .update(any(Settings.class));

        UserLogic.update(ANY_USER);
    }

    @Test
    public void deleteDaoMethodCallTest() throws Exception {
        Lot lot = new Lot();
        lot.setId(1L);
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);

        Bid bid = new Bid();
        bid.setId(1L);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        LotDao lotDao = Mockito.mock(LotDao.class);
        BidDao bidDao = Mockito.mock(BidDao.class);

        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null).thenReturn(null);
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(bids);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(lots);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(null);

        UserLogic.delete(ANY_USER);

        Mockito.verify(userDao, Mockito.times(1)).update(any(User.class));
        Mockito.verify(userDao, Mockito.times(1)).findByPk(anyLong());
        Mockito.verify(bidDao, Mockito.times(2 * bids.size())).update(any(Bid.class));
        Mockito.verify(bidDao, Mockito.times(2 * bids.size())).findByPk(anyLong());
        Mockito.verify(bidDao, Mockito.times(1)).findByUser(any(User.class));
        Mockito.verify(bidDao, Mockito.times(lots.size())).findByLot(any(Lot.class));
        Mockito.verify(lotDao, Mockito.times(lots.size())).findByPk(anyLong());
        Mockito.verify(lotDao, Mockito.times(lots.size())).update(any(Lot.class));
        Mockito.verify(lotDao, Mockito.times(1)).findSellingOutByUser(any(User.class));
    }

    @Test
    public void deleteUserDaoFindByPkThrowException() throws Exception {
        Lot lot = new Lot();
        lot.setId(1L);
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);

        Bid bid = new Bid();
        bid.setId(1L);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        LotDao lotDao = Mockito.mock(LotDao.class);
        BidDao bidDao = Mockito.mock(BidDao.class);

        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null).thenReturn(null);
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(bids);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(lots);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: UserDao findByPk() method produced DaoException."))
                .when(userDao)
                .findByPk(anyLong());

        UserLogic.delete(ANY_USER);
    }

    @Test
    public void deleteUserDaoUpdatePkThrowException() throws Exception {
        Lot lot = new Lot();
        lot.setId(1L);
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);

        Bid bid = new Bid();
        bid.setId(1L);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        LotDao lotDao = Mockito.mock(LotDao.class);
        BidDao bidDao = Mockito.mock(BidDao.class);

        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null).thenReturn(null);
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(bids);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(lots);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: UserDao update() method produced DaoException."))
                .when(userDao)
                .update(any(User.class));

        UserLogic.delete(ANY_USER);
    }

    @Test
    public void deleteLotDaoFindSellingOutByUserThrowException() throws Exception {
        Lot lot = new Lot();
        lot.setId(1L);
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);

        Bid bid = new Bid();
        bid.setId(1L);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        LotDao lotDao = Mockito.mock(LotDao.class);
        BidDao bidDao = Mockito.mock(BidDao.class);

        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null).thenReturn(null);
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(bids);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(lots);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: LotDao findSellingOutByUser() method produced DaoException."))
                .when(lotDao)
                .findSellingOutByUser(any(User.class));

        UserLogic.delete(ANY_USER);
    }

    @Test
    public void deleteLotDaoUpdateThrowException() throws Exception {
        Lot lot = new Lot();
        lot.setId(1L);
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);

        Bid bid = new Bid();
        bid.setId(1L);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        LotDao lotDao = Mockito.mock(LotDao.class);
        BidDao bidDao = Mockito.mock(BidDao.class);

        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null).thenReturn(null);
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(bids);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(lots);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: LotDao update() method produced DaoException."))
                .when(lotDao)
                .update(any(Lot.class));

        UserLogic.delete(ANY_USER);
    }

    @Test
    public void deleteLotDaoFindByPkThrowException() throws Exception {
        Lot lot = new Lot();
        lot.setId(1L);
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);

        Bid bid = new Bid();
        bid.setId(1L);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        LotDao lotDao = Mockito.mock(LotDao.class);
        BidDao bidDao = Mockito.mock(BidDao.class);

        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null).thenReturn(null);
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(bids);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(lots);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: LotDao findByPk() method produced DaoException."))
                .when(lotDao)
                .findByPk(anyLong());

        UserLogic.delete(ANY_USER);
    }

    @Test
    public void deleteBidDaoFindByPkThrowException() throws Exception {
        Lot lot = new Lot();
        lot.setId(1L);
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);

        Bid bid = new Bid();
        bid.setId(1L);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        LotDao lotDao = Mockito.mock(LotDao.class);
        BidDao bidDao = Mockito.mock(BidDao.class);

        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null).thenReturn(null);
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(bids);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(lots);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: BidDao findByPk() method produced DaoException."))
                .when(bidDao)
                .findByPk(anyLong());

        UserLogic.delete(ANY_USER);
    }

    @Test
    public void deleteBidDaoFindByLotThrowException() throws Exception {
        Lot lot = new Lot();
        lot.setId(1L);
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);

        Bid bid = new Bid();
        bid.setId(1L);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        LotDao lotDao = Mockito.mock(LotDao.class);
        BidDao bidDao = Mockito.mock(BidDao.class);

        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null).thenReturn(null);
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(bids);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(lots);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: BidDao findByLot() method produced DaoException."))
                .when(bidDao)
                .findByLot(any(Lot.class));

        UserLogic.delete(ANY_USER);
    }

    @Test
    public void deleteBidDaoFindByUserThrowException() throws Exception {
        Lot lot = new Lot();
        lot.setId(1L);
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);

        Bid bid = new Bid();
        bid.setId(1L);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        LotDao lotDao = Mockito.mock(LotDao.class);
        BidDao bidDao = Mockito.mock(BidDao.class);

        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null).thenReturn(null);
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(bids);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(lots);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: BidDao findByUser() method produced DaoException."))
                .when(bidDao)
                .findByUser(any(User.class));

        UserLogic.delete(ANY_USER);
    }

    @Test
    public void deleteBidDaoUpdateThrowException() throws Exception {
        Lot lot = new Lot();
        lot.setId(1L);
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);

        Bid bid = new Bid();
        bid.setId(1L);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        LotDao lotDao = Mockito.mock(LotDao.class);
        BidDao bidDao = Mockito.mock(BidDao.class);

        PowerMockito.when(DaoFactory.getDao(ModelType.BID)).thenReturn(bidDao);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(bidDao.findByPk(anyLong())).thenReturn(null).thenReturn(null);
        Mockito.when(bidDao.findByUser(any(User.class))).thenReturn(bids);
        Mockito.when(bidDao.findByLot(any(Lot.class))).thenReturn(bids);
        Mockito.when(lotDao.findSellingOutByUser(any(User.class))).thenReturn(lots);
        Mockito.when(lotDao.findByPk(anyLong())).thenReturn(null);
        Mockito.when(userDao.findByPk(anyLong())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: BidDao update() method produced DaoException."))
                .when(bidDao)
                .update(any(Bid.class));

        UserLogic.delete(ANY_USER);
    }

    @Test
    public void sendEmailDaoMethodCallTest() throws Exception {
        PowerMockito.mockStatic(SSLMailer.class);
        Mockito.when(userDao.findByEmail(anyString())).thenReturn(ANY_USER).thenReturn(ANY_USER);
        UserLogic.sendEmail(ANY_USER.getEmail(), ANY_USER.getPassword(), ANY_USER.getEmail(), EMPTY_STRING, EMPTY_STRING);

        Mockito.verify(userDao, Mockito.times(2)).findByEmail(anyString());
    }

    @Test
    public void sendEmailThrowDaoExceptionTest() throws Exception {
        PowerMockito.mockStatic(SSLMailer.class);
        Mockito .doThrow(new DaoException("Test: UserDao findByEmail() method produced DaoException."))
                .when(userDao)
                .findByEmail(anyString());

        UserLogic.sendEmail(ANY_USER.getEmail(), ANY_USER.getPassword(), ANY_USER.getEmail(), EMPTY_STRING, EMPTY_STRING);
    }

    @Test
    public void sendEmailThrowMailerExceptionTest() throws Exception {
        /*PowerMockito.mockStatic(SSLMailer.class);
        PowerMockito.doThrow(new MailerException("Test: SSLMailer send() method produced MailerException."))
                    .when(SSLMailer.class);

        Mockito.when(userDao.findByEmail(anyString())).thenReturn(ANY_USER).thenReturn(ANY_USER);

        UserLogic.sendEmail(ANY_USER.getEmail(), ANY_USER.getPassword(), ANY_USER.getEmail(), EMPTY_STRING, EMPTY_STRING);*/
    }
}