package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;
import ru.otus.spring.repositories.AuthorRepository;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.GenreRepository;

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
    public String one(@PathVariable("id") long id, Model model) {
        Book book = repository.findById(id).orElseThrow(RuntimeException::new);
        List<Author> authors = authorRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("book", book);
        return "/book/edit";
    }

    @PostMapping("/book/edit/{id}")
    public String edit(@PathVariable("id") long id,
                       @RequestParam("name") String name,
                       @RequestParam("author") String author,
                       @RequestParam("genre") String genre) {
        Book book = repository.findById(id).orElseThrow(RuntimeException::new);
        Author newAuthor = authorRepository.findByAuthorName(author).orElseThrow(RuntimeException::new);
        Genre newGenre = genreRepository.findByGenreName(genre).orElseThrow(RuntimeException::new);
        book.setBookName(name);
        book.setAuthor(newAuthor);
        book.setGenre(newGenre);
        repository.save(book);
        return "redirect:/book/all";
    }

    @GetMapping("/book/create")
    public String create(Model model) {
        List<Author> authors = authorRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "/book/create";
    }

    @PostMapping("/book/create")
    public String edit(@RequestParam("name") String name,
                       @RequestParam("author") String author,
                       @RequestParam("genre") String genre) {
        Author newAuthor = authorRepository.findByAuthorName(author).orElseThrow(RuntimeException::new);
        Genre newGenre = genreRepository.findByGenreName(genre).orElseThrow(RuntimeException::new);
        Book book = new Book(name, newAuthor, newGenre);
        repository.save(book);
        return "redirect:/book/all";
    }

    @GetMapping("/book/comments/{id}")
    public String comments(@PathVariable("id") long id, Model model) {
        Book book = repository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("book", book);
        return "/book/comments";
    }

    @DeleteMapping("/book/delete/{id}")
    public @ResponseBody String edit(@PathVariable("id") long id) {
        repository.deleteById(id);
        return "/book/all";
    }
}
