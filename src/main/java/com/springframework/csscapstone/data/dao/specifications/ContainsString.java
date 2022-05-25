package com.springframework.csscapstone.data.dao.specifications;

public class ContainsString {
    public static String contains(String search) { return String.format("%%%s%%", search); }
}
