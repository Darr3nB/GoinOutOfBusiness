package com.ZSoos_Darren.GoingOutOfBusiness.dao;

import com.ZSoos_Darren.GoingOutOfBusiness.Model.Product;
import com.ZSoos_Darren.GoingOutOfBusiness.Model.ProductType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductDao extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("update Product p set p.inventory = p.inventory + :valueChange WHERE p.id = :productId")
    void updateInventory(@Param("productId") Long productId, @Param("valueChange") int valueChange);

    Page<Product> findAllByType(ProductType searchedType, Pageable pageable);
}

