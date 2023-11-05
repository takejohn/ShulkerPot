package jp.takejohn.shulkerpot.java.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.function.Function;

public final class Strings {

    private Strings() {
        throw new AssertionError();
    }

    @SafeVarargs
    public static <E> @NotNull String join(@NotNull CharSequence delimiter,
                                           @NotNull Function<? super E, ? extends @Nullable CharSequence> toString,
                                           @Nullable E... elements) {
        return String.join(delimiter, Arrays.stream(elements).map(toString).toArray(CharSequence[]::new));
    }

    public static <E> @NotNull String join(@NotNull CharSequence delimiter, @NotNull Collection<? extends @Nullable E> elements,
                                           @NotNull Function<? super E, ? extends @NotNull CharSequence> function) {
        return String.join(delimiter, elements.stream().map(function).toArray(CharSequence[]::new));
    }

    public static boolean containsIgnoreCase(@NotNull String string, @NotNull String substring, @NotNull Locale locale) {
        return string.toLowerCase(locale).contains(substring.toLowerCase(locale));
    }

}
