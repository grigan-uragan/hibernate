package ru.job4j.spring.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.job4j.spring.di");
        context.refresh();
        StartUI startUI = context.getBean(StartUI.class);
        StartUI another = context.getBean(StartUI.class);
        ConsoleInput input = context.getBean(ConsoleInput.class);
        ConsoleInput anotherInput = context.getBean(ConsoleInput.class);

        System.out.println("StartUI has scope prototype, startUI == another ? "
                + (startUI == another));
        System.out.println("Console input has default scope singleton, input == anotherInput? "
                + (input == anotherInput));
    }
}
