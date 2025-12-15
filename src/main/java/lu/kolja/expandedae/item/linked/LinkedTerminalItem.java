package lu.kolja.expandedae.item.linked;

import appeng.core.localization.GuiText;
import appeng.core.localization.Tooltips;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import lu.kolja.expandedae.definition.ExpLang;
import lu.kolja.expandedae.helper.misc.IBlockPattern;
import lu.kolja.expandedae.helper.misc.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LinkedTerminalItem extends LinkedItem {

    public LinkedTerminalItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        var player = ctx.getPlayer();
        var level = ctx.getLevel();
        if (player == null || !player.isShiftKeyDown() || level.isClientSide) return InteractionResult.PASS;
        var pos = ctx.getClickedPos();
        var item = ctx.getItemInHand();
        if (!(MetaMachine.getMachine(level, pos) instanceof IMultiController controller)) return InteractionResult.PASS;
        if (controller.isFormed()) return InteractionResult.PASS; // TODO: destroy if formed
        var grid = getLinkedGrid(item, level, player);
        if (grid == null) return InteractionResult.PASS;
        ((IBlockPattern) controller.getPattern()).eae$autoBuild(player, controller.getMultiblockState(), grid);
        player.getCooldowns().addCooldown(this, 20);
        return InteractionResult.sidedSuccess(false);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
        var pos = linkedPos(stack);
        if (pos != null) {
            lines.add(Tooltips.of(GuiText.Linked, Tooltips.GREEN));
            TooltipHelper.shiftInfo(lines,
                    ExpLang.BOUND_TO.text(pos.dimension().location().getPath() + "[" + pos.pos().toShortString() + "]")
                            .withStyle(ChatFormatting.GOLD),
                    ExpLang.LINKED_TERM_HINT.text()
            );
        } else {
            lines.add(Tooltips.of(GuiText.Unlinked, Tooltips.RED));
        }
        super.appendHoverText(stack, level, lines, flag);
    }
}
