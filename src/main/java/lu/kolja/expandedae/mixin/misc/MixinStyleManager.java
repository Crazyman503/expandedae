package lu.kolja.expandedae.mixin.misc;

import appeng.client.gui.style.StyleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = StyleManager.class, remap = false)
public class MixinStyleManager {
    @ModifyVariable(method = "loadStyleDoc", at = @At("HEAD"), argsOnly = true)
    private static String loadStyleDocHooks(String path) {
        return switch (path) {
            case "/screens/wireless_pattern_encoding_terminal.json" -> "/screens/wtlib/modify_wireless_pattern_encoding_terminal.json";
            case "/screens/pattern_encoding_terminal.json" -> "/screens/terminals/modify_pattern_encoding_terminal.json";
            case "/screens/crafting_status.json" -> "/screens/exp_mode/modify_crafting_status.json";
            case "/screens/craft_confirm.json" -> "/screens/exp_mode/modify_craft_confirm.json";
            case "/screens/crafting_cpu.json" -> "/screens/exp_mode/modify_crafting_cpu.json";
            default -> path;
        };
    }
}
