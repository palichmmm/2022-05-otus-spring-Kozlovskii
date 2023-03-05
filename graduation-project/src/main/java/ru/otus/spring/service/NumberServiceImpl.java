package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class NumberServiceImpl implements NumberService {

    public static final String NUMBER_SEPARATOR = "_";

    @Override
    public List<File> detectAndReplaceNumberFile(List<File> fileList) {
        List<File> listNotNumber = new ArrayList<>();
        List<File> listNumber = new ArrayList<>();

        for (File file : fileList) {
            file.setOutputName(file.getOutputName().trim());
            int index = file.getOutputName().indexOf(NUMBER_SEPARATOR);
            if (index > 0) {
                String substr = file.getOutputName().substring(0, index);
                if (isNumber(substr)) {
                    file.setSerialNumber(substr);
                    file.setOutputName(file.getOutputName().replace(substr + NUMBER_SEPARATOR, ""));
                    listNumber.add(file);
                } else {
                    listNotNumber.add(file);
                }
            } else {
                listNotNumber.add(file);
            }
        }
        Collections.sort(fileList);
        return fileList;
    }

    @Override
    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
