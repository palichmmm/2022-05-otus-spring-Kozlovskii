package ru.otus.spring.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.BookDTO;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import javax.validation.Valid;

@Controller
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/book/all")
    public String getAllBook(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "/book/all";
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @GetMapping("/book/edit/{id}")
    public String getBookEditPage(@ModelAttribute("book") BookDTO book, Model model) {
        Book editBook = bookService.findById(book.getId());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        book.setBookName(editBook.getBookName());
        book.setAuthor(editBook.getAuthor().getAuthorName());
        book.setGenre(editBook.getGenre().getGenreName());
        return "/book/edit";
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/book/edit/{id}")
    public String saveBookEditPage(@Valid @ModelAttribute("book") BookDTO book,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "/book/edit";
        }
        Book editBook = bookService.findById(book.getId());
        Author newAuthor = authorService.findByName(book.getAuthor());
        Genre newGenre = genreService.findByName(book.getGenre());
        editBook.setBookName(book.getBookName());
        editBook.setAuthor(newAuthor);
        editBook.setGenre(newGenre);
        bookService.save(editBook);
        return "redirect:/book/all";
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @GetMapping("/book/create")
    public String getBookCreationPage(@ModelAttribute("book") BookDTO book, Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "/book/create";
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/book/create")
    public String saveBookCreationPage(@Valid @ModelAttribute("book") BookDTO book,
                                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "/book/create";
        }
        Author newAuthor = authorService.findByName(book.getAuthor());
        Genre newGenre = genreService.findByName(book.getGenre());
        Book newBook = new Book(book.getBookName(), newAuthor, newGenre);
        bookService.save(newBook);
        return "redirect:/book/all";
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/book/comments/{id}")
    public String getCommentsBook(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "/book/comments";
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @GetMapping("/book/delete/{id}")
    public String getBookDeletePage(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "/book/delete";
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/book/all";
    }
}
