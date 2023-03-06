package com.ZSoos_Darren.GoingOutOfBusiness;

import com.ZSoos_Darren.GoingOutOfBusiness.Model.Product;
import com.ZSoos_Darren.GoingOutOfBusiness.Model.ProductType;
import com.ZSoos_Darren.GoingOutOfBusiness.dto.CartItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CartItemTest {

    private static final Product testProduct = new Product();

    @BeforeAll
    public static void setUp() {
        testProduct.setName("Test product");
        testProduct.setPrice(BigDecimal.valueOf(1000.000555));
        testProduct.setType(ProductType.HOUSEHOLD);
    }

    @Test
    public void test_setDataFromProduct_valuesEqual() {
        CartItem cartItem = new CartItem();

        cartItem.setDataFromProduct(testProduct);

        Assertions.assertEquals(cartItem.getType(),testProduct.getType(),"Product types must match");
        Assertions.assertEquals(cartItem.getName(),testProduct.getName(),"Product names must match");
        Assertions.assertEquals(cartItem.getPrice(),testProduct.getPrice(),"Product prices must match");
    }
}
