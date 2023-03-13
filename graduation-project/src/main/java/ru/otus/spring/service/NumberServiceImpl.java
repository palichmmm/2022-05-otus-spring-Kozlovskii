package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.Mp3FileDescriptor;

import java.util.Collections;
import java.util.List;

@Service
public class NumberServiceImpl implements NumberService {

    public static final String NUMBER_SEPARATOR = "_";

    @Override
    public List<Mp3FileDescriptor> detectAndReplaceNumberFile(List<Mp3FileDescriptor> mp3FileDescriptorList) {
        for (Mp3FileDescriptor mp3FileDescriptor : mp3FileDescriptorList) {
            if (mp3FileDescriptor.getPosition() == null) {
                mp3FileDescriptor.setOutputName(mp3FileDescriptor.getOutputName().trim());
                int index = mp3FileDescriptor.getOutputName().indexOf(NUMBER_SEPARATOR);
                if (index > 0) {
                    String substr = mp3FileDescriptor.getOutputName().substring(0, index);
                    if (isNumber(substr)) {
                        if (mp3FileDescriptorList.stream().anyMatch(f -> f.getPositionInt() == Integer.parseInt(substr))) {
                            mp3FileDescriptor.setPosition(null);
                        } else {
                            mp3FileDescriptor.setPosition(substr);
                        }
                        mp3FileDescriptor.setOutputName(mp3FileDescriptor.getOutputName().replace(substr + NUMBER_SEPARATOR, ""));
                    }
                }
            }
        }
        Collections.sort(mp3FileDescriptorList);
        return mp3FileDescriptorList;
    }

    @Override
    public List<Mp3FileDescriptor> renumbering(List<Mp3FileDescriptor> list) {
        int listSize = list.size();
        String pattern = "%0" + String.valueOf(listSize).length() + "d";
        for (Mp3FileDescriptor mp3FileDescriptor : list) {
            if (mp3FileDescriptor.getPositionInt() != 0) {
                mp3FileDescriptor.setPosition(String.format(pattern, mp3FileDescriptor.getPositionInt()));
            }
        }
        for (int i = 1; i <= listSize; i++) {
            int number = i;
            if (list.stream().noneMatch(file -> file.getPositionInt() == number)) {
                if (list.stream().anyMatch(file -> file.getPositionInt() == 0)) {
                    for (Mp3FileDescriptor mp3FileDescriptor : list) {
                        if (mp3FileDescriptor.getPositionInt() == 0) {
                            mp3FileDescriptor.setPosition(String.format(pattern, i));
                            break;
                        }
                    }
                } else {
                    for (Mp3FileDescriptor mp3FileDescriptor : list) {
                        if (mp3FileDescriptor.getPositionInt() > number) {
                            mp3FileDescriptor.setPosition(String.format(pattern, i));
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

}
