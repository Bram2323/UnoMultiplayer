package net.server;

import java.util.function.Consumer;

public enum Command implements Consumer<?>{
    HANDSHAKE((String s) -> setOnHandshake(s)),
    START_GAME((String s) -> setOnStartGame(s));

    private Command(Consumer<?> onAction){
        this.onAction = onAction;
    }

    private final Consumer<?> onAction;
}
