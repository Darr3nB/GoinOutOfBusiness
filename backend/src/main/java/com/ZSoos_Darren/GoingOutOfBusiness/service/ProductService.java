package com.ZSoos_Darren.GoingOutOfBusiness.service;

import com.ZSoos_Darren.GoingOutOfBusiness.Model.Product;
import com.ZSoos_Darren.GoingOutOfBusiness.Model.ProductType;
import com.ZSoos_Darren.GoingOutOfBusiness.dao.ProductDao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductDao productDao;

    public Page<Product> getPageOfProductsFromCategory(int page, int numberOfItemsOnPage, ProductType type, String orderBy, String direction) {
        return productDao.findAllByType(type, PageRequest.of(page, numberOfItemsOnPage,Sort.by(direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,orderBy)));
    }

    public Page<Product> getPageOfAllProducts(int page, int numberOfItemsOnPage, String orderBy, String direction) {
        return productDao.findAll(PageRequest.of(page, numberOfItemsOnPage, Sort.by(direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,orderBy)));
    }

    public void saveProduct(Product product) {
        productDao.save(product);
    }
}
