package com.demo.productsapp.domain.models.binding;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class AddToCartBindingModel {

    private Integer quantity;
}
