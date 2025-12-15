package lu.kolja.expandedae.xmod.recipemanager;

import appeng.api.stacks.GenericStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

public interface RecipeManager {

    RecipeManager INSTANCE = find();

    void addFavorites(GenericStack... stacks);

    static RecipeManager find() {
        for (IModInfo iModInfo : ModList.get().getMods()) {
            String id = iModInfo.getModId();
            if (id.equals("emi") || id.equals("jei") || id.equals("rei")) {
                return switch (id) {
                    case "emi" -> new EMI();
                    case "jei" -> new JEI();
                    case "rei" -> new REI();
                    default -> null;
                };
            }
        }
        return null;
    }
}
