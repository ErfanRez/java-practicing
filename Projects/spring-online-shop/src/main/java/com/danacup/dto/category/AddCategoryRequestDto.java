package com.danacup.dto.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddCategoryRequestDto {

    @NotBlank
    public String title;
    @NotBlank
    public String slug;
    public Long parentCategoryId;

}
