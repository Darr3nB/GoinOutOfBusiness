package com.ZSoos_Darren.GoingOutOfBusiness.controller;

import com.ZSoos_Darren.GoingOutOfBusiness.Model.Product;
import com.ZSoos_Darren.GoingOutOfBusiness.Model.ProductType;
import com.ZSoos_Darren.GoingOutOfBusiness.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @GetMapping("/category/{category}/{page}")
    public Page<Product> getProductsPageFromCategory(
            @PathVariable String category,
            @PathVariable int page,
            @RequestParam(name = "order-by", defaultValue = "id") String orderBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction,
            @RequestParam(name = "per-page", defaultValue = "20") int productPerPage) {
        return productService.getPageOfProductsFromCategory(page,productPerPage,ProductType.valueOf(category),orderBy,direction);
    }

//    @GetMapping("/list/{page}")
//    public ResponseEntity<Page<Product>> getAllProductsPage(
//            @PathVariable int page,
//            @RequestParam(name = "order-by", defaultValue = "id") String orderBy,
//            @RequestParam(name = "direction", defaultValue = "desc") String direction,
//            @RequestParam(name = "per-page", defaultValue = "20") int productPerPage) {
//
//
//    }


}
