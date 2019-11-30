package com.seedling.main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Zithub {

    private HashMap<String, String> hub;

    public Zithub() {
        hub = new HashMap<>();
    }

    public int add(String path) {
        if (path.contains("/")) {
            String[] temp = path.split("/");
            String name = temp[temp.length - 1];

            hub.put(name, path);
            return 1;
        }

        return -1;
    }

    public int remove() {
        hub.clear();
        return 1;
    }

    public List<String> remove(String[] args) {
        List<String> keysNotFound = new ArrayList<>();

        if (args.length > 0) {
            for (String key : args) {
                try {
                    hub.remove(key);
                } catch(Exception e) {
                    keysNotFound.add(key);
                }
            }
        }

        return keysNotFound;
    }

    public String show(String para) {
        String returnValue = "";

        if (para.equals("-f")) {
            for (String value :
                    hub.values()) {
                returnValue = returnValue.concat(value + "\n");
            }

        } else if (para.equals("-n")) {

            for (String key :
                    hub.keySet()) {
                returnValue = returnValue.concat(key + "\n");
            }
        }

        return returnValue;
    }

    public int zip(String destination) {
        return -1;
    }

    public int zip(String[] args, String destination) {
        return -1;
    }


}
