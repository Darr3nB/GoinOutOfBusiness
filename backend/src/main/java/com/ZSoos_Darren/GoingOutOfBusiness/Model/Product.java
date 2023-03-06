package com.ZSoos_Darren.GoingOutOfBusiness.Model;

import com.ZSoos_Darren.GoingOutOfBusiness.helper.IncrementGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o.getClass() != this.getClass()) {
            return false;
        }

        final Product other = (Product) o;

        return (Objects.equals(this.id, other.id) &&
                Objects.equals(this.name, other.name) &&
                Objects.equals(this.description,other.description) &&
                Objects.equals(this.price,other.price) &&
                Objects.equals(this.type,other.type) &&
                Objects.equals(this.inventory,other.inventory));
    }
}