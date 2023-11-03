package jp.takejohn.shulkerpot.bukkit.entity;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Players {

    private static final @NotNull Pattern LANGUAGE_PATTERN = Pattern.compile("[A-Za-z]+");

    private static final @NotNull Pattern LANGUAGE_COUNTRY_PATTERN = Pattern.compile("([A-Za-z]+)_([A-Za-z]+)");

    private Players() {
        throw new AssertionError();
    }

    public static @NotNull Locale getLocale(@NotNull Player player) {
        final @NotNull String string = player.getLocale();
        final @NotNull Matcher languageCountryMatcher = LANGUAGE_COUNTRY_PATTERN.matcher(string);
        if (languageCountryMatcher.matches()) {
            return new Locale(languageCountryMatcher.group(1), languageCountryMatcher.group(2));
        }
        final @NotNull Matcher languageMatcher = LANGUAGE_PATTERN.matcher(string);
        if (languageMatcher.matches()) {
            return new Locale(string);
        }
        return Locale.ROOT;
    }

}
