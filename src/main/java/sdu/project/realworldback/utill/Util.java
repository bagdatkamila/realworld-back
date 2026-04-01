package sdu.project.realworldback.utill;

import org.springframework.stereotype.Component;

@Component
public class Util {

    public String toSlug(String input) {
        if (input == null) {
            return "";
        }

        String sanitized = StringEscapeUtils.escapeHtml4(input.trim());

        if (sanitized.length() > 200) {
            sanitized = sanitized.substring(0, 200);
        }

        return sanitized.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}
