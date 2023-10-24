package jp.takejohn.shulkerpot.bukkit.entity;

import jp.takejohn.shulkerpot.bukkit.event.inventory.InventoryTypes;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

public final class HumanEntities {

    private HumanEntities() {
        throw new AssertionError();
    }

    public static boolean isOpeningExternalInventory(@NotNull HumanEntity entity) {
        return !InventoryTypes.isInternal(entity.getOpenInventory().getType());
    }

}
