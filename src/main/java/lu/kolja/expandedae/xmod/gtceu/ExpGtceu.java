package lu.kolja.expandedae.xmod.gtceu;

import appeng.api.stacks.AEItemKey;
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public class ExpGtceu {
    public static final ResourceTexture MULTIPLY_OVERLAY = new ResourceTexture("expandedae:textures/gui/widget/multiply_overlay.png");

    public static final Supplier<AEItemKey> programmedCircuit = Lazy.of(() -> AEItemKey.of(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("gtceu", "programmed_circuit"))));
}
