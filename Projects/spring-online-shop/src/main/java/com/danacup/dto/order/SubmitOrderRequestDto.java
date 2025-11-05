package com.danacup.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class SubmitOrderRequestDto {

    @Valid
    public List<SubmitOrderItemRequestDto> items;

    @NotBlank
    public String fullName;

    @NotBlank
    public String address;

    @NotBlank
    public String zipCode;

}