package ru.job4j.spring.di;

import java.util.Scanner;

public class ConsoleInput {
    private Scanner scanner = new Scanner(System.in);

    public String askString() {
        System.out.println("enter your message: ");
        return scanner.nextLine();
    }
}
