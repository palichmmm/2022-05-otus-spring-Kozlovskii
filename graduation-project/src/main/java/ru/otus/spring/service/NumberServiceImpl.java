package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.File;

import java.util.Collections;
import java.util.List;

@Service
public class NumberServiceImpl implements NumberService {

    public static final String NUMBER_SEPARATOR = "_";

    @Override
    public List<File> detectAndReplaceNumberFile(List<File> fileList) {
        for (File file : fileList) {
            if (file.getSerialNumber() == null) {
                file.setOutputName(file.getOutputName().trim());
                int index = file.getOutputName().indexOf(NUMBER_SEPARATOR);
                if (index > 0) {
                    String substr = file.getOutputName().substring(0, index);
                    if (isNumber(substr)) {
                        if (fileList.stream().anyMatch(f -> f.getSerialNumberInt() == Integer.parseInt(substr))) {
                            file.setSerialNumber(null);
                        } else {
                            file.setSerialNumber(substr);
                        }
                        file.setOutputName(file.getOutputName().replace(substr + NUMBER_SEPARATOR, ""));
                    }
                }
            }
        }
        Collections.sort(fileList);
        return fileList;
    }

    @Override
    public List<File> renumbering(List<File> list) {
        int listSize = list.size();
        String pattern = "%0" + String.valueOf(listSize).length() + "d";
        for (File file : list) {
            if (file.getSerialNumberInt() != 0) {
                file.setSerialNumber(String.format(pattern, file.getSerialNumberInt()));
            }
        }
        for (int i = 1; i <= listSize; i++) {
            int number = i;
            if (list.stream().noneMatch(file -> file.getSerialNumberInt() == number)) {
                if (list.stream().anyMatch(file -> file.getSerialNumberInt() == 0)) {
                    for (File file : list) {
                        if (file.getSerialNumberInt() == 0) {
                            file.setSerialNumber(String.format(pattern, i));
                            break;
                        }
                    }
                } else {
                    for (File file : list) {
                        if (file.getSerialNumberInt() > number) {
                            file.setSerialNumber(String.format(pattern, i));
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
