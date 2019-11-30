package com.seedling.main;

import java.util.HashMap;

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
        return -1;
    }

    public int remove(String[] args) {
        return -1;
    }

    public String show(String para) {
        return "";
    }

    public int zip(String destination) {
        return -1;
    }

    public int zip(String[] args, String destination) {
        return -1;
    }


}
