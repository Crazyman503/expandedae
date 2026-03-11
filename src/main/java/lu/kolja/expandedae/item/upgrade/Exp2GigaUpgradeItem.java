package lu.kolja.expandedae.item.upgrade;

import lu.kolja.expandedae.block.entity.ExpPatternProviderBlockEntity;
import lu.kolja.expandedae.definition.ExpBlockEntities;
import lu.kolja.expandedae.definition.ExpBlocks;
import lu.kolja.expandedae.definition.ExpItems;
import lu.kolja.expandedae.definition.ExpLang;
import lu.kolja.expandedae.item.abstracts.UpgradeItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Exp2GigaUpgradeItem extends UpgradeItem {
    public Exp2GigaUpgradeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag advancedTooltips) {
        tooltip.add(ExpLang.ITEM_UPGRADE_TOOLTIP.text("an Expanded Pattern Provider to a Giga Pattern Provider")
                .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, level, tooltip, advancedTooltips);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return replace(context,
                ExpPatternProviderBlockEntity.class, ExpBlockEntities.GIGA_PATTERN_PROVIDER, ExpBlocks.GIGA_PATTERN_PROVIDER,
                ExpItems.EXP_PATTERN_PROVIDER_PART.asItem().getPartClass(), ExpItems.GIGA_PATTERN_PROVIDER_PART.asItem());
    }
}
