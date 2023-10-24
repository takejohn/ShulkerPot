package jp.takejohn.shulkerpot.bukkit.persistence;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class BooleanPersistentDataType implements PersistentDataType<Byte, Boolean> {

    @NotNull
    @Override
    public Class<Byte> getPrimitiveType() {
        return Byte.class;
    }

    @NotNull
    @Override
    public Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    @NotNull
    @Override
    public Byte toPrimitive(@NotNull Boolean complex, @NotNull PersistentDataAdapterContext context) {
        return complex ? (byte)1 : (byte)0;
    }

    @NotNull
    @Override
    public Boolean fromPrimitive(@NotNull Byte primitive, @NotNull PersistentDataAdapterContext context) {
        return primitive != 0;
    }

}
