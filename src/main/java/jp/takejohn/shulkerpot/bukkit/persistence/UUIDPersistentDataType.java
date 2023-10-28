package jp.takejohn.shulkerpot.bukkit.persistence;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UUIDPersistentDataType implements PersistentDataType<long[], UUID> {

    @Override
    public @NotNull Class<long[]> getPrimitiveType() {
        return long[].class;
    }

    @Override
    public @NotNull Class<UUID> getComplexType() {
        return UUID.class;
    }

    @Contract(value = "_, _ -> new", pure = true)
    @Override
    public long @NotNull[] toPrimitive(@NotNull UUID complex, @NotNull PersistentDataAdapterContext context) {
        return new long[]{ complex.getMostSignificantBits(), complex.getLeastSignificantBits() };
    }

    @Contract(value = "_, _ -> new", pure = true)
    @Override
    public @NotNull UUID fromPrimitive( long @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        return new UUID(primitive[0], primitive[1]);
    }

}
