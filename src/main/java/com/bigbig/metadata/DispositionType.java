package com.bigbig.metadata;

import java.util.stream.Stream;

/**
 * dispose
 */
public enum DispositionType {

    SHIP(1), HOLD(2), DISP(3), DONO(4), VIRT(5);

    private final int code;

    DispositionType(int code) {
        this.code = code;
    }

    public static DispositionType valueOf(int code) {
        return Stream
            .of(values())
            .filter(dis -> dis.code == code)
            .findFirst()
            .orElse(null);
    }

    public int getCode() {
        return code;
    }
}
