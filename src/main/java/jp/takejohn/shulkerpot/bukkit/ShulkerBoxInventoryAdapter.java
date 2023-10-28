package jp.takejohn.shulkerpot.bukkit;

import jp.takejohn.shulkerpot.bukkit.inventory.meta.BlockSetMetas;
import jp.takejohn.shulkerpot.bukkit.inventory.ItemStacks;
import jp.takejohn.shulkerpot.bukkit.entity.PlayerSpecific;
import jp.takejohn.shulkerpot.bukkit.persistence.UUIDPersistentDataType;
import jp.takejohn.shulkerpot.java.util.RunnableOnce;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class ShulkerBoxInventoryAdapter {

    private static final Listener shulkerBoxInventoryListener = new Listener() {

        @EventHandler
        public void onInventoryClick(@NotNull InventoryClickEvent event) {
            final @Nullable ShulkerBoxInventoryAdapter adapter = getAdapter(event.getWhoClicked());
            if (adapter != null) {
                adapter.findNewItemStack(Stream.of(event.getCurrentItem(), event.getCursor()));
                adapter.acceptInventoryUpdate();
            }
        }

        @EventHandler
        public void onInventoryDrag(@NotNull InventoryDragEvent event) {
            final @Nullable ShulkerBoxInventoryAdapter adapter = getAdapter(event.getWhoClicked());
            if (adapter != null) {
                adapter.findNewItemStack(Stream.concat(Stream.of(event.getOldCursor(), event.getCursor()),
                        event.getNewItems().values().stream()));
                adapter.acceptInventoryUpdate();
            }
        }

        @EventHandler
        public void onInventoryClose(@NotNull InventoryCloseEvent event) {
            if (event.getPlayer() instanceof Player player) {
                final @Nullable ShulkerBoxInventoryAdapter adapter = adapters.get(player);
                if (adapter != null) {
                    adapter.onClose();
                }
            }
        }

        @EventHandler
        public void onPlayerDropItem(@NotNull PlayerDropItemEvent event) {
            final @Nullable ShulkerBoxInventoryAdapter adapter = adapters.get(event.getPlayer());
            if (adapter != null) {
                final @NotNull ItemStack itemDrop = event.getItemDrop().getItemStack();
                adapter.findNewItemStack(Stream.of(itemDrop));
                if (adapter.isOpening(itemDrop)) {
                    Bukkit.getScheduler().runTask(ShulkerPot.getPlugin(), adapter::close);
                }
            }
        }

        @EventHandler
        public void onPlayerDeath(@NotNull PlayerDeathEvent event) {
            final @Nullable ShulkerBoxInventoryAdapter adapter = adapters.get(event.getEntity());
            if (adapter != null) {
                adapter.findNewItemStack(event.getDrops().stream());
                adapter.close();
            }
        }

        private static @Nullable ShulkerBoxInventoryAdapter getAdapter(@NotNull HumanEntity human) {
            if (!(human instanceof Player player)) {
                return null;
            }
            return adapters.get(player);
        }

    };

    private static final RunnableOnce initializer = new RunnableOnce() {

        @Override
        public void run() {
            Bukkit.getPluginManager().registerEvents(shulkerBoxInventoryListener, ShulkerPot.getPlugin());
        }

    };

    private static final PlayerSpecific<ShulkerBoxInventoryAdapter> adapters = new PlayerSpecific<>();

    private static final NamespacedKey KEY = new NamespacedKey(ShulkerPot.getPlugin(), "adapter_id");

    private static final UUIDPersistentDataType DATA_TYPE = new UUIDPersistentDataType();

    private final @NotNull Player opener;

    private @NotNull ItemStack itemStack;

    private final @NotNull Inventory inventory;

    private final @NotNull UUID id = UUID.randomUUID();

    private ShulkerBoxInventoryAdapter(@NotNull Player opener, @NotNull ItemStack shulkerBoxItem) {
        Objects.requireNonNull(opener);
        Objects.requireNonNull(shulkerBoxItem);
        if (!ItemStacks.isShulkerBox(shulkerBoxItem)) {
            throw new IllegalArgumentException(shulkerBoxItem + " is not a shulker box");
        }
        initializer.runOnce();
        this.opener = opener;
        this.itemStack = shulkerBoxItem;
        setItemOpened(shulkerBoxItem);
        inventory = getInventoryOfItemStack();
    }

    /**
     * Opens a shulker box for a player from their inventory.
     * Make sure that <code>shulkerBoxItem</code> is in player's inventory.
     * @param player The player
     * @param shulkerBoxItem The shulker box as an item
     */
    public static void open(@NotNull Player player, @NotNull ItemStack shulkerBoxItem) {
        if (Objects.requireNonNull(shulkerBoxItem.getItemMeta()).getPersistentDataContainer().get(KEY, DATA_TYPE) != null) {
            throw new IllegalStateException("The item stack is already marked as an opened shulker box: " + shulkerBoxItem);
        }
        final @NotNull ShulkerBoxInventoryAdapter adapter = new ShulkerBoxInventoryAdapter(player, shulkerBoxItem);
        adapters.set(player, adapter);
        final @NotNull InventoryView openInventory = player.getOpenInventory();
        final @Nullable ItemStack cursor = openInventory.getCursor();
        openInventory.setCursor(null);
        Objects.requireNonNull(player.openInventory(adapter.inventory)).setCursor(cursor);
    }

    private void close() {
        onClose();
        opener.closeInventory();
    }

    private void onClose() {
        acceptInventoryUpdate();
        setItemClosed(itemStack);
        final @NotNull InventoryView openInventory = opener.getOpenInventory();
        final @Nullable ItemStack cursor = openInventory.getCursor();
        if (cursor == null) {
            return;
        }
        openInventory.setCursor(null);
        final @Nullable ItemStack drops = opener.getInventory().addItem(cursor).get(0);
        if (drops != null) {
            opener.getWorld().dropItem(opener.getLocation(), drops);
        }
        adapters.set(opener, null);
    }

    private void acceptInventoryUpdate() {
        if (!isOpening(itemStack)) {
            throw new IllegalStateException("The item stack is not marked as an opened shulker box: " + this);
        }
        ItemStacks.editItemMeta(itemStack,
                itemMeta -> BlockSetMetas.editBlockState((BlockStateMeta)itemMeta,
                blockState -> ((ShulkerBox)blockState).getInventory().setContents(inventory.getContents())));
    }

    /**
     * Updates {@link #itemStack} to a new one if the open shulker box is in the specified stream.
     * @param items The {@link Stream} of items to find the shulker box.
     */
    private void findNewItemStack(@NotNull Stream<@Nullable ItemStack> items) {
        items.filter(this::isOpening).findFirst().ifPresent(newItem -> this.itemStack = newItem);
    }

    private @NotNull Inventory getInventoryOfItemStack() {
        final @NotNull BlockStateMeta blockStateMeta = (BlockStateMeta)Objects.requireNonNull(ItemStacks.getItemMeta(itemStack));
        final @NotNull ShulkerBox blockState = (ShulkerBox)blockStateMeta.getBlockState();
        return blockState.getInventory();
    }

    @Override
    public @NotNull String toString() {
        return "ShulkerBoxInventoryAdapter{opener=" + opener + ", itemStack=" + itemStack + "}";
    }

    @Contract("null -> false")
    private boolean isOpening(@Nullable ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return false;
        }
        final @Nullable UUID result = Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer()
                .get(KEY, DATA_TYPE);
        if (result == null) {
            return false;
        }
        return result.equals(this.id);
    }

    private void setItemOpened(@NotNull ItemStack itemStack) {
        ItemStacks.editItemMeta(itemStack, itemMeta -> itemMeta.getPersistentDataContainer().set(KEY, DATA_TYPE, id));
    }

    private void setItemClosed(@NotNull ItemStack itemStack) {
        ItemStacks.editItemMeta(itemStack, itemMeta -> itemMeta.getPersistentDataContainer().remove(KEY));
    }

}
