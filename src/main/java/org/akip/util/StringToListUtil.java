package org.akip.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringToListUtil {

    public static List<String> stringToList(String content) {
        return stringToList(content, ",");
    }

    public static List<String> stringToList(String content, String delimiter) {
        if (StringUtils.isBlank(content)) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>();
        Arrays
                .stream(content.split(delimiter))
                .forEach(
                        candidateGroup -> {
                            if (candidateGroup.length() > 0) {
                                list.add(candidateGroup);
                            }
                        }
                );
        return list;
    }
}
