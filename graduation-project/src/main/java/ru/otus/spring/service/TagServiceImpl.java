package ru.otus.spring.service;

import com.mpatric.mp3agic.*;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.TagFile;
import ru.otus.spring.repository.TagRepository;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagFile saveTagToDb(TagFile tagFile) {
        return tagRepository.save(tagFile);
    }

    @Override
    public void saveTagToFile(TagFile tagFile) {
    }

    @Override
    public TagFile loadTagFromFile(Path path) {
        try {
            Mp3File mp3file = new Mp3File(path);
            TagFile tagFile = new TagFile();
            tagFile.setFileName(path.getFileName().toString());
            tagFile.setPlayTime(mp3file.getLengthInSeconds());
            tagFile.setBitrate(mp3file.getBitrate());
            tagFile.setSampleRate(mp3file.getSampleRate());
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                tagFile.setTrack(id3v2Tag.getTrack());
                tagFile.setArtist(id3v2Tag.getArtist());
                tagFile.setTitle(id3v2Tag.getTitle());
                tagFile.setAlbum(id3v2Tag.getAlbum());
                tagFile.setYear(id3v2Tag.getYear());
                tagFile.setGenre(id3v2Tag.getGenreDescription());
                return tagFile;
            } else if (mp3file.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                tagFile.setTrack(id3v1Tag.getTrack());
                tagFile.setArtist(id3v1Tag.getArtist());
                tagFile.setTitle(id3v1Tag.getTitle());
                tagFile.setAlbum(id3v1Tag.getAlbum());
                tagFile.setYear(id3v1Tag.getYear());
                tagFile.setGenre(id3v1Tag.getGenreDescription());
                return tagFile;
            }
            return null;
        } catch (InvalidDataException | UnsupportedTagException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearTagToFile(String id) {

    }

    @Override
    public void deleteByFileName(String fileName) {
        tagRepository.deleteByFileName(fileName);
    }
}
