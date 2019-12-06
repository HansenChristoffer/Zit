package com.seedling.main;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Zithub {

    private HashMap<String, Zit> hub = new HashMap<>();

    public Zithub() {
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

    public int remove() {
        hub.clear();
        return 1;
    }

    public ArrayList<String> remove(String[] args) {
        ArrayList<String> keysNotFound = new ArrayList<>();

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

    public String show(String para) {
        String returnValue = "";

        if (para.equals("-f")) {
            for (Zit value :
                    hub.values()) {
                returnValue = returnValue.concat(value.getValue() + "\n");
            }

        } else if (para.equals("-n")) {
            for (String key :
                    hub.keySet()) {
                returnValue = returnValue.concat(key + "\n");
            }
        }

        return returnValue;
    }

    public int zip(String destination) throws ZipException, InterruptedException {
        if (!hub.isEmpty()) {
            System.out.println("\n$-> Commencing archiving Zits..");

            for (Zit zit :
                    hub.values()) {
                File path = new File(zit.getValue());
                ZipFile zipFile = new ZipFile(destination);

                if (path.isFile()) {
                    System.out.printf("$-> Adding %s file to archive...%n", new File(zit.getValue()).getName());
                    zipFile.addFile(path);
                } else if (path.isDirectory()){
                    System.out.printf("$-> Adding %s folder to archive...%n", new File(zit.getValue()).getName());
                    zipFile.addFolder(path);
                }

            }
            System.out.println("$-> Archiving completed!");
            return 1;

        } else {
            System.out.println("E-> Unable to zip Zits!");
            return -1;
        }
    }

    public int zip(String[] args, String destination) {
        return -1;
    }


}
