package io.christofferhansen.commons.commands;

import io.christofferhansen.annotations.NotNull;
import io.christofferhansen.xo.DataPacketXO;

public interface CommandRepository {

    void commence(@NotNull DataPacketXO dpXo);

}
