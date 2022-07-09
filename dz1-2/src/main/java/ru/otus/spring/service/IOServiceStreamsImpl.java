package ru.otus.spring.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
public class IOServiceStreamsImpl implements IOService{
    private final PrintStream output;
    private final Scanner input;

    public IOServiceStreamsImpl(PrintStream outputStream, InputStream inputStream) {
        output = outputStream;
        input = new Scanner(inputStream);
    }

    public void outputString(String text) {
        output.println(text);
    }

    @Override
    public String readString(String text) {
        System.out.print(text);
        return input.nextLine();
    }
}
