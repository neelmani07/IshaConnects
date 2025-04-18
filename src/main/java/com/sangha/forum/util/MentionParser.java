package com.sangha.forum.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class MentionParser {
    private static final Pattern MENTION_PATTERN = Pattern.compile("@([a-zA-Z0-9._-]+)");

    public static List<String> extractMentions(String text) {
        List<String> mentions = new ArrayList<>();
        Matcher matcher = MENTION_PATTERN.matcher(text);
        while (matcher.find()) {
            mentions.add(matcher.group(1));
        }
        return mentions;
    }

    public static String replaceMentionsWithLinks(String text, String baseUrl) {
        return text.replaceAll("@([a-zA-Z0-9._-]+)", 
            "<a href=\"" + baseUrl + "/$1\" class=\"mention\">@$1</a>");
    }
} 