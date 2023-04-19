package com.ZSoos_Darren.GoingOutOfBusiness.controller;

import com.ZSoos_Darren.GoingOutOfBusiness.dto.EditedProduct;
import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import com.ZSoos_Darren.GoingOutOfBusiness.model.Product;
import com.ZSoos_Darren.GoingOutOfBusiness.dto.Registration;
import com.ZSoos_Darren.GoingOutOfBusiness.service.ProductService;
import com.ZSoos_Darren.GoingOutOfBusiness.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/search-user")
    public Page<GoobUser> getAllUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "order-by", defaultValue = "id") String orderBy,
                                      @RequestParam(name = "direction", defaultValue = "desc") String direction,
                                      @RequestParam(name = "per-page", defaultValue = "20") int perPage,
                                      @RequestParam(name = "id", required = false) Long id,
                                      @RequestParam(name = "email", required = false) String email,
                                      @RequestParam(name = "role", required = false) String role) {
        return userService.searchWithoutDate(page,perPage,orderBy,direction,id,email,role);
    }

    @PostMapping("/add-product")
    public ResponseEntity<Void> addProduct(@RequestBody Product newProduct) {
        productService.saveProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/edit-product/{id}")
    public ResponseEntity<Void> editProduct(@RequestBody EditedProduct editedProduct, @PathVariable Long id) {
        boolean isSuccessful = productService.updateProduct(editedProduct,id);
        return ResponseEntity.status(isSuccessful? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/add-new-admin")
    public ResponseEntity<Void> addNewAdmin(@RequestBody Registration registration){
        if(!registration.validateField()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userService.saveNewAdmin(registration);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
