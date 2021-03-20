package ru.job4j.spring.di;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput {
    private Scanner scanner = new Scanner(System.in);

    public String askString() {
        System.out.println("enter your message: ");
        return scanner.nextLine();
    }
}
