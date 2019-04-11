package com.bigbig;


import com.bigbig.metadata.DispositionType;
import org.junit.Test;

public class TestMetadata {
    @Test
    public void test() {
//        System.out.println(DispositionType.valueOf("SHIP"));
//        System.out.println(DispositionType.valueOf(1));
//        System.out.println(DispositionType.valueOf("HOLD"));
//        System.out.println(DispositionType.valueOf(6));
        System.out.println(isCorrectableClaim(DispositionType.DISP));
        System.out.println(isCorrectableClaim(DispositionType.HOLD));
        System.out.println(isCorrectableClaim(DispositionType.SHIP));
    }

    public Boolean isCorrectableClaim(DispositionType state) {
        return DispositionType.DISP == state || DispositionType.SHIP == state ? Boolean.TRUE : Boolean.FALSE;
    }
}