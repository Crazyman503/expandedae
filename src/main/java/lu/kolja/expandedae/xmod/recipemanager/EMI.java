package lu.kolja.expandedae.xmod.recipemanager;

import appeng.api.stacks.GenericStack;
import appeng.integration.modules.emi.EmiStackHelper;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.runtime.EmiFavorites;
import dev.emi.emi.screen.EmiScreenManager;

class EMI implements RecipeManager {
    @Override
    public void addFavorites(GenericStack... stacks) {
        for (var stack : stacks) {
            EmiFavorites.addFavorite(EmiStackHelper.toEmiStack(stack));
        }
        EmiScreenManager.repopulatePanels(SidebarType.FAVORITES);
    }
}