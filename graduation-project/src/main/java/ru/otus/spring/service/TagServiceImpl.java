package ru.otus.spring.service;

import com.mpatric.mp3agic.*;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.Tag;
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
    public Tag saveTagToDb(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void saveTagToFile(Tag tag) {
    }

    @Override
    public Tag loadTagFromFile(Path path) {
        try {
            Mp3File mp3file = new Mp3File(path);
            Tag tag = new Tag();
            tag.setFileName(path.getFileName().toString());
            tag.setPlayTime(mp3file.getLengthInSeconds());
            tag.setBitrate(mp3file.getBitrate());
            tag.setSampleRate(mp3file.getSampleRate());
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                tag.setTrack(id3v2Tag.getTrack());
                tag.setArtist(id3v2Tag.getArtist());
                tag.setTitle(id3v2Tag.getTitle());
                tag.setAlbum(id3v2Tag.getAlbum());
                tag.setYear(id3v2Tag.getYear());
                tag.setGenre(id3v2Tag.getGenreDescription());
                return tag;
            } else if (mp3file.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                tag.setTrack(id3v1Tag.getTrack());
                tag.setArtist(id3v1Tag.getArtist());
                tag.setTitle(id3v1Tag.getTitle());
                tag.setAlbum(id3v1Tag.getAlbum());
                tag.setYear(id3v1Tag.getYear());
                tag.setGenre(id3v1Tag.getGenreDescription());
                return tag;
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
