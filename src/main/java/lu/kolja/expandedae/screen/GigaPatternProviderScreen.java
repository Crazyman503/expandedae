package lu.kolja.expandedae.screen;

import appeng.client.gui.implementations.PatternProviderScreen;
import appeng.client.gui.style.ScreenStyle;
import lu.kolja.expandedae.client.gui.widgets.ExpActionButton;
import lu.kolja.expandedae.client.gui.widgets.ExpActionItems;
import lu.kolja.expandedae.menu.GigaPatternProviderMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GigaPatternProviderScreen<C extends GigaPatternProviderMenu> extends PatternProviderScreen<C> {
    private final ExpActionButton nextPageButton;
    private final ExpActionButton prevPageButton;

    public GigaPatternProviderScreen(C menu, Inventory playerInventory, Component title, ScreenStyle style) {
        super(menu, playerInventory, title, style);

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

        this.nextPageButton.visible = menu.getCurrentPage() < 3;
        this.prevPageButton.visible = menu.getCurrentPage() > 0;
    }
}