package com.ZSoos_Darren.GoingOutOfBusiness.service;

import com.ZSoos_Darren.GoingOutOfBusiness.model.Product;
import com.ZSoos_Darren.GoingOutOfBusiness.model.ProductType;
import com.ZSoos_Darren.GoingOutOfBusiness.dao.ProductDao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    public Product getProductForId(long productId) {
        return productDao.findById(productId).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productDao.save(product);
    }

    public Page<Product> getProductsForTypeAndPriceBetween(ProductType searchedType, BigDecimal from, BigDecimal to, int page, int numberOfItemsOnPage, String orderBy, String direction){
        return productDao.findAllByTypeAndPriceBetween(searchedType, from, to,
                PageRequest.of(page, numberOfItemsOnPage,Sort.by(direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,orderBy)));
    }
    public Page<Product> getProductForTypeAndPriceBetweenAndNameContainsIgnoreCase(ProductType searchedType, BigDecimal from, BigDecimal to,String searchedName, int page, int numberOfItemsOnPage, String orderBy, String direction) {
        return productDao.findAllByTypeAndPriceBetweenAndNameContainsIgnoreCase(searchedType,from , to, searchedName,
                PageRequest.of(page, numberOfItemsOnPage,Sort.by(direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,orderBy)));
    }

    public Page<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to, int page, int numberOfItemsOnPage, String orderBy, String direction){
        return productDao.findAllByPriceBetween(from, to,
                PageRequest.of(page, numberOfItemsOnPage,Sort.by(direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,orderBy)));
    }

    public Page<Product> findAllByPriceBetweenAndNameContainsIgnoreCase(BigDecimal from, BigDecimal to, String searchedName, int page, int numberOfItemsOnPage, String orderBy, String direction){
        return productDao.findAllByPriceBetweenAndNameContainsIgnoreCase(from, to, searchedName,
                PageRequest.of(page, numberOfItemsOnPage,Sort.by(direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,orderBy)));
    }
}
