package ru.job4j.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartUI {
    @Autowired
    private Store store;
    private ConsoleInput input;

    @Autowired
    public void setInput(ConsoleInput input) {
        this.input = input;
    }

    public void add() {
        String value = input.askString();
        store.add(value);
    }

    public void print() {
        store.getAll().forEach(System.out::println);
    }
}
