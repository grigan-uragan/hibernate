package ru.job4j.spring.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.job4j.spring.di");
        context.refresh();
        StartUI startUI = context.getBean(StartUI.class);
        startUI.add();
        startUI.add();
        startUI.print();
    }
}
