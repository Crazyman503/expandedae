package lu.kolja.expandedae.mixin.storage;

import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.me.common.MEStorageScreen;
import appeng.client.gui.me.common.RepoSlot;
import appeng.client.gui.style.ScreenStyle;
import appeng.menu.me.common.MEStorageMenu;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import lu.kolja.expandedae.client.ExpandedaeClient;
import lu.kolja.expandedae.network.ExpNetworkHandler;
import lu.kolja.expandedae.network.implementations.HighlightDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MEStorageScreen.class, remap = false)
public abstract class MixinMEStorageScreen<C extends MEStorageMenu> extends AEBaseScreen<C> {
    public MixinMEStorageScreen(C menu, Inventory playerInventory, Component title, ScreenStyle style) {
        super(menu, playerInventory, title, style);
    }

    @WrapOperation(
            method = "keyPressed",
            at = @At(
                    value = "INVOKE",
                    target = "Lappeng/client/gui/AEBaseScreen;keyPressed(III)Z"
            )
    )
    public boolean keyPressed(MEStorageScreen<C> instance, int keyCode, int scanCode, int keyPressed, Operation<Boolean> original) {
        var hotKey = ExpandedaeClient.HIGHLIGHT.get();
        if (hotKey.isActiveAndMatches(InputConstants.getKey(keyCode, scanCode))) {
            if (this.hoveredSlot instanceof RepoSlot repoSlot) {
                var entry = repoSlot.getEntry();
                if (entry != null && entry.getStoredAmount() > 0L) {
                    hotKey.consumeClick();
                    var packet = new HighlightDataPacket.HighlightWhat(entry.getWhat());
                    ExpNetworkHandler.HANDLER.sendToServer(packet);
                    return true;
                }
            }
        }
        return original.call(instance, keyCode, scanCode, keyPressed);
    }
}
