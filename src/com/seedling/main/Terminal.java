package com.seedling.main;

import java.util.Scanner;

public class Terminal {

    private static final Scanner in = new Scanner(System.in);
    private static boolean end = false;
    private static Zithub zh;

    public Terminal() {
        Zithub zh = new Zithub();
    }

    public static void start() {
        System.out.println("-- Zit --");

        while (!end) {
            System.out.print("~$ ");
            String cmd = in.nextLine();
            call(cmd);
        }

    }

    private static int call(String cmd) {
        int returnValue = -1;

        if (cmd.equals("exit")) {
            end = true;
        } else if (cmd.startsWith("add")) {
            String path = cmd.substring(4);



        } else if (cmd.startsWith("remove")) {

        }

        return returnValue;
    }

}
