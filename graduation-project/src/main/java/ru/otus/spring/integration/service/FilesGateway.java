//package ru.otus.string.integration.service;
//
//import org.springframework.integration.annotation.Gateway;
//import org.springframework.integration.annotation.MessagingGateway;
//import org.springframework.web.multipart.MultipartFile;
//import ru.otus.string.models.File;
//
//import java.util.List;
//
//@MessagingGateway
//public interface FilesGateway {
//
//    @Gateway(requestChannel = "uploadChannel", replyChannel = "outputFilesChannel")
//    List<File> uploadFiles(MultipartFile[] multipartFiles);

//    @Gateway(requestChannel = "genreChannel", replyChannel = "outputGenreChannel")
//    List<Genre> genreReplacementLetters(List<Genre> list);
//
//    @Gateway(requestChannel = "bookChannel", replyChannel = "outputBookChannel")
//    List<Book> bookReplacementLetters(List<Book> list);

//}
