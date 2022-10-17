package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;

@Service
public class ApplicationLauncher implements Launcher {

    private final String testNameAuthor;
    private final String testRealAuthor;
    private final String testNameGenre;
    private final String testRealGenre;
    private final String testNameBook;
    private final String testRealBook;
    private final String testComment;
    private final long longIdTable;
    private final IOService ioService;
    private final CRUDModelBook book;
    private final CRUDModelAuthor author;
    private final CRUDModelGenre genre;
    private final CRUDModelComment comment;

    public ApplicationLauncher(@Value("${test.name.author}") String testNameAuthor,
                               @Value("${test.name.genre}") String testNameGenre,
                               @Value("${test.name.book}") String testNameBook,
                               @Value("${test.name.comment}") String testComment,
                               @Value("${test.real.author}") String testRealAuthor,
                               @Value("${test.real.genre}") String testRealGenre,
                               @Value("${test.real.book}") String testRealBook,
                               @Value("${test.table.id}") long longIdTable,
                               IOService ioService,
                               CRUDModelBook book,
                               CRUDModelAuthor author,
                               CRUDModelGenre genre,
                               CRUDModelComment comment) {
        this.testNameAuthor = testNameAuthor;
        this.testNameGenre = testNameGenre;
        this.testNameBook = testNameBook;
        this.testComment = testComment;
        this.testRealAuthor = testRealAuthor;
        this.testRealGenre = testRealGenre;
        this.testRealBook = testRealBook;
        this.longIdTable = longIdTable;
        this.ioService = ioService;
        this.book = book;
        this.author = author;
        this.genre = genre;
        this.comment = comment;
    }

    @Override
    public void run() {

        ioService.outputString("\n################################ ПРОВЕРКИ ПО АВТОРУ ################################");

        ioService.outputString("\nВсе авторы в базе:");
        author.showAll();

        ioService.outputString("\nЗапрос в базу авторов по ID=" + longIdTable);
        author.showById(longIdTable);

        ioService.outputString("\nЗапрос в базу авторов по NAME=" + testRealAuthor);
        author.showByName(testRealAuthor);

        ioService.outputString("\nСоздать запись автора(" + testNameAuthor + ") в базу");
        Author resultAuthor = author.create(new Author(testNameAuthor));
        ioService.outputString(resultAuthor.getId() > 0 ? "Создан автор с ID=" + resultAuthor.getId() : "Ошибка создания автора!!!");

        ioService.outputString("\nСмотрим создался ли новый автор в базе:");
        author.showAll();

        ioService.outputString("\nИзменяем имя автора путем удвоения его имени...");
        resultAuthor.setAuthorName(resultAuthor.getAuthorName() + "-" + resultAuthor.getAuthorName());
        author.update(resultAuthor);

        ioService.outputString("\nСмотрим удвоилось ли имя автора в базе:");
        author.showAll();

        ioService.outputString("\nУдаление автора с ID=" + resultAuthor.getId());
        author.deleteById(resultAuthor.getId());

        ioService.outputString("\nСмотрим удалился ли новый автор из базы:");
        author.showAll();

        ioService.outputString("\n============================ КОНЕЦ ПРОВЕРОК ПО АВТОРУ ============================");


        ioService.outputString("\n################################ ПРОВЕРКИ ПО ЖАНРУ ################################");

        ioService.outputString("\nВсе жанры в базе:");
        genre.showAll();

        ioService.outputString("\nЗапрос в базу жанров по ID=" + longIdTable);
        genre.showById(longIdTable);

        ioService.outputString("\nЗапрос в базу жанров по NAME=" + testRealGenre);
        genre.showById(longIdTable);

        ioService.outputString("\nСоздать запись жанра(" + testNameGenre + ") в базу");
        Genre resultGenre = genre.create(new Genre(testNameGenre));
        ioService.outputString(resultGenre.getId() > 0 ? "Создан жанр с ID=" + resultGenre.getId() : "Ошибка создания жанра!!!");

        ioService.outputString("\nСмотрим создался ли новый жанр в базе:");
        genre.showAll();

        ioService.outputString("\nИзменяем название жанра путем удвоения его названия...");
        resultGenre.setGenreName(resultGenre.getGenreName() + "-" + resultGenre.getGenreName());
        genre.update(resultGenre);

        ioService.outputString("\nСмотрим удвоилось ли название жанра в базе:");
        genre.showAll();

        ioService.outputString("\nУдаление жанра с ID=" + resultGenre.getId());
        genre.deleteById(resultGenre.getId());

        ioService.outputString("\nСмотрим удалилось ли название жанра из базы:");
        genre.showAll();

        ioService.outputString("\n============================ КОНЕЦ ПРОВЕРОК ПО ЖАНРУ ============================");


        ioService.outputString("\n################################ ПРОВЕРКИ ПО КНИГЕ ################################");

        ioService.outputString("\nВсе книги в базе: ");
        book.showAll();

        ioService.outputString("\nЗапрос в базу книг по ID=" + longIdTable);
        book.showById(longIdTable);

        ioService.outputString("\nЗапрос в базу книг по NAME=" + testRealBook);
        book.showByName(testRealBook);

        ioService.outputString("\nВывод всех комментариев книги по ее ID=" + longIdTable);
        book.showAllCommentsBookById(longIdTable);

        ioService.outputString("\nСоздать запись книги(" + testNameBook + ") в базу");
        Book resultBook = book.create(new Book(testNameBook, new Author(1), new Genre(1)));
        ioService.outputString(resultBook.getId() > 0 ? "Создана книга с ID=" + resultBook.getId() : "Ошибка создания книги!!!");

        ioService.outputString("\nСмотрим создалась ли новая книга в базе:");
        book.showAll();

        ioService.outputString("\nИзменяем название книги путем удвоения ее названия...");
        resultBook.setBookName(resultBook.getBookName() + "-" + resultBook.getBookName());
        book.update(resultBook);

        ioService.outputString("\nСмотрим удвоилось ли название книги в базе:");
        book.showAll();

        ioService.outputString("\nУдаление книги с ID=" + resultBook.getId());
        book.deleteById(resultBook.getId());

        ioService.outputString("\nСмотрим удалилась книга из базы:");
        book.showAll();

        ioService.outputString("\n============================ КОНЕЦ ПРОВЕРОК ПО КНИГЕ ============================");

        ioService.outputString("\n################################ ПРОВЕРКИ ПО КОММЕНТАРИЮ ################################");

        ioService.outputString("\nВсе комментарии в базе:");
        comment.showAll();


        ioService.outputString("\nЗапрос в базу комментариев по ID=" + longIdTable);
        comment.showById(longIdTable);

        ioService.outputString("\nСоздать запись комментария(" + testComment + ") в базу");
        Comment resultComment = comment.create(new Comment(new Book(1), testComment));
        ioService.outputString(resultComment.getId() > 0 ? "Создан комментарий с ID=" + resultComment.getId() : "Ошибка создания комментария!!!");

        ioService.outputString("\nСмотрим создался ли новый комментарий в базе:");
        comment.showAll();

        ioService.outputString("\nИзменяем название комментария путем удвоения его названия...");
        resultComment.setComment(resultComment.getComment() + "-" + resultComment.getComment());
        comment.update(resultComment);

        ioService.outputString("\nСмотрим удвоился ли комментарий в базе:");
        comment.showAll();

        ioService.outputString("\nУдаление комментария с ID=" + resultComment.getId());
        comment.deleteById(resultComment.getId());

        ioService.outputString("\nСмотрим удалился ли комментарий из базы:");
        comment.showAll();

        ioService.outputString("\n============================ КОНЕЦ ПРОВЕРОК ПО КОММЕНТАРИЮ ============================");
    }
}
