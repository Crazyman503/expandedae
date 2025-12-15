package lu.kolja.expandedae.item.linked;

import appeng.api.features.IGridLinkableHandler;
import appeng.api.implementations.blockentities.IWirelessAccessPoint;
import appeng.api.networking.IGrid;
import appeng.core.localization.PlayerMessages;
import appeng.items.AEBaseItem;
import appeng.util.Platform;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;

public abstract class LinkedItem extends AEBaseItem {
    public static IGridLinkableHandler handler = new LinkedTerminalItem.LinkedTerminalHandler();
    protected static final String NBT_ACCESS_POINT = "accessPoint";

    protected LinkedItem(Properties properties) {
        super(properties);
    }

    protected static class LinkedTerminalHandler implements IGridLinkableHandler {
        @Override
        public boolean canLink(ItemStack itemStack) {
            return itemStack.getItem() instanceof LinkedTerminalItem;
        }

        @Override
        public void link(ItemStack itemStack, GlobalPos globalPos) {
            GlobalPos.CODEC.encodeStart(
                            NbtOps.INSTANCE,
                            globalPos)
                    .result()
                    .ifPresent(tag -> itemStack.getOrCreateTag().put(NBT_ACCESS_POINT, tag));
        }

        @Override
        public void unlink(ItemStack itemStack) {
            itemStack.removeTagKey(NBT_ACCESS_POINT);
        }
    }

    protected GlobalPos linkedPos(ItemStack stack) {
        var tag = stack.getTag();
        if (tag == null || !tag.contains(NBT_ACCESS_POINT, Tag.TAG_COMPOUND)) return null;
        return GlobalPos.CODEC.decode(
                        NbtOps.INSTANCE,
                        tag.get(NBT_ACCESS_POINT))
                .resultOrPartial(Util.prefix("Linked position", Log::error))
                .map(Pair::getFirst)
                .orElse(null);
    }

    @Nullable
    protected IGrid getLinkedGrid(ItemStack stack, Level level, @Nullable Player who) {
        if (level.isClientSide) return null;
        var pos = linkedPos(stack);
        if (pos == null) {
            if (who != null) who.displayClientMessage(PlayerMessages.DeviceNotLinked.text(), true);
            return null;
        }
        var linkedLevel = level.getServer().getLevel(pos.dimension());
        if (linkedLevel == null) {
            if (who != null) who.displayClientMessage(PlayerMessages.LinkedNetworkNotFound.text(), true);
            return null;
        }

        var be = Platform.getTickingBlockEntity(linkedLevel, pos.pos());
        if (!(be instanceof IWirelessAccessPoint accessPoint)) {
            if (who != null) who.displayClientMessage(PlayerMessages.LinkedNetworkNotFound.text(), true);
            return null;
        }

        var grid = accessPoint.getGrid();
        if (grid == null) {
            if (who != null) who.displayClientMessage(PlayerMessages.LinkedNetworkNotFound.text(), true);
            return null; // Remove if we want infinite range
        }
        return grid;
    }
}
