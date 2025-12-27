package lu.kolja.expandedae.mixin.accessor;

import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.style.WidgetStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = ScreenStyle.class, remap = false)
public interface AccessorScreenStyle {
    @Accessor("widgets")
    Map<String, WidgetStyle> getWidgets();
}
