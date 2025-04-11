package com.vunnen.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    private int userId;
    @NotEmpty(message = "Name should be filled")
    private String fullName;
    @Min(value = 0, message = "Birth of year should be more than zero")
    private int birthYear;
}
