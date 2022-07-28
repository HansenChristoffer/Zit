package io.christofferhansen;

import net.lingala.zip4j.exception.ZipException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Terminal {

    private static final Logger logger = LogManager.getFormatterLogger(Terminal.class);
    private static final String REGEX = "^(?<cmd>\\w+)\\s?(?<arg>-[a-z])?\\s?(?<path>.+)?$";
    private final ZitHub zh = new ZitHub();
    private final CommandHandler commandHandler;
    private boolean end = false;


    public Terminal() {
        commandHandler = command -> {
            command = command.replace("/", File.separator);

            Pattern regexPattern = Pattern.compile(REGEX);
            Matcher matcher = regexPattern.matcher(command);
            if (matcher.matches()) {
                String cmd = matcher.group("cmd");
                String arg = matcher.group("arg");
                String path = matcher.group("path");

                switch (cmd) {
                    case "exit" -> end = true;
                    case "add" -> {
                        if (path != null && !path.isBlank()) {
                            commandAdd(path);
                        } else {
                            // PATH IS BLANK/EMPTY!
                            System.out.println("$-> You need to declare the path to the " +
                                    "file or folder you want to add to ZitHub!");
                        }
                    }
                    case "remove" -> {
                        if (arg != null && !arg.isBlank()) {
                            if (arg.equalsIgnoreCase("-a")) {
                                commandRemove("-a", null);
                            } else if (arg.equalsIgnoreCase("-f")) {
                                if (path != null && !path.isBlank()) {
                                    commandRemove("-f", path);
                                } else {
                                    // Path IS blaNk/EmPTy!
                                    System.out.println("$-> You need to declare the path to the " +
                                            "file or folder you want to remove from ZitHub!");
                                }
                            } else {
                                // UNKNOWN ARGUMENT!
                                System.out.println("$-> Unknown parameter. Today there exists the parameters -f and -a");
                            }
                        } else {
                            // ARG IS BLANK/EMPTY!
                            System.out.println("$-> No parameter given. Today there exists the parameters -f and -a");
                        }
                    }
                    case "show" -> commandShow();
                    case "zip" -> {
                        Random random = new Random();
                        int randomNumber = random.nextInt(10000) + 1;
                        String destination = "Zit-[" + randomNumber + "].zip";
                        commandZip(destination);
                    }
                    default -> System.out.println("$-> No such command!");
                }
            } else {
                System.out.println("$-> No such command!");
            }
        };
    }

    public void start() {
        System.out.println("-- Zit --");

        try (Scanner in = new Scanner(System.in)) {
            while (!end) {
                System.out.print("~$ ");
                commandHandler.onCommandReceived(in.nextLine());
            }
        }
    }

    private void commandAdd(String path) {
        File f = new File(path);

        if (f.exists()) {
            zh.add(path);
        } else {
            System.out.println("$-> No such file or folder!");
        }
    }

    private void commandRemove(String param, String path) {
        switch (param) {
            case "-a" -> {
                System.out.println(zh.remove()
                        ? "$-> Have removed all Zits in ZitHub!"
                        : "$-> Unable to remove all Zits from ZitHub!");
            }
            case "-f" -> {
                if (!path.isBlank()) {
                    String[] zits = path.split(";");

                    for (int i = 0; i < zits.length; i++) {
                        if (zits[i].startsWith(" ")) {
                            zits[i] = zits[i].replaceFirst(" ", "");
                        }
                    }

                    List<String> keysNotFound = zh.remove(zits);
                    System.out.println("$-> Zits that was able to be removed was removed.");
                    if (!keysNotFound.isEmpty()) {
                        System.out.println("$-> These zit keys did not exist and therefore was not removed:\n");

                        for (String s : keysNotFound) {
                            System.out.println("\t" + s);
                        }
                        System.out.println();
                    }
                } else {
                    // Path is empty and therefore can not remove specific. Inform user!
                    System.out.println("$-> No Zits to be removed declared!");
                }
            }
            default -> {
                System.out.println("$-> Unknown remove parameter!");
            }
        }
    }

    private void commandShow() {
        System.out.println("-- Current ZitHub content --");

        System.out.println(zh.show());
    }

    private void commandZip(String destination) {
        try {
            zh.zip(destination);
        } catch (InterruptedException | ZipException ioe) {
            logger.error("Issues with archiving...", ioe);
        }
    }


}
