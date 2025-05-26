package sdu.project.realworldback.utill;


import org.springframework.stereotype.Component;

@Component
public class Util {

    public String toSlug(String input) {
        return input.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}
