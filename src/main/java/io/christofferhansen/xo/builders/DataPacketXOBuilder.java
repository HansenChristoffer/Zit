package io.christofferhansen.xo.builders;

import io.christofferhansen.commons.ZitHub;
import io.christofferhansen.xo.DataPacketXO;

public class DataPacketXOBuilder {
    private ZitHub zithub;
    private String command;
    private String arg;
    private String path;

    public DataPacketXOBuilder zithub(ZitHub zithub) {
        this.zithub = zithub;
        return this;
    }

    public DataPacketXOBuilder command(String command) {
        this.command = command;
        return this;
    }

    public DataPacketXOBuilder arg(String arg) {
        this.arg = arg;
        return this;
    }

    public DataPacketXOBuilder path(String path) {
        this.path = path;
        return this;
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

    public DataPacketXO build() {
        return new DataPacketXO(this);
    }
}