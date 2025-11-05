package com.danacup.dto.order;

import com.danacup.dto.product.ProductDto;
import com.danacup.entity.OrderEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderItemDto {

    private Long id;
    private ProductDto product;
    private Integer quantity;
    private Double totalPrice;

}
