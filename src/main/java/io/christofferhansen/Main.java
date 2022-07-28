package io.christofferhansen;

import io.christofferhansen.commons.Terminal;

import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String VERSION = "2.0-snapshot";
    private static final String DEVELOPER = "Christoffer Hansen";
    private static final String EMAIL = "chris.hansen.ch@outlook.com";

    public static void main(String[] args) {
        List<String> arguments = Arrays.asList(args);

        if (!arguments.isEmpty()) {
            if (arguments.contains("--version")) {
                System.out.println(VERSION);
            }
            if (arguments.contains("--about")) {
                System.out.println(DEVELOPER + "\n" + EMAIL);
            }
        } else {
            new Terminal().start();
        }
    }
}
