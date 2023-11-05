package jp.takejohn.shulkerpot.bukkit.resources;

import jp.takejohn.shulkerpot.bukkit.ShulkerPot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Collection;
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

    public static @NotNull String join(@NotNull Locale locale, @NotNull Collection<? extends @NotNull CharSequence> args) {
        if (args.isEmpty()) {
            return get(locale, "terms.none");
        }
        return String.join(get(locale, "terms.delimiter"), args);
    }

    public static @NotNull String join(@NotNull Locale locale, @NotNull CharSequence @NotNull... args) {
        if (args.length == 0) {
            return get(locale, "terms.none");
        }
        return String.join(get(locale, "terms.delimiter"), args);
    }

}
