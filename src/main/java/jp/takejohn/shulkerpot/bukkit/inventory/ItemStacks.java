package jp.takejohn.shulkerpot.bukkit.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public final class ItemStacks {

    private ItemStacks() {
        throw new AssertionError();
    }

    @Contract(pure = true, value = "null -> false")
    public static boolean isShulkerBox(@Nullable ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        return switch (itemStack.getType()) {
            case SHULKER_BOX,
                    WHITE_SHULKER_BOX,
                    ORANGE_SHULKER_BOX,
                    MAGENTA_SHULKER_BOX,
                    LIGHT_BLUE_SHULKER_BOX,
                    YELLOW_SHULKER_BOX,
                    LIME_SHULKER_BOX,
                    PINK_SHULKER_BOX,
                    GRAY_SHULKER_BOX,
                    LIGHT_GRAY_SHULKER_BOX,
                    CYAN_SHULKER_BOX,
                    PURPLE_SHULKER_BOX,
                    BLUE_SHULKER_BOX,
                    BROWN_SHULKER_BOX,
                    GREEN_SHULKER_BOX,
                    RED_SHULKER_BOX,
                    BLACK_SHULKER_BOX -> true;
            default -> false;
        };
    }

    /**
     * Gets a copy of a {@link ItemStack}'s {@link ItemMeta}.
     * If the item does not have metadata, creates a new item meda for the material of the item.
     * @param itemStack The item stack to get metadata from
     * @return          The copied or created metadata
     */
    public static @Nullable ItemMeta getItemMeta(@NotNull ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            return itemStack.getItemMeta();
        }
        return Bukkit.getServer().getItemFactory().getItemMeta(itemStack.getType());
    }

    /**
     * Edit the metadata of an {@link ItemStack} with the specified {@link Consumer}.
     * @param itemStack The {@link ItemStack} to edit the metadata
     * @param consumer  The {@link Consumer} to edit the metadata
     * @return          True if successfully applied {@link ItemMeta}
     */
    public static boolean editItemMeta(@NotNull ItemStack itemStack, @NotNull Consumer<@NotNull ItemMeta> consumer) {
        final @Nullable ItemMeta itemMeta = getItemMeta(itemStack);
        if (itemMeta == null) {
            return false;
        }
        consumer.accept(itemMeta);
        return itemStack.setItemMeta(itemMeta);
    }

}
