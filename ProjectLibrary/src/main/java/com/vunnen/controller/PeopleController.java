package com.vunnen.controller;

import com.vunnen.dao.PersonDAO;
import com.vunnen.model.Book;
import com.vunnen.model.Person;
import com.vunnen.util.PersonValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
@AllArgsConstructor
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personDAO.getPeopleList());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        Person person = personDAO.get(id);
        Optional<List<Book>> books = personDAO.findBooksByUserId(id);
        books.ifPresent(bookList -> model.addAttribute("books", bookList));
        model.addAttribute("person", person);
        return "people/show";
    }

    @GetMapping("/new")
    public String AddPersonView(@ModelAttribute("person") Person person) {
        return "people/new-person";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new-person";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        Person person = personDAO.get(id);
        model.addAttribute("person", person);
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,
                               @ModelAttribute("person") @Valid Person updatedPerson,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDAO.update(id, updatedPerson);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
