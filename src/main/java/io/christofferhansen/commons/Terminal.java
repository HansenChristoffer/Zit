package io.christofferhansen.commons;

import io.christofferhansen.commons.commands.CommandImpl;
import io.christofferhansen.handlers.CommandHandler;
import io.christofferhansen.xo.DataPacketXO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Terminal {

    private static final Logger logger = LogManager.getFormatterLogger(Terminal.class);
    private static final String REGEX = "^(?<cmd>\\w+)\\s?(?<arg>-[a-z])?\\s?(?<path>.+)?$";
    private final ZitHub zh = new ZitHub();
    private final CommandHandler commandHandler;
    private final CommandImpl commandImpl = new CommandImpl();
    private boolean end = false;


    public Terminal() {
        commandHandler = command -> {
            Pattern regexPattern = Pattern.compile(REGEX);
            Matcher matcher = regexPattern.matcher(command);
            if (matcher.matches()) {
                String cmd = matcher.group("cmd");

                if (cmd.equalsIgnoreCase("exit")) {
                    end = true;
                    return;
                }

                String arg = matcher.group("arg");
                String path = matcher.group("path");
                commandImpl.commence(DataPacketXO.create(zh, cmd, arg, path));
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
}
