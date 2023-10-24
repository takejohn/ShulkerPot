package jp.takejohn.shulkerpot.bukkit.event.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public final class InventoryTypes {

    private InventoryTypes() {
        throw new AssertionError();
    }

    public static boolean isInternal(@NotNull InventoryType inventoryType) {
        return inventoryType == InventoryType.CRAFTING || inventoryType == InventoryType.CREATIVE;
    }

}
