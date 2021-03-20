package ru.job4j.spring.di;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.reg(Store.class);
        context.reg(ConsoleInput.class);
        context.reg(StartUI.class);
        StartUI startUI = context.get(StartUI.class);
        startUI.add();
        startUI.add();
        startUI.print();
    }
}
