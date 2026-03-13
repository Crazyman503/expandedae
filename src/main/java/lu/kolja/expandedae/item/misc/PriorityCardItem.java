package lu.kolja.expandedae.item.misc;

import appeng.api.parts.IPartHost;
import appeng.helpers.IPriorityHost;
import appeng.items.AEBaseItem;
import appeng.util.InteractionUtil;
import lu.kolja.expandedae.definition.ExpLang;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PriorityCardItem extends AEBaseItem {
    public static final String NBT_PRIO = "priority";
    public static final String NBT_MODE = "mode";
    public PriorityCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (ctx.getLevel().isClientSide) return InteractionResult.FAIL;
        var block = ctx.getLevel().getBlockEntity(ctx.getClickedPos());
        var tag = ctx.getItemInHand().getOrCreateTag();
        var prio = tag.getInt(NBT_PRIO);
        var mode = tag.getBoolean(NBT_MODE);

        Component be;
        if (block instanceof IPriorityHost prioHost) {
            prioHost.setPriority(prio);
            be = block.getBlockState().getBlock().getName();
        } else if (block instanceof IPartHost partHost && partHost.getPart(ctx.getHorizontalDirection()) instanceof IPriorityHost prioPart) {
            prioPart.setPriority(prio);
            var partItem = partHost.getPart(ctx.getClickedFace()).getPartItem().asItem();
            be = partItem.getName(new ItemStack(partItem));
        } else return InteractionResult.FAIL;
        ctx.getPlayer().sendSystemMessage(ExpLang.PRIO_CHANGED.text(be, "§b" + prio));
        prio += mode ? 1 : -1;
        tag.putInt(NBT_PRIO, prio);
        ctx.getItemInHand().setTag(tag);
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) return InteractionResultHolder.fail(player.getItemInHand(hand));
        var tag = player.getItemInHand(hand).getOrCreateTag();
        if (InteractionUtil.isInAlternateUseMode(player)) {
            tag.putInt(NBT_PRIO, 0);
            player.sendSystemMessage(ExpLang.PRIO_RESET.text("§b0"));
        } else {
            var mode = !tag.getBoolean(NBT_MODE);
            tag.putBoolean(NBT_MODE, mode);
            player.sendSystemMessage(ExpLang.CHANGED_MODE.text(mode ? "§2Increase" : "§cDecrease"));
        }
        player.getItemInHand(hand).setTag(tag);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.add(ExpLang.PRIO_CARD_HINT_1.text());
        list.add(ExpLang.PRIO_CARD_HINT_2.text());
        var tag = stack.getOrCreateTag();
        list.add(ExpLang.CURRENT.text("§b" + tag.getInt(NBT_PRIO)));
        list.add(ExpLang.PRIO_CARD_HINT_3.text());
        list.add(ExpLang.CURRENT.text((tag.getBoolean(NBT_MODE) ? "§2Increase" : "§cDecrease")));
        super.appendHoverText(stack, level, list, flag);
    }
}
