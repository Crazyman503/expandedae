package lu.kolja.expandedae.client;

import appeng.client.gui.implementations.PatternProviderScreen;
import appeng.client.render.crafting.CraftingCubeModel;
import appeng.hooks.BuiltInModelHooks;
import appeng.init.client.InitScreens;
import com.mojang.blaze3d.platform.InputConstants;
import lu.kolja.expandedae.Expandedae;
import lu.kolja.expandedae.client.render.ExpCraftingUnitModelProvider;
import lu.kolja.expandedae.definition.ExpCreativeTab;
import lu.kolja.expandedae.definition.ExpMenus;
import lu.kolja.expandedae.enums.ExpTiers;
import lu.kolja.expandedae.menu.ExpPatternProviderMenu;
import lu.kolja.expandedae.menu.GigaPatternProviderMenu;
import lu.kolja.expandedae.screen.ExpIOPortScreen;
import lu.kolja.expandedae.screen.GigaPatternProviderScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class ExpandedaeClient {
    public static final ExpandedaeClient INSTANCE = new ExpandedaeClient();

    @SubscribeEvent
    public void registerEvent(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.CREATIVE_MODE_TAB)) {
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ExpCreativeTab.ID, ExpCreativeTab.TAB);
        }
    }

    public static final Lazy<KeyMapping> HIGHLIGHT = Lazy.of(() -> new KeyMapping(
            "key.expandedae.highlight",
            KeyConflictContext.GUI,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "key.categories.expandedae"
    ));

    @SubscribeEvent
    public void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(HIGHLIGHT.get());
    }

    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event) {
        initCraftingUnitModels();
        InitScreens.register(
                ExpMenus.EXP_PATTERN_PROVIDER,
                PatternProviderScreen<ExpPatternProviderMenu>::new,
                "/screens/exp_pattern_provider.json"
        );
        InitScreens.register(
                ExpMenus.EXP_IO_PORT,
                ExpIOPortScreen::new,
                "/screens/exp_io_port.json"
        );
        InitScreens.register(
                ExpMenus.GIGA_PATTERN_PROVIDER,
                GigaPatternProviderScreen<GigaPatternProviderMenu>::new,
                "/screens/giga_pattern_provider.json"
        );
    }

    private static void initCraftingUnitModels() {
        for (var tier : ExpTiers.values()) {
            var affix = tier.isCPU() ? tier.getCpuAffix() : tier.getAffix();
            BuiltInModelHooks.addBuiltInModel(
                    Expandedae.makeId("block/crafting/" + affix + "_formed"),
                    new CraftingCubeModel(new ExpCraftingUnitModelProvider(tier))
            );
        }
    }
}
