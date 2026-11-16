package com.workpilot.authservice.util;

import java.text.Normalizer;
import java.util.UUID;
import java.util.function.Predicate;

public final class SlugGenerator {

    private SlugGenerator() {
    }

    // "Kunal's Workspace" -> "kunals-workspace"
    public static String toSlug(String name) {

        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        String slug = normalized.toLowerCase()
                .replaceAll("['\u2019]", "")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-+|-+$", "");

        return slug.isEmpty() ? "workspace" : slug;
    }

    // Returns the base slug, or the base slug plus a short suffix if it is taken.
    public static String uniqueSlug(String name, Predicate<String> slugExists) {

        String base = toSlug(name);

        if (base.length() > 90) {
            base = base.substring(0, 90);
        }

        String candidate = base;

        while (slugExists.test(candidate)) {
            candidate = base + "-" + UUID.randomUUID().toString().substring(0, 6);
        }

        return candidate;
    }
}