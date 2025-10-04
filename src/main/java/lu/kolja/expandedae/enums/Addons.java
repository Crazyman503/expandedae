package lu.kolja.expandedae.enums;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.ModList;

public enum Addons {
    EXT("expatternprovider"),
    MEGA("megacells"),
    APPFLUX("appflux"),
    ADV("advancedae"),
    APPMEK("appmek"),
    ARSENG("arseng"),
    APPBOT("appbot"),
    GTCEU("gtceu");

    public final String mod;
    public final boolean isLoaded;

    Addons(String mod) {
        this.mod = mod;
        this.isLoaded = isLoaded(mod);
    }

    private boolean isLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public Component getUnavailableTooltip() {
        return Component.literal("Mod " + mod + " not installed!").withStyle(ChatFormatting.DARK_RED);
    }
}
