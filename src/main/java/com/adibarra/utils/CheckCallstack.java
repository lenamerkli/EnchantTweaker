package com.adibarra.utils;


import com.adibarra.enchanttweaker.EnchantTweaker;

import java.util.Arrays;
import java.util.stream.Collectors;


public class CheckCallstack {

    static final String[] STRINGS = {
        "MerchantEntity",
        "EnchantRandomlyLootFunction",
        "net.minecraft.class_3988",
        "net.minecraft.class_109",
    };

    public static boolean checkCallstack(String searchString) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String[] callstack = new String[stackTrace.length];
        for (int i = 0; i < stackTrace.length; i++) {
            callstack[i] = stackTrace[i].getClassName();
        }
        /* EnchantTweaker.LOGGER.error("Callstack: {}", String.join(", ", callstack)); */
        // check
        for (String s : callstack) {
            if (s.contains(searchString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean merchantOrLoot() {
        for (String s : STRINGS) {
            if (checkCallstack(s)) {
                return true;
            }
        }
        return false;
    }
}
