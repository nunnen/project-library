package com.vunnen.controller;

import com.vunnen.dao.PersonDAO;
import com.vunnen.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;

    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String showPeople(Model model) {
        model.addAttribute("people", personDAO.getPeopleList());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        Person person = personDAO.get(id);
        System.out.println(person);
        model.addAttribute("person", person);
        return "people/show";
    }

    @GetMapping("/new")
    public String AddPersonView(@ModelAttribute("person") Person person) {
        return "people/new-person";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        return "redirect:/people";
    }
}
