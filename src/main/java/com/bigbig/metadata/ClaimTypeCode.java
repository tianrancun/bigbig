package com.bigbig.metadata;

import java.util.stream.Stream;


public enum ClaimTypeCode {

    SUPP(1),
    RCTR(2),
    JRCTR(3),
    RCLM(4),
    MKDN(5),
    INTR(6);

    private final int code;

    ClaimTypeCode(int code) {
        this.code = code;
    }

    /**
     * Returns the type code of the code.
     *
     * @param code
     * @return
     */
    public static ClaimTypeCode valueOf(int code) {
        return Stream.of(values()).filter(ctc -> ctc.code == code).findFirst().orElse(null);
    }

    public int getCode() {
        return code;
    }
}
