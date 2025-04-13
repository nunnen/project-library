package com.vunnen.controller;

import com.vunnen.dao.BookDAO;
import com.vunnen.dao.PersonDAO;
import com.vunnen.model.Book;
import com.vunnen.model.Person;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.getBooksList());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        List<Person> people = personDAO.getPeopleList();
        Book book = bookDAO.get(id);
        model.addAttribute("book", book);
        if (!book.isBookRented()) model.addAttribute("people", people);
        else model.addAttribute("tenant", personDAO.get(book.getUserId()));
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new-book";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new-book";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.get(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book updatedBook, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDAO.update(id, updatedBook);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PutMapping("/{id}")
    public String addUserToBook(@PathVariable("id") int id,
                                @ModelAttribute("person") Person person) {
        bookDAO.addUserToBook(id, person.getUserId());
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        Book book = bookDAO.get(id);
        int userId = book.getUserId();
        bookDAO.removeUserFromBook(id, userId);
        return "redirect:/books/" + id;
    }
}
