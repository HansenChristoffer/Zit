package com.seedling.main;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {

    private static final Scanner in = new Scanner(System.in);
    private static boolean end = false;
    private static Zithub zh = new Zithub();

    public Terminal() {
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
        cmd = cmd.replaceAll("/", File.separator);

        if (cmd.equals("exit")) {
            end = true;
        } else if (cmd.startsWith("add ")) {
            String path = cmd.substring(3);
            path = path.replaceFirst(" ", "");

            if (!path.isEmpty()) {
                cmdAdd(path);
            } else {
                System.out.println("$-> Path to file or folder not given!");
            }

        } else if (cmd.equals("remove -a") || (cmd.startsWith("remove -z "))) {
            cmdRemove(cmd);

        } else if (cmd.equals("show") ||
                cmd.startsWith("show ") && (cmd.substring(5).startsWith("-f") || cmd.substring(5).startsWith("-n"))) {
            cmdShow(cmd);

        } else if (cmd.equals("zip")) {
            int randomNumber = ((int) (Math.random() * 10000) + 1);
            String destination = "Zit-[" + randomNumber + "].zip";
            cmdZip(destination);
        } else {
            System.out.println("$-> No such command!");
        }
    }

    private static void cmdAdd(String param) {
        File f = new File(param);

        if (f.exists()) {
            zh.add(param);
        } else {
            System.out.println("$-> No such file or folder!");
        }
    }

    private static void cmdRemove(String param) {
        if (param.substring(7).startsWith("-a")) {
            int i = zh.remove();
            if (i == 1) {
                System.out.println("$-> All zits has been removed.");
            } else {
                System.out.println("$-> Could not remove any zits.");
            }

        } else {
            String[] zits = param.substring(10).split(",");

            for (int i = 0; i < zits.length; i++) {
                if (zits[i].startsWith(" ")) {
                    zits[i] = zits[i].replaceFirst(" ", "");
                }
            }

            ArrayList<String> temp = zh.remove(zits);
            System.out.println("$-> Zits that was able to be removed was removed.");
            if (!temp.isEmpty()) {
                System.out.println("These zit keys did not exist and therefore was not removed:\n");

                for (String s :
                        temp) {
                    System.out.println("\t" + s);
                }
                System.out.println();
            }

        }
    }

    private static void cmdShow(String param) {
        System.out.println("On Zithub content\nType \"show -f\" to get the paths to the files or folders.\n");

        if (param.equals("show") || param.equals("show -n")) {
            System.out.println(zh.show("-n"));
        } else if (param.equals("show -f")) {
            System.out.println(zh.show("-f"));

        } else {
            System.out.println("\tZithub is empty!\n\n");
        }
    }

    private static void cmdZip(String destination) {
        try {
            System.out.println(zh.zip(destination));
        } catch (InterruptedException | ZipException ioe) {
            System.err.println(ioe.getMessage());
        }
    }


}
