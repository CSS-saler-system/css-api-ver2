package com.springframework.csscapstone.utils.languges_conveter_utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class VietnameseToEnglish {

    public static String vietVietnameseToEnglish(String convertString) {
        String nfdNormalizedString = Normalizer.normalize(convertString, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
