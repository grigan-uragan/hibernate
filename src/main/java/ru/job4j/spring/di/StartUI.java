package ru.job4j.spring.di;

import org.springframework.stereotype.Component;

@Component
public class StartUI {
    private Store store;
    private ConsoleInput input;

    public StartUI(Store store, ConsoleInput input) {
        this.store = store;
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
