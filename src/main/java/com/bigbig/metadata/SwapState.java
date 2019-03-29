package com.bigbig.metadata;

import java.util.stream.Stream;


public enum SwapState {

    TO_BE_SWAPPED(1),
    SENT_FOR_SWAP(2),
    SWAPPED(3),
    SWAP_FAILED(4),
    SWAP_NOT_NEEDED(5);

    private int code;

    SwapState(int code) {
        this.code = code;
    }

    public static SwapState valueOf(int code) {
        return Stream
                .of(values())
                .filter(s->s.code == code)
                .findFirst().orElseGet(null);

    }

    public int getState() {
        return code;
    }
}
