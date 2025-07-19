package com.WHS.Robotics.util;

public class SqlInjectionFilter {
    public static final String DEBUG_REPO_CLASS = "com.WHS.Robotics.repository.ProductRepository";
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