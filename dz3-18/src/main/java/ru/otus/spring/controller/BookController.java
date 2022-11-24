package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dto.BookDTO;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {

    private final BookRepository repository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository repository, GenreRepository genreRepository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/book/all")
    public String all(Model model) {
        List<Book> books = repository.findAll();
        model.addAttribute("books", books);
        return "/book/all";
    }

    @GetMapping("/book/edit/{id}")
    public String edit(@ModelAttribute("book") BookDTO book, Model model) {
        Book editBook = repository.findById(book.getId()).orElseThrow(RuntimeException::new);
        List<Author> authors = authorRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        book.setBookName(editBook.getBookName());
        book.setAuthor(editBook.getAuthor().getAuthorName());
        book.setGenre(editBook.getGenre().getGenreName());
        return "/book/edit";
    }

    @Validated
    @PostMapping("/book/edit/{id}")
    public String edit(@Valid @ModelAttribute("book") BookDTO book,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Author> authors = authorRepository.findAll();
            List<Genre> genres = genreRepository.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            return "/book/edit";
        }
        Book editBook = repository.findById(book.getId()).orElseThrow(RuntimeException::new);
        Author newAuthor = authorRepository.findByAuthorName(book.getAuthor()).orElseThrow(RuntimeException::new);
        Genre newGenre = genreRepository.findByGenreName(book.getGenre()).orElseThrow(RuntimeException::new);
        editBook.setBookName(book.getBookName());
        editBook.setAuthor(newAuthor);
        editBook.setGenre(newGenre);
        repository.save(editBook);
        return "redirect:/book/all";
    }

    @GetMapping("/book/create")
    public String create(@ModelAttribute("book") BookDTO book, Model model) {
        List<Author> authors = authorRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "/book/create";
    }

    @Validated
    @PostMapping("/book/create")
    public String create(@Valid @ModelAttribute("book") BookDTO book,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Author> authors = authorRepository.findAll();
            List<Genre> genres = genreRepository.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            return "/book/create";
        }
        Author newAuthor = authorRepository.findByAuthorName(book.getAuthor()).orElseThrow(RuntimeException::new);
        Genre newGenre = genreRepository.findByGenreName(book.getGenre()).orElseThrow(RuntimeException::new);
        Book newBook = new Book(book.getBookName(), newAuthor, newGenre);
        repository.save(newBook);
        return "redirect:/book/all";
    }

    @GetMapping("/book/comments/{id}")
    public String comments(@PathVariable("id") long id, Model model) {
        Book book = repository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("book", book);
        return "/book/comments";
    }

    @DeleteMapping("/book/delete/{id}")
    public @ResponseBody String delete(@PathVariable("id") long id) {
        repository.deleteById(id);
        return "/book/all";
    }
}
