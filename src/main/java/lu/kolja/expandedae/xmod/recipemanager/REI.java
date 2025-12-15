package lu.kolja.expandedae.xmod.recipemanager;

import appeng.api.stacks.GenericStack;
import me.shedaniel.rei.api.client.favorites.FavoriteEntry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.impl.client.gui.widget.favorites.FavoritesEntriesManager;

class REI implements RecipeManager {
    @Override
    public void addFavorites(GenericStack... stacks) {
        for (var stack : stacks) {
            FavoritesEntriesManager.INSTANCE.add(FavoriteEntry.fromEntryStack(EntryStacks.of(GenericStack.wrapInItemStack(stack))));
        }
    }
}