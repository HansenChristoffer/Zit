package io.christofferhansen.commons.commands;

import io.christofferhansen.annotations.NotNull;
import io.christofferhansen.xo.DataPacketXO;
import net.lingala.zip4j.exception.ZipException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Random;

public class CommandImpl implements CommandRepository {

    private static final Logger logger = LogManager.getFormatterLogger(CommandImpl.class);

    @Override
    public void commence(@NotNull DataPacketXO dpXo) {
        Commands c = Commands.fromName(dpXo.getCommand());

        if (c != null) {
            switch (c) {
                case ADD_COMMAND -> add(dpXo);
                case REMOVE_COMMAND -> remove(dpXo);
                case SHOW_COMMAND -> show(dpXo);
                case ZIP_COMMAND -> zip(dpXo);
                default -> System.out.println("$-> No such command!");
            }
        }
    }

    private void add(@NotNull DataPacketXO dpXo) {
        File f = new File(dpXo.getPath());

        if (f.exists()) {
            dpXo.getZithub().add(dpXo.getPath());
        } else {
            System.out.println("$-> No such file or folder!");
        }
    }

    private void remove(@NotNull DataPacketXO dpXo) {
        if (dpXo.getArg() != null && !dpXo.getArg().isBlank()) {
            switch (dpXo.getArg()) {
                case "-a" -> removeAll(dpXo);
                case "-f" -> removeSpecific(dpXo);
                default -> System.out.println("$-> Unknown remove parameter!");
            }
        } else {
            System.out.println("$-> Parameter is not allowed to be empty nor null!");
        }
    }

    private void removeAll(@NotNull DataPacketXO dpXo) {
        System.out.println(dpXo.getZithub().remove()
                ? "$-> Have removed all Zits in ZitHub!"
                : "$-> Unable to remove all Zits from ZitHub!");
    }

    private void removeSpecific(@NotNull DataPacketXO dpXo) {
        if (dpXo.getPath() != null && !dpXo.getPath().isBlank()) {
            String[] zits = dpXo.getPath().split(";");

            for (int i = 0; i < zits.length; i++) {
                if (zits[i].startsWith(" ")) {
                    zits[i] = zits[i].replaceFirst(" ", "");
                }
            }

            List<String> keysNotFound = dpXo.getZithub().remove(zits);
            System.out.println("$-> Zits that was able to be removed was removed.");

            if (!keysNotFound.isEmpty()) {
                System.out.println("$-> These zit keys did not exist and therefore was not removed:\n");

                for (String s : keysNotFound) {
                    System.out.println("\t" + s);
                }
            }
        } else {
            System.out.println("$-> No Zits to be removed declared!");
        }
    }

    private void show(@NotNull DataPacketXO dpXo) {
        System.out.println("-- Current ZitHub content --\n" + dpXo.getZithub().show());
    }

    private void zip(@NotNull DataPacketXO dpXo) {
        try {
            Random random = new Random();
            int randomNumber = random.nextInt(10000) + 1;
            String destination = "Zit-[" + randomNumber + "].zip";

            dpXo.getZithub().zip(destination);
        } catch (InterruptedException | ZipException ioe) {
            logger.error("Issues with archiving...", ioe);
        }
    }

}
