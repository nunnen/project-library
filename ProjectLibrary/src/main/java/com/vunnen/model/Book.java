package com.vunnen.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.util.Objects.isNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    private int bookId;
    private int userId;
    @Size(min = 2, max = 200, message = "Название должно быть от 2 до 64 символов")
    @NotEmpty(message = "Поле с названием должно быть заполнено")
    private String name;
    @Size(min = 2, max = 200, message = "Имя автора должно быть от 2 до 64 символов")
    @NotEmpty(message = "Поле с именем автора должно быть заполнено")
    private String author;
    @Min(value = 1900, message = "Книга должна быть старше 1900 года")
    @Max(value = 2025, message = "Книга должна быть младше 2025 года")
    private int year;

    public Boolean isBookRented() {
        return (userId > 0);
    }
}
