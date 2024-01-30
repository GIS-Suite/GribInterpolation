package org.example;

public class Main {
    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
        }
        System.out.println("Hello world!");
        System.out.println(args[1]);
    }
}