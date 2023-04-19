package com.ZSoos_Darren.GoingOutOfBusiness.controller;

import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import com.ZSoos_Darren.GoingOutOfBusiness.service.ProductService;
import com.ZSoos_Darren.GoingOutOfBusiness.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
