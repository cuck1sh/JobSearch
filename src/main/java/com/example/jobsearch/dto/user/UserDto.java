package com.example.jobsearch.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    Integer age;

    @NotBlank
    @Email
    String email;

    @NotBlank
    @Size(min = 4, max = 24, message = "Length must be more than 4 and less than 24 characters")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$",
            message = "Should contain at least one UPPER case letter, One number"
    )
    String password;

    @JsonProperty("phone_number")
    @Pattern(regexp = "^996-\\d{3}-\\d{2}-\\d{2}-\\d{2}$", message = "формат должен соответствовать '996-ddd-dd-dd-dd'")
    String phoneNumber;

    String avatar;

    @JsonProperty("account_type")
    @Pattern(regexp = "^((EMPLOYER|EMPLOYEE)$)", message = "Тип может быть либо 'EMPLOYER' или 'EMPLOYEE'")
    String accountType;
}

