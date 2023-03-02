package com.ZSoos_Darren.GoingOutOfBusiness.Model;

import com.ZSoos_Darren.GoingOutOfBusiness.helper.IncrementGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator = "increment-generator")
    @GenericGenerator(name="increment-generator", strategy = "com.ZSoos_Darren.GoingOutOfBusiness.helper.IncrementGenerator", parameters = {
            @Parameter(name = IncrementGenerator.TABLE_NAME,value = "product")
    })
    Long id;
    String name;
    String description;
    BigDecimal price;
    @Enumerated(EnumType.STRING)
    ProductType type;
    Integer inventory;
}