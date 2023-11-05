package jp.takejohn.shulkerpot.bukkit.entity;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * A Class to represent a variable related to each player.
 * The value would be removed when a player leaves the server.
 * @param <T> The type of values
 */
public class PlayerSpecific<T> extends PlayerSpecificConstant<T> {

    /**
     * Set a new value related to the player.
     * @param player The player
     * @param value  The new value related to the player
     */
    public void set(@NotNull Player player, T value) {
        map.put(player.getUniqueId(), value);
    }

    public static <S> @NotNull PlayerSpecific<S> withInitial(Function<? super Player, ? extends S> function) {
        return new PlayerSpecific<>() {

            @Override
            protected S initialValue(@NotNull Player player) {
                return function.apply(player);
            }

        };
    }

}
