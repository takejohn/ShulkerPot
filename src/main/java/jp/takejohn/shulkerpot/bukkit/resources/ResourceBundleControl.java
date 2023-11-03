package jp.takejohn.shulkerpot.bukkit.resources;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ResourceBundleControl extends ResourceBundle.Control {

    private static final @NotNull List<@NotNull String> FORMATS = Collections.singletonList("properties");

    private final @NotNull Plugin plugin;

    public ResourceBundleControl(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getFormats(String baseName) {
        return FORMATS;
    }

    @Override
    public @Nullable ResourceBundle newBundle(@NotNull String baseName, @NotNull Locale locale,
                                              @NotNull String format, @NotNull ClassLoader loader, boolean reload)
            throws IOException {
        final @NotNull String resourceName = toResourceName(toBundleName(baseName, locale), format);
        final @Nullable InputStream inputStream = plugin.getResource(resourceName);
        if (inputStream == null) {
            return null;
        }
        return new PropertyResourceBundle(inputStream);
    }

}
