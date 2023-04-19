package com.ZSoos_Darren.GoingOutOfBusiness.controller;

import com.ZSoos_Darren.GoingOutOfBusiness.model.Product;
import com.ZSoos_Darren.GoingOutOfBusiness.model.ProductType;
import com.ZSoos_Darren.GoingOutOfBusiness.dto.CartItem;
import com.ZSoos_Darren.GoingOutOfBusiness.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @GetMapping("/category/{category}/{page}")
    public ResponseEntity<Page<Product>> getProductsPageFromCategory(
            @PathVariable String category,
            @PathVariable int page,
            @RequestParam(name = "order-by", defaultValue = "id") String orderBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction,
            @RequestParam(name = "per-page", defaultValue = "20") int productPerPage) {
        var results = productService.getPageOfProductsFromCategory(page,productPerPage, ProductType.valueOf(category.toUpperCase()),orderBy,direction);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @GetMapping("/list/{page}")
    public ResponseEntity<Page<Product>> getAllProductsPage(
            @PathVariable int page,
            @RequestParam(name = "order-by", defaultValue = "id") String orderBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction,
            @RequestParam(name = "per-page", defaultValue = "20") int productPerPage) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getPageOfAllProducts(page,productPerPage,orderBy,direction));
    }

    @GetMapping("/search/{page}")
    public ResponseEntity<Page<Product>> getAllProductsPage(
            @PathVariable int page,
            @RequestParam(name = "order-by", defaultValue = "id") String orderBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction,
            @RequestParam(name = "per-page", defaultValue = "20") int productPerPage,
            @RequestParam(name = "from", defaultValue = "0") BigDecimal priceFrom,
            @RequestParam(name = "to") BigDecimal priceTo,
            @RequestParam(name = "name", defaultValue = "") String searchName,
            @RequestParam(name = "category", defaultValue = "") String category) {
        if(category.isEmpty()) {
            if(searchName.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(productService.findAllByPriceBetween(priceFrom,priceTo,page,productPerPage,orderBy,direction));
            return ResponseEntity.status(HttpStatus.OK).body(productService.findAllByPriceBetweenAndNameContainsIgnoreCase(priceFrom,priceTo,searchName,page,productPerPage,orderBy,direction));
        } else {
            if(searchName.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsForTypeAndPriceBetween(ProductType.valueOf(category),priceFrom, priceTo, page,productPerPage,orderBy,direction));
            return ResponseEntity.status(HttpStatus.OK).body(productService.getProductForTypeAndPriceBetweenAndNameContainsIgnoreCase(ProductType.valueOf(category),priceFrom,priceTo,searchName,page,productPerPage,orderBy,direction));
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductDetails(@PathVariable long id) {
        var product = productService.getProductForId(id);
        if(product == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping("/cart")
    public ResponseEntity<Set<CartItem>> viewCart(@RequestBody Set<CartItem> items) {
        for (Iterator<CartItem> i = items.iterator(); i.hasNext();) {
            CartItem element = i.next();
            Product product = productService.getProductForId(element.getId());
            if (product == null) {
                i.remove();
                continue;
            }
            element.setDataFromProduct(product);
        }
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("/get-categories")
    public ProductType[] getAllPossibleCategories() {
        return ProductType.class.getEnumConstants();
    }
}
