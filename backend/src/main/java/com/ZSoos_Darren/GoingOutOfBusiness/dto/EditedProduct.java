package com.ZSoos_Darren.GoingOutOfBusiness.dto;

import com.ZSoos_Darren.GoingOutOfBusiness.model.Product;
import com.ZSoos_Darren.GoingOutOfBusiness.model.ProductType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EditedProduct {
    private String name;
    private String description;
    private BigDecimal price;
    ProductType type;
    Integer inventory;
    private String picture;

    public Product overWriteProduct(Product product) {
        if (name != null) product.setName(name);
        if (description != null) product.setDescription(description);
        if (price != null) product.setPrice(price);
        if (type != null) product.setType(type);
        if (inventory != null) product.setInventory(inventory);
        if (picture != null) product.setPicture(picture);
        return product;
    }
}

