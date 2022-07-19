package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleServiceImpl implements LocaleService{
    private final MessageSource messageSource;

    public LocaleServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String keyLocale) {
        return messageSource.getMessage(keyLocale, null, Locale.getDefault());
    }
}
