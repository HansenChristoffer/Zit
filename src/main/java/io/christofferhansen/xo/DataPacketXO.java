package io.christofferhansen.xo;

import io.christofferhansen.commons.ZitHub;
import io.christofferhansen.xo.builders.DataPacketXOBuilder;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DataPacketXO implements Serializable {

    private final long id;
    private final ZitHub zithub;
    private final String command;
    private final String arg;
    private final String path;

    public DataPacketXO(DataPacketXOBuilder builder) {
        this.id = UUID.randomUUID().getMostSignificantBits();
        this.zithub = builder.getZithub();
        this.command = builder.getCommand();
        this.arg = builder.getArg();
        this.path = builder.getPath();
    }

    private DataPacketXO(ZitHub zithub, String command, String arg, String path) {
        this.id = UUID.randomUUID().getMostSignificantBits();
        this.zithub = zithub;
        this.command = command;
        this.arg = arg;
        this.path = path;
    }

    public static DataPacketXO create(ZitHub zithub, String command, String arg, String path) {
        return new DataPacketXO(zithub, command, arg, path);
    }

    public long getId() {
        return id;
    }

    public ZitHub getZithub() {
        return zithub;
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataPacketXO that)) return false;
        return id == that.id
                && zithub.equals(that.zithub)
                && command.equals(that.command)
                && Objects.equals(arg, that.arg)
                && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zithub, command, arg, path);
    }

    @Override
    public String toString() {
        return "DataPacketXO{" +
                "id=" + id +
                ", zithub=" + zithub +
                ", command='" + command + '\'' +
                ", arg='" + arg + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
