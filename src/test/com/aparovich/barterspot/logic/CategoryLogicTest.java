package com.aparovich.barterspot.logic;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.dao.factory.DaoFactory;
import com.aparovich.barterspot.dao.impl.BidDao;
import com.aparovich.barterspot.dao.impl.CategoryDao;
import com.aparovich.barterspot.dao.impl.LotDao;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Category;
import com.aparovich.barterspot.model.bean.Lot;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest({DaoFactory.class})
public class CategoryLogicTest {

    private static final Category ANY_CATEGORY = new Category();

    private static final List<Category> NOT_EMPTY_CATEGORIES_LIST = new ArrayList<>();

    @Mock
    private CategoryDao categoryDao;


    @Before
    public void setUp() throws Exception {
        /*
        We need to set ANY_CATEGORY fields id and lot in order to prevent
        comparison ANY_CATEGORY and any new empty Category.class instance.
        */
        ANY_CATEGORY.setId(1L);

        NOT_EMPTY_CATEGORIES_LIST.add(ANY_CATEGORY);

        PowerMockito.mockStatic(DaoFactory.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.CATEGORY)).thenReturn(categoryDao);
    }

    @Test
    public void findByNameResultTest() throws Exception {
        Mockito.when(categoryDao.findByName(anyString())).thenReturn(ANY_CATEGORY);

        assertEquals(ANY_CATEGORY, CategoryLogic.findByName(anyString()));
    }

    @Test
    public void findByNameDaoMethodCallTest() throws Exception {
        CategoryLogic.findByName(anyString());

        Mockito.verify(categoryDao, Mockito.times(1)).findByName(anyString());
    }

    @Test
    public void findByNameThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: CategoryDao findByName() method produced DaoException."))
                .when(categoryDao)
                .findByName(anyString());

        assertNull(CategoryLogic.findByName(anyString()));
    }

    @Test
    public void findAllResultTest() throws Exception {
        Mockito.when(categoryDao.findAll()).thenReturn(NOT_EMPTY_CATEGORIES_LIST);

        assertEquals(NOT_EMPTY_CATEGORIES_LIST, CategoryLogic.findAll());
    }

    @Test
    public void findAllDaoMethodCall() throws Exception {
        CategoryLogic.findAll();

        Mockito.verify(categoryDao, Mockito.times(1)).findAll();
    }

    @Test
    public void findAllThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: CategoryDao findByAll() method produced DaoException."))
                .when(categoryDao)
                .findAll();

        assertTrue(CategoryLogic.findAll().isEmpty());
    }

    @Test
    public void createResultTest() throws Exception {
        Mockito.when(categoryDao.create(any(Category.class))).thenReturn(ANY_CATEGORY);

        assertEquals(ANY_CATEGORY, CategoryLogic.create(any(Category.class)));
    }

    @Test
    public void createDaoMethodCallTest() throws Exception {
        CategoryLogic.create(any(Category.class));

        Mockito.verify(categoryDao, Mockito.times(1)).create(any(Category.class));
    }

    @Test
    public void createThrowExceptionTest() throws Exception {
        Mockito .doThrow(new DaoException("Test: CategoryDao create() method produced DaoException."))
                .when(categoryDao)
                .create(any(Category.class));

        assertNull(CategoryLogic.create(any(Category.class)));
    }

    @Test
    public void deleteDaoMethodCallTest() throws Exception {
        LotDao lotDao = Mockito.mock(LotDao.class);
        List<Lot> lots = Mockito.mock(List.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(lots.isEmpty()).thenReturn(true);
        Mockito.when(lotDao.findByCategory(any(Category.class))).thenReturn(lots);
        Mockito.when(categoryDao.findByName(anyString())).thenReturn(null);

        CategoryLogic.delete(ANY_CATEGORY);

        Mockito.verify(lotDao, Mockito.times(1)).findByCategory(any(Category.class));
        Mockito.verify(categoryDao, Mockito.times(1)).delete(any(Category.class));
        Mockito.verify(categoryDao, Mockito.times(1)).findByName(anyString());
    }

    @Test
    public void deleteDeleteThrowExceptionTest() throws Exception {
        LotDao lotDao = Mockito.mock(LotDao.class);
        List<Lot> lots = Mockito.mock(List.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(lots.isEmpty()).thenReturn(true);
        Mockito.when(lotDao.findByCategory(any(Category.class))).thenReturn(lots);
        Mockito.when(categoryDao.findByName(anyString())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: CategoryDao delete() method produced DaoException."))
                .when(categoryDao)
                .delete(any(Category.class));

        CategoryLogic.delete(ANY_CATEGORY);
    }

    @Test
    public void deleteFindByCategoryThrowExceptionTest() throws Exception {
        LotDao lotDao = Mockito.mock(LotDao.class);
        List<Lot> lots = Mockito.mock(List.class);
        PowerMockito.when(DaoFactory.getDao(ModelType.LOT)).thenReturn(lotDao);

        Mockito.when(lots.isEmpty()).thenReturn(true);
        Mockito.when(lotDao.findByCategory(any(Category.class))).thenReturn(lots);
        Mockito.when(categoryDao.findByName(anyString())).thenReturn(null);

        Mockito .doThrow(new DaoException("Test: LotDao findByCategory() method produced DaoException."))
                .when(lotDao)
                .findByCategory(any(Category.class));

        CategoryLogic.delete(ANY_CATEGORY);
    }
}