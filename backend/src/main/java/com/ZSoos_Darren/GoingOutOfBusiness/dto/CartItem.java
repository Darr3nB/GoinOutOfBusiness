package com.ZSoos_Darren.GoingOutOfBusiness.dto;

import com.ZSoos_Darren.GoingOutOfBusiness.Model.Product;
import com.ZSoos_Darren.GoingOutOfBusiness.Model.ProductType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItem {
    private long id;
    private int quantity;

    private BigDecimal price;
    private String name;
    private ProductType type;

    public void setDataFromProduct(Product product) {
        name = product.getName();
        type = product.getType();
        price = product.getPrice();
    }
}
