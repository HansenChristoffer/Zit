package io.christofferhansen;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ZitHub {

    private static final Logger logger = LogManager.getFormatterLogger(ZitHub.class);
    private final HashMap<String, Zit> hub = new HashMap<>();

    public ZitHub() {
    }

    public int add(String path) {
        if (path.contains("/")) {
            String[] temp = path.split("/");
            String name = temp[temp.length - 1];

            if (hub.containsKey(name)) {
                name = name.concat("[" + ((int) (Math.random() * 1000) + 1) + "]");
            }

            hub.put(name, new Zit(name, path));
            return 1;
        }

        return -1;
    }

    public boolean remove() {
        hub.clear();
        return true;
    }

    public List<String> remove(String[] args) {
        List<String> keysNotFound = new ArrayList<>();

        /*
         * Zit will only remove by the String key that the Map has. Therefore user can not remove by fullpath.
         * Plus if we would try to add 2 different files but with the same name then we would be met with the issue
         * of having 2 entries trying to have the same key. Keys are unique. Either change what key is e.g.
         * should it be the fullpath?
         * Or should there be added a random number on duplicates key value? (Which it already does)
         *
         * Another issue is, should we allow duplicates of the same fullpath? Should I be able to have a
         * ZitHub with 100 Zits with the fullpath: /home/johnny/bravo/README.md? Or should we prevent this and make
         * it unique?
         */

        if (args.length > 0) {
            for (String key : args) {
                if (hub.containsKey(key)) {
                    hub.remove(key);
                } else {
                    keysNotFound.add(key);
                }
            }
        }

        return keysNotFound;
    }

    public String show() {
        StringBuilder returnValue = new StringBuilder();

        for (Zit e : hub.values()) {
            returnValue.append(String.format("[%s] %s%n",
                    e.getKey(),
                    e.getValue()));
        }

        return returnValue.toString();
    }

    public int zip(String destination) throws ZipException, InterruptedException {
        if (!hub.isEmpty()) {
            System.out.println("\n$-> Archiving Zits..");

            for (Zit zit : hub.values()) {
                File path = new File(zit.getValue());
                ZipFile zipFile = null;

                try {
                    if (path.exists()) {
                        zipFile = new ZipFile(destination);
                        BasicFileAttributes basicFileAttributes = Files.readAttributes(path.toPath(), BasicFileAttributes.class);

                        if (!basicFileAttributes.isSymbolicLink()) {
                            if (basicFileAttributes.isRegularFile()) {
                                System.out.printf("$-> Adding %s file to archive...%n", zit.getValue());
                                zipFile.addFile(path);
                            } else if (basicFileAttributes.isDirectory()) {
                                System.out.printf("$-> Adding %s folder to archive...%n", zit.getValue());
                                zipFile.addFolder(path);
                            }
                        } else {
                            System.out.println("$-> Symbolic links are not allowed!");
                        }
                    } else {
                        System.out.println("$-> Tried to add " + path.getAbsolutePath() + " but it does not exist!");
                    }
                } catch (IOException ioe) {
                    logger.error("Error occurred while trying to archive and save the data!", ioe);
                } finally {
                    if (zipFile != null) {
                        try {
                            zipFile.close();
                        } catch (IOException ioe) {
                            logger.error("Issues with closing ZipFile instance!", ioe);
                        }
                    }
                }
            }

            System.out.println("$-> Archiving completed!");
            remove();
            return 1;
        } else {
            System.out.println("E-> Unable to archive Zits!");
            return -1;
        }
    }

    public int zip(String[] args, String destination) {
        return -1;
    }


}
