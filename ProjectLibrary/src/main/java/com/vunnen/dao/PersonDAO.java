package com.vunnen.dao;

import com.vunnen.model.Book;
import com.vunnen.model.Person;
import com.vunnen.util.BookMapper;
import com.vunnen.util.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeopleList() {
        return jdbcTemplate.query("SELECT * FROM Person ORDER BY user_id", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, birth_year) VALUES (?, ?)", person.getFullName(), person.getBirthYear());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name = ?, birth_year = ? WHERE user_id = ?", updatedPerson.getFullName(), updatedPerson.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE user_id = ?", id);
    }

    public Person get(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE user_id = ?", new PersonMapper(), id);
    }

    public Optional<Person> findPersonByFullName(String fullName) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM Person WHERE full_name = ?", new PersonMapper(), fullName));
    }

    public Optional<List<Book>> findBooksByUserId(int userId) {
        return Optional.of(jdbcTemplate.query("SELECT * FROM Book WHERE user_id = ?", new BookMapper(), userId));
    }
}
