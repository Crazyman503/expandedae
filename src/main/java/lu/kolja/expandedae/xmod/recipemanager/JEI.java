package lu.kolja.expandedae.xmod.recipemanager;

import appeng.api.stacks.GenericStack;
import lu.kolja.expandedae.Expandedae;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.gui.bookmarks.IngredientBookmark;
import mezz.jei.library.ingredients.itemStacks.TypedItemStack;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
class JEI implements IModPlugin, RecipeManager {
    ResourceLocation UID;
    IIngredientManager ingredientManager;

    JEI() {
        UID = Expandedae.makeId("jei");
    }

    @Override
    public void addFavorites(GenericStack... stacks) {
        for (var stack : stacks) {
            IngredientBookmark.create(TypedItemStack.create(GenericStack.wrapInItemStack(stack)), ingredientManager);
        }
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        ingredientManager = jeiRuntime.getIngredientManager();
    }
}
