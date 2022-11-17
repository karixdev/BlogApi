package com.github.karixdev.blogapi.util;

import org.springframework.stereotype.Component;

@Component
public class NameCaseConverter {

    public String camelToSnake(String name) {
        StringBuilder resultBuilder = new StringBuilder();

        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c)) {
                resultBuilder.append("_");
            }

            resultBuilder.append(Character.toLowerCase(c));
        }

        return resultBuilder.toString();
    }

}
