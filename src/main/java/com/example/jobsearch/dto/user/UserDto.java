package com.example.jobsearch.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    Integer id;

    @NotBlank
    String name;

    String surname;

    @Positive
    @Min(18)
    @Max(99)
    Integer age;

    @NotBlank
    @Email
    String email;

    @NotBlank
    @Size(min = 4, max = 24, message = "Длина должна быть в диапазоне от 4 до 24 символов")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$",
            message = "Должны содержаться минимум один заглавный символ и одна цифра"
    )
    String password;

    @JsonProperty("phone_number")
    @Pattern(regexp = "^996-\\d{3}-\\d{2}-\\d{2}-\\d{2}$", message = "формат должен соответствовать '996-ddd-dd-dd-dd'")
    String phoneNumber;

    String avatar;

    //    @NotBlank
    @JsonProperty("account_type")
    @Pattern(regexp = "^((EMPLOYER|EMPLOYEE)$)", message = "Тип аккаунта может быть либо 'Соискатель' или 'Работодатель'")
    String accountType;
}

