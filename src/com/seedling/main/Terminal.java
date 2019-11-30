package com.seedling.main;

import java.io.File;
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

    private static void call(String cmd) {
        if (cmd.equals("exit")) {
            end = true;
        } else if (cmd.startsWith("add")) {
            String path = cmd.substring(4);
            cmdAdd(path);

        } else if (cmd.startsWith("remove")) {


        } else if (cmd.startsWith("show")) {
            cmdShow(cmd);

        } else if (cmd.startsWith("zip")) {

        }
    }

    private static void cmdAdd(String argu) {
        File f = new File(argu);

        if (f.exists()) {
            zh.add(argu);
        } else {
            System.out.println("$-> No such file or folder!");
        }
    }

    private static void cmdShow(String argu) {
        System.out.println("On Zithub content\n\n");

        if (argu.substring(4).startsWith("-n")) {
            System.out.println(zh.show("-n"));

        } else if (argu.substring(4).startsWith("-f") || argu.equals("show")) {
            System.out.println(zh.show("-f"));

        } else {
            System.out.println("\tZithub is empty!");
        }
    }


}
