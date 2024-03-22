package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @NotNull
    @Positive
    private Integer id;

    @Pattern(regexp = "^((Программирование|Дизайн)$)", message = "Родительский пип может быть либо 'Программирование' или 'Дизайн'")
    private String parent;

    @Pattern(
            regexp = "^((Программирование|WEB программист|QA Engineer|Дизайн|UI/UX дизайнер|Графический дизайнер)$)",
            message = "Категория может быть либо Программирование|WEB программист|QA Engineer|Дизайн|UI/UX дизайнер|Графический дизайнер"
    )
    private String name;
}
