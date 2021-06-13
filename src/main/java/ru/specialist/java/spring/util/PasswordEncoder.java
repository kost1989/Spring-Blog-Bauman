package ru.specialist.java.spring.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class PasswordEncoder {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)){
            System.out.println(new BCryptPasswordEncoder().encode(scanner.nextLine()));
        }
    }
}
