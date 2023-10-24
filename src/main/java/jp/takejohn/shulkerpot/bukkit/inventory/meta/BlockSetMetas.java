package jp.takejohn.shulkerpot.bukkit.inventory.meta;

import org.bukkit.block.BlockState;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class BlockSetMetas {

    private BlockSetMetas() {
        throw new AssertionError();
    }

    public static void editBlockState(@NotNull BlockStateMeta itemMeta, @NotNull Consumer<? super BlockState> consumer) {
        @NotNull BlockState blockState = itemMeta.getBlockState();
        consumer.accept(blockState);
        itemMeta.setBlockState(blockState);
    }

}
