package com.demo.productsapp.domain.models.binding;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class ProductCreateBindingModel {

    @NotEmpty
    private String name;

    @NotEmpty
    @Min(value = 1)
    @Max(value = 10000)
    private Integer quantity;

    @NotEmpty
    @Min(value = 1)
    private BigDecimal price;
}
