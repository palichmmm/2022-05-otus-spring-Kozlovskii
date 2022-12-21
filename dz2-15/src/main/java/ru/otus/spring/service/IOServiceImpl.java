package ru.otus.spring.service;

import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Scanner;

@Component
public class IOServiceImpl implements IOService {
    private final PrintStream output;
    private final Scanner input;

    public IOServiceImpl() {
        output = System.out;
        input = new Scanner(System.in);
    }

    @Override
    public void outputString(String text) {
        output.println(text);
    }

    @Override
    public String readString(String text) {
        System.out.print(text);
        return input.nextLine();
    }
}
