package com.sangha.forum.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class ContentSanitizer {
    private static final int MAX_CONTENT_LENGTH = 10000;
    private static final int MAX_TITLE_LENGTH = 200;

    public String sanitizeHtml(String html) {
        if (html == null) return "";
        
        // Configure allowed HTML elements and attributes
        Safelist safelist = Safelist.basic()
            .addTags("h1", "h2", "h3", "h4", "h5", "h6", "blockquote", "pre", "code")
            .addAttributes("a", "href", "title")
            .addAttributes("img", "src", "alt", "title")
            .addProtocols("a", "href", "http", "https")
            .addProtocols("img", "src", "http", "https");

        return Jsoup.clean(html, safelist);
    }

    public String sanitizePlainText(String text) {
        if (text == null) return "";
        return Jsoup.clean(text, Safelist.none());
    }

    public boolean validateContentLength(String content) {
        return content != null && content.length() <= MAX_CONTENT_LENGTH;
    }

    public boolean validateTitleLength(String title) {
        return title != null && title.length() <= MAX_TITLE_LENGTH;
    }

    public String generatePreview(String htmlContent) {
        if (htmlContent == null) return "";
        
        // Extract first paragraph or first 200 characters
        String plainText = sanitizePlainText(htmlContent);
        return plainText.length() > 200 ? plainText.substring(0, 200) + "..." : plainText;
    }
} 