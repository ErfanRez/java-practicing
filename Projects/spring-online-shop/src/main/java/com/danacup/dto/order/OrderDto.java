package com.danacup.dto.order;

import com.danacup.dto.product.ProductDto;
import com.danacup.entity.OrderEntity;
import com.danacup.entity.OrderItemEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDto {

    private Long id;
    private List<OrderItemDto> items;
    private OrderEntity.Status status;
    private Double totalPrice;
    public String fullName;
    public String address;
    public String zipCode;

}
