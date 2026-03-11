package lu.kolja.expandedae.item.upgrade;

import com.glodblock.github.extendedae.common.EPPItemAndBlock;
import com.glodblock.github.extendedae.common.tileentities.TileExPatternProvider;
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

public class Ext2ExpUpgradeItem extends UpgradeItem {
    public Ext2ExpUpgradeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag advancedTooltips) {
        tooltip.add(ExpLang.ITEM_UPGRADE_TOOLTIP.text("an Extended Pattern Provider to an Expanded Pattern Provider")
                .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, level, tooltip, advancedTooltips);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return replace(context,
                TileExPatternProvider.class, ExpBlockEntities.EXP_PATTERN_PROVIDER, ExpBlocks.EXP_PATTERN_PROVIDER,
                EPPItemAndBlock.EX_PATTERN_PROVIDER_PART.getPartClass(), ExpItems.EXP_PATTERN_PROVIDER_PART.asItem());
    }
}