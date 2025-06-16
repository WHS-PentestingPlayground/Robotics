package com.WHS.Robotics.util;

public class SqlInjectionFilter {
    public static boolean isMalicious(String input) {
        if (input == null) return false;
        return input.contains("or")
                || input.contains("OR")
                || input.contains("oR")
                || input.contains("Or")
                || input.contains("/*")
                || input.contains("union")
                || input.contains("UNION")
                || input.contains("select")
                || input.contains("SELECT");
    }
}