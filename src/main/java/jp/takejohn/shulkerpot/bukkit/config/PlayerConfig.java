package jp.takejohn.shulkerpot.bukkit.config;

import jp.takejohn.shulkerpot.bukkit.command.MessageExpressible;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public interface PlayerConfig extends MessageExpressible {

    interface ClickTypes extends MessageExpressible {

        boolean get(@NotNull ClickTypeKey clickType);

        void set(@NotNull ClickTypeKey clickType, boolean value);

        default void setAll(@NotNull Collection<@NotNull ClickTypeKey> clickTypes, boolean value) {
            for (@NotNull ClickTypeKey clickType : clickTypes) {
                set(clickType, value);
            }
        }

        default void setAll(boolean value) {
            for (@NotNull ClickTypeKey clickType : ClickTypeKey.values()) {
                set(clickType, value);
            }
        }

        @Override
        default @NotNull String @NotNull[] getMessage() {
            final @NotNull ClickTypeKey[] keys = ClickTypeKey.values();
            final int length = keys.length;
            final @NotNull String @NotNull[] result = new String[length];
            for (int i = 0 ; i < length ; i++) {
                final @NotNull ClickTypeKey key = keys[0];
                result[i] = key.getName() + ": " + get(key);
            }
            return result;
        }

    }

    @NotNull Player getPlayer();

    boolean enabled();

    void setEnabled(boolean enabled);

    @NotNull ClickTypes clickTypes();

    @Override
    default @NotNull String @NotNull[] getMessage() {
        final @NotNull List<@NotNull String> result = new ArrayList<>();
        result.add("enabled: " + enabled());
        result.add("clickTypes:");
        for (@NotNull String clickType : clickTypes().getMessage()) {
            result.add("  " + clickType);
        }
        return result.toArray(new String[0]);
    }

}
