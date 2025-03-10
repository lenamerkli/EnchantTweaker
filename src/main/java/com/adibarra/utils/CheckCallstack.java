package com.adibarra.utils;


import com.adibarra.enchanttweaker.EnchantTweaker;


public class CheckCallstack {

    static final String[] STRINGS = {
        "MerchantEntity",
        "EnchantRandomlyLootFunction",
        "net.minecraft.class_3988",
        "net.minecraft.class_109",
    };

    public static boolean checkCallstack(String searchString) {
        return checkCallstack(new String[]{searchString});
    }

    public static boolean checkCallstack(String[] searchStrings) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String[] callstack = new String[stackTrace.length];
        for (int i = 0; i < stackTrace.length; i++) {
            callstack[i] = stackTrace[i].getClassName();
        }
        /* EnchantTweaker.LOGGER.error("Callstack: {}", String.join(", ", callstack)); */
        // check
        for (String s : callstack) {
            for (String searchString : searchStrings) {
                if (s.contains(searchString)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean merchantOrLoot() {
        return checkCallstack(STRINGS);
    }
}
