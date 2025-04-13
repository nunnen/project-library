package com.vunnen.dao;

import com.vunnen.model.Book;
import com.vunnen.util.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooksList() {
        return jdbcTemplate.query("SELECT * FROM Book ORDER BY book_id", new BookMapper());
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET name = ?, author = ?, year = ? WHERE book_id = ?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", id);
    }

    public Book get(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Book WHERE book_id = ?", new BookMapper(), id);
    }

    public List<Book> getBooksByUserId(int UserId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE user_id = ?", new BookMapper(), UserId);
    }

    public void addUserToBook(int bookId, int userId) {
        jdbcTemplate.update("UPDATE Book SET user_id = ? WHERE book_id = ?", userId, bookId);
    }

    public void removeUserFromBook(int bookId, int userId) {
        jdbcTemplate.update("UPDATE Book SET user_id = NULL WHERE book_id = ?", bookId);
    }
}
