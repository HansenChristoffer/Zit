package io.christofferhansen.commons.commands;

public enum Commands {

    ADD_COMMAND(),
    REMOVE_COMMAND(),
    SHOW_COMMAND(),
    ZIP_COMMAND();

    Commands() {
    }

    public static Commands fromName(final String name) {
        for (Commands c : Commands.values()) {
            if (c.name().contains(name.toUpperCase())) {
                return c;
            }
        }

        return null;
    }

}
