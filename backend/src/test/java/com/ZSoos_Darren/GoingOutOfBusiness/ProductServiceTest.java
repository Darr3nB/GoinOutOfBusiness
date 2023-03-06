package com.ZSoos_Darren.GoingOutOfBusiness;

import com.ZSoos_Darren.GoingOutOfBusiness.Model.Product;
import com.ZSoos_Darren.GoingOutOfBusiness.Model.ProductType;
import com.ZSoos_Darren.GoingOutOfBusiness.dao.ProductDao;
import com.ZSoos_Darren.GoingOutOfBusiness.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductServiceTest {
    private static final long EXPECTED_ID = 100L;
    private static final String EXPECTED_NAME = "Test product";
    private static final String EXPECTED_DESCRIPTION = "Test description";
    private static final BigDecimal EXPECTED_PRICE = new BigDecimal("1000.0001");
    private static final ProductType EXPECTED_TYPE_OTHER = ProductType.OTHER;
    private static final int EXPECTED_INVENTORY = 1000;

    private static final int PAGE = 1;
    private static final int PRODUCT_PER_PAGE = 1;
    private static final String ORDER_COLUMN = "id";
    private static final String ORDER_DIRECTION_DESC_STRING = "desc";
    private static final String ORDER_DIRECTION_ASC_STRING = "asc";
    private static final Sort.Direction ORDER_DIRECTION = Sort.Direction.DESC;

    @Mock
    private ProductDao mockProductDao;

    @InjectMocks
    private ProductService productService;

    private static final Product testProduct1 = new Product(EXPECTED_ID,EXPECTED_NAME,EXPECTED_DESCRIPTION,EXPECTED_PRICE, EXPECTED_TYPE_OTHER,EXPECTED_INVENTORY);
    private static final Product testProduct2 = new Product(EXPECTED_ID+1,EXPECTED_NAME,EXPECTED_DESCRIPTION,EXPECTED_PRICE, EXPECTED_TYPE_OTHER,EXPECTED_INVENTORY+1);
    private static final Product testProduct3 = new Product(EXPECTED_ID+2,EXPECTED_NAME,EXPECTED_DESCRIPTION,EXPECTED_PRICE, EXPECTED_TYPE_OTHER,EXPECTED_INVENTORY+2);


    @BeforeEach
    public void setUpEach(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_saveProduct_successfulSave_returnsMatchingProduct() {
        Mockito.when(mockProductDao.save(testProduct1)).thenReturn(testProduct1);

        Product savedProduct = productService.saveProduct(testProduct1);

        Assertions.assertTrue(savedProduct.equals(testProduct1),"Returned product fields should match initial product fields");
    }

    @Test
    public void test_getProductForId_productIsInDatabase_returnsCorrectProduct() {
        Mockito.when(mockProductDao.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(testProduct1));

        Product returnedProduct = productService.getProductForId(EXPECTED_ID);

        Assertions.assertTrue(returnedProduct.equals(testProduct1),"The returned products values should match the expected values");
    }

    @Test
    public void test_getProductForId_productNotInDatabase_returnsNull() {
        Mockito.when(mockProductDao.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Product returnedProduct = productService.getProductForId(EXPECTED_ID);

        Assertions.assertNull(returnedProduct,"The returned product is should be null");
    }

    @Test
    public void test_getPageOfAllProducts_returnsPageObjectCorrectly() {
        List<Product> expectedProducts = new ArrayList<>(List.of(testProduct1,testProduct2,testProduct3));
        int expectedSize = expectedProducts.size();
        Page<Product> expectedPage = new PageImpl<>(expectedProducts, PageRequest.of(PAGE,PRODUCT_PER_PAGE, Sort.by(ORDER_DIRECTION,ORDER_COLUMN)),expectedSize);

        Mockito.when(mockProductDao.findAll(Mockito.any(PageRequest.class))).thenReturn(expectedPage);

        Page<Product> returnedPage = productService.getPageOfAllProducts(PAGE,PRODUCT_PER_PAGE,ORDER_COLUMN, ORDER_DIRECTION_DESC_STRING);

        var returnedContent = returnedPage.getContent();

        Assertions.assertEquals(expectedPage.getTotalPages(), returnedPage.getTotalPages(), "Total pages should match expected");
        Assertions.assertTrue(expectedPage.getContent().size() == returnedPage.getContent().size()
        && expectedProducts.containsAll(returnedContent) && returnedContent.containsAll(expectedProducts), "Returned content should match expected content");
    }

    @Test
    public void test_getPageOfAllProducts_noProductsInDatabase_returnsPageObjectWithEmptyList() {
        List<Product> emptyList = new ArrayList<>();
        int expectedSize = 0;
        Page<Product> expectedPage = new PageImpl<>(emptyList, PageRequest.of(PAGE,PRODUCT_PER_PAGE, Sort.by(ORDER_DIRECTION,ORDER_COLUMN)),expectedSize);

        Mockito.when(mockProductDao.findAll(Mockito.any(PageRequest.class))).thenReturn(expectedPage);

        Page<Product> returnedPage = productService.getPageOfAllProducts(PAGE,PRODUCT_PER_PAGE,ORDER_COLUMN, ORDER_DIRECTION_DESC_STRING);

        Assertions.assertEquals(expectedSize, returnedPage.getContent().size(), "Returned content should be empty");
    }

    @Test
    public void test_getPageOfAllProducts_sortIdOrderDesc_returnedListInOrder() {
        List<Product> expectedProducts = new ArrayList<>(List.of(testProduct1,testProduct2,testProduct3));
        int expectedSize = expectedProducts.size();
        Page<Product> expectedPage = new PageImpl<>(expectedProducts, PageRequest.of(PAGE,PRODUCT_PER_PAGE, Sort.by(Sort.Direction.DESC,ORDER_COLUMN)),expectedSize);

        Mockito.when(mockProductDao.findAll(Mockito.any(PageRequest.class))).thenReturn(expectedPage);

        Page<Product> returnedPage = productService.getPageOfAllProducts(PAGE,PRODUCT_PER_PAGE,ORDER_COLUMN,ORDER_DIRECTION_DESC_STRING);
        var returnedContent = returnedPage.getContent();

        Assertions.assertTrue(returnedContent.get(0).getId() > returnedContent.get(1).getId() && returnedContent.get(1).getId() > returnedContent.get(2).getId() , "");
    }

    @Test
    public void test_getPageOfProductsFromCategory_returnsPageObjectCorrectly() {
        List<Product> expectedProducts = new ArrayList<>(List.of(testProduct1,testProduct2,testProduct3));
        int expectedSize = expectedProducts.size();
        Page<Product> expectedPage = new PageImpl<>(expectedProducts, PageRequest.of(PAGE,PRODUCT_PER_PAGE, Sort.by(ORDER_DIRECTION,ORDER_COLUMN)),expectedSize);

//        Mockito.when(mockProductDao.findAll(Mockito.any(PageRequest.class))).thenReturn(expectedPage);

        Page<Product> returnedPage = productService.getPageOfProductsFromCategory(PAGE, PRODUCT_PER_PAGE,EXPECTED_TYPE_OTHER,ORDER_COLUMN, ORDER_DIRECTION_DESC_STRING);

        var returnedContent = returnedPage.getContent();

        Assertions.assertEquals(expectedPage.getTotalPages(), returnedPage.getTotalPages(), "Total pages should match expected");
        Assertions.assertTrue(expectedPage.getContent().size() == returnedPage.getContent().size()
                && expectedProducts.containsAll(returnedContent) && returnedContent.containsAll(expectedProducts), "Returned content should match expected content");
    }

}
