package jp.takejohn.shulkerpot.bukkit.entity;

import jp.takejohn.shulkerpot.bukkit.ShulkerPot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * A Class to represent a value related to each player.
 * The value would be removed when a player leaves the server.
 * @param <T> The type of values
 */
public class PlayerSpecificConstant<T> {

    private class PlayerSessionListener implements Listener {

        @EventHandler
        private void onPlayerQuit(@NotNull PlayerQuitEvent event) {
            map.remove(event.getPlayer().getUniqueId());
        }

    }

    protected final Map<@NotNull UUID, T> map = new HashMap<>();

    public PlayerSpecificConstant() {
        Bukkit.getPluginManager().registerEvents(new PlayerSessionListener(), ShulkerPot.getPlugin());
    }

    /**
     * Returns the value related to the player.
     * If the value has not set, sets {@link #initialValue(Player)} for the player and returns the initial value.
     * @param player The player
     * @return       The value related to the player
     */
    public T get(@NotNull Player player) {
        final @NotNull UUID uniqueId = player.getUniqueId();
        if (map.containsKey(uniqueId)) {
            return map.get(uniqueId);
        }
        final T value = initialValue(player);
        map.put(uniqueId, value);
        return value;
    }

    /**
     * Returns an initial value for a player.
     * @param player The player
     * @return       The initial value for the player
     */
    protected T initialValue(@NotNull Player player) {
        return null;
    }

    /**
     * Creates a new player-specific variable.
     * The initial value would be calculated with the specified function.
     * @param function The function to calculate an initial value for a player.
     * @return The new player-specific variable.
     */
    public static <S> @NotNull PlayerSpecificConstant<S> withInitial(Function<? super Player, ? extends S> function) {
        return new PlayerSpecificConstant<>() {

            @Override
            protected S initialValue(@NotNull Player player) {
                return function.apply(player);
            }

        };
    }

}
