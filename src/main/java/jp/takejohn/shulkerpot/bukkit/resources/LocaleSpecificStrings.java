package jp.takejohn.shulkerpot.bukkit.resources;

import jp.takejohn.shulkerpot.bukkit.ShulkerPot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;

public final class LocaleSpecificStrings {

    private LocaleSpecificStrings() {
        throw new AssertionError();
    }

    public static @NotNull String get(@NotNull String key) {
        return ShulkerPot.getPlugin().getResourceBundle().getString(key);
    }

    public static @NotNull String get(@NotNull Locale locale, @NotNull String key) {
        return ShulkerPot.getPlugin().getResourceBundle(locale).getString(key);
    }

    public static @NotNull String format(@NotNull Locale locale, @NotNull String key, @Nullable Object... args) {
        return MessageFormat.format(get(locale, key), args);
    }

}
