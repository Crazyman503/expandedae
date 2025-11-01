package lu.kolja.expandedae.mixin.cpu;

import appeng.client.gui.me.crafting.CraftingCPUScreen;
import appeng.client.gui.me.crafting.CraftingStatusScreen;
import appeng.client.gui.style.ScreenStyle;
import appeng.menu.me.crafting.CraftingStatusMenu;
import lu.kolja.expandedae.definition.ExpLang;
import lu.kolja.expandedae.helper.misc.ICancellable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CraftingStatusScreen.class, remap = false)
public class MixinCraftingStatusScreen extends CraftingCPUScreen<CraftingStatusMenu> {
    @Unique
    private Button expandedae$cancelAll;

    private MixinCraftingStatusScreen(CraftingStatusMenu menu, Inventory playerInventory, Component title, ScreenStyle style) {
        super(menu, playerInventory, title, style);
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL"),
            remap = false
    )
    private void init(CraftingStatusMenu menu, Inventory playerInventory, Component title, ScreenStyle style, CallbackInfo ci) {
        this.expandedae$cancelAll = this.widgets.addButton(
                "cancelAll",
                ExpLang.CANCEL_ALL.text(),
                () -> ((ICancellable) menu).expandedae$cancelAll()
        );
        this.expandedae$cancelAll.setTooltip(Tooltip.create(ExpLang.CANCEL_ALL_HINT.text()));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float btn) {
        this.expandedae$cancelAll.active = menu.cpuList.cpus().stream().anyMatch(cpu -> cpu.currentJob() != null);
        super.render(guiGraphics, mouseX, mouseY, btn);
    }
}