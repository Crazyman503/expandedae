package lu.kolja.expandedae.screen;

import appeng.api.config.LockCraftingMode;
import appeng.api.config.Settings;
import appeng.api.config.YesNo;
import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.Icon;
import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.widgets.ServerSettingToggleButton;
import appeng.client.gui.widgets.SettingToggleButton;
import appeng.client.gui.widgets.ToggleButton;
import appeng.client.gui.widgets.ToolboxPanel;
import appeng.core.localization.GuiText;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.ConfigButtonPacket;
import lu.kolja.expandedae.client.gui.widgets.ExpActionButton;
import lu.kolja.expandedae.client.gui.widgets.ExpActionItems;
import lu.kolja.expandedae.menu.GigaPatternProviderMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GigaPatternProviderScreen<C extends GigaPatternProviderMenu> extends AEBaseScreen<C> {
    private final ExpActionButton nextPageButton;
    private final ExpActionButton prevPageButton;

    private final SettingToggleButton<YesNo> blockingModeButton;
    private final SettingToggleButton<LockCraftingMode> lockCraftingModeButton;
    private final ToggleButton showInPatternAccessTerminalButton;
    private final GigaPatternProviderLockReason lockReason;

    public GigaPatternProviderScreen(C menu, Inventory playerInventory, Component title,
                                 ScreenStyle style) {
        super(menu, playerInventory, title, style);
        this.blockingModeButton = new ServerSettingToggleButton<>(Settings.BLOCKING_MODE, YesNo.NO);
        this.addToLeftToolbar(this.blockingModeButton);


        lockCraftingModeButton = new ServerSettingToggleButton<>(Settings.LOCK_CRAFTING_MODE, LockCraftingMode.NONE);
        this.addToLeftToolbar(lockCraftingModeButton);

        widgets.addOpenPriorityButton();

        if (menu.getToolbox().isPresent()) {
            this.widgets.add("toolbox", new ToolboxPanel(style, menu.getToolbox().getName()));
        }

        this.showInPatternAccessTerminalButton = new ToggleButton(Icon.PATTERN_ACCESS_SHOW,
                Icon.PATTERN_ACCESS_HIDE,
                GuiText.PatternAccessTerminal.text(), GuiText.PatternAccessTerminalHint.text(),
                btn -> selectNextPatternProviderMode());
        this.addToLeftToolbar(this.showInPatternAccessTerminalButton);

        this.lockReason = new GigaPatternProviderLockReason(this);
        widgets.add("lockReason", this.lockReason);

        this.nextPageButton = new ExpActionButton(
                ExpActionItems.NEXT_PAGE,
                () -> this.getMenu().setPage(this.getMenu().getCurrentPage() + 1)
        );
        this.addToLeftToolbar(this.nextPageButton);
        this.prevPageButton = new ExpActionButton(
                ExpActionItems.PREV_PAGE,
                () -> this.getMenu().setPage(this.getMenu().getCurrentPage() - 1)
        );
        this.addToLeftToolbar(this.prevPageButton);
    }

    @Override
    protected void updateBeforeRender() {
        super.updateBeforeRender();

        this.lockReason.setVisible(menu.getLockCraftingMode() != LockCraftingMode.NONE);
        this.blockingModeButton.set(this.menu.getBlockingMode());
        this.lockCraftingModeButton.set(this.menu.getLockCraftingMode());
        this.showInPatternAccessTerminalButton.setState(this.menu.getShowInAccessTerminal() == YesNo.YES);

        this.nextPageButton.visible = menu.getCurrentPage() < 3;
        this.prevPageButton.visible = menu.getCurrentPage() > 0;
    }

    private void selectNextPatternProviderMode() {
        final boolean backwards = isHandlingRightClick();
        NetworkHandler.instance().sendToServer(new ConfigButtonPacket(Settings.PATTERN_ACCESS_TERMINAL, backwards));
    }
}