package com.vunnen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    private int bookId;
    private int userId;
    private String name;
    private String author;
    private int year;
}
