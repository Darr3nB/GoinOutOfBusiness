package com.ZSoos_Darren.GoingOutOfBusiness.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    Long id;
    String name;
    String description;
    BigDecimal price;
    @Enumerated(EnumType.STRING)
    ProductType type;
    Integer inventory;
}