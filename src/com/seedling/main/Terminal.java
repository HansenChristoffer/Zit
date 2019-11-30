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


        } else if (cmd.startsWith("show")) {
            cmdShow(cmd);

        } else if (cmd.startsWith("zip")) {

        }

        return returnValue;
    }

    private static void cmdShow(String arg) {
        System.out.println("On Zithub content\n\n");

        if (arg.substring(4).startsWith("-n")) {
            System.out.println(zh.show("-n"));

        } else if (arg.substring(4).startsWith("-f") || arg.equals("show")) {
            System.out.println(zh.show("-f"));

        } else {
            System.out.println("\tZithub is empty!");
        }
    }


}
