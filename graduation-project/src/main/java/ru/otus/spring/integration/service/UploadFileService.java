package ru.otus.spring.integration.service;

import org.springframework.stereotype.Service;

@Service
public class UploadFileService {

//    public File saveLocation(MultipartFile file, Path location) {
//        try {
//            Files.copy(file.getInputStream(), location.resolve(Objects.requireNonNull(file.getOriginalFilename())));
//        } catch (Exception e) {
//            if (e instanceof FileAlreadyExistsException) {
//                throw new RuntimeException("Файл с таким именем уже существует.");
//            }
//            throw new RuntimeException(e.getMessage());
//        }
//    }

//    public Genre genreReplacementLetters(Genre genre) {
//        String newName = genre.getGenreName();
//        for (String taboo : TabooService.tabooList) {
//            newName = newName.replaceAll(taboo, "*");
//        }
//        genre.setGenreName(newName);
//        return genre;
//    }
//
//    public Book bookReplacementLetters(Book book) {
//        String newName = book.getBookName();
//        for (String taboo : TabooService.tabooList) {
//            newName = newName.replaceAll(taboo, "*");
//        }
//        book.setBookName(newName);
//        return book;
//    }
}
