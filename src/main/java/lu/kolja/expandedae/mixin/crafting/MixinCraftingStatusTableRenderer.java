package lu.kolja.expandedae.mixin.crafting;

import appeng.client.gui.me.crafting.CraftingStatusTableRenderer;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import lu.kolja.expandedae.definition.ExpLang;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(value = CraftingStatusTableRenderer.class, remap = false)
public class MixinCraftingStatusTableRenderer {
    @ModifyReturnValue(
            method = "getEntryTooltip(Lappeng/menu/me/crafting/CraftingStatusEntry;)Ljava/util/List;",
            at = @At("RETURN")
    )
    private List<Component> eae$modifyTooltip(List<Component> tooltip) {
        tooltip.add(ExpLang.HIGHLIGHT.text());
        return tooltip;
    }
}
