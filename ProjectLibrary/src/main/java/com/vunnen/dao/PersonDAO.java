package com.vunnen.dao;

import com.vunnen.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeopleList() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, birth_year) VALUES (?, ?)", person.getFullName(), person.getBirthYear());
    }

    public void updatePerson(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name=?, birth_date=? WHERE user_id = ?", updatedPerson.getFullName(), updatedPerson.getBirthYear(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE user_id = ?", id);
    }

}
