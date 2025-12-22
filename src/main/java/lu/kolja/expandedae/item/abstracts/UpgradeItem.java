package lu.kolja.expandedae.item.abstracts;

import appeng.block.AEBaseEntityBlock;
import appeng.blockentity.AEBaseBlockEntity;
import appeng.blockentity.networking.CableBusBlockEntity;
import appeng.core.definitions.BlockDefinition;
import appeng.items.parts.PartItem;
import appeng.parts.AEBasePart;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class UpgradeItem extends Item {
    public UpgradeItem(Properties pProperties) {
        super(pProperties);
    }

    protected void replaceTile(Level world, BlockPos pos, BlockEntity oldTile, BlockEntity newTile, BlockState newBlock) {
        CompoundTag contents = oldTile.serializeNBT();
        world.removeBlockEntity(pos);
        world.removeBlock(pos, false);
        world.setBlock(pos, newBlock, 3);
        world.setBlockEntity(newTile);
        newTile.deserializeNBT(contents);
        if (newTile instanceof AEBaseBlockEntity aeTile) {
            aeTile.markForUpdate();
        } else {
            newTile.setChanged();
        }
    }

    protected InteractionResult replace(
            UseOnContext context,
            Class<? extends AEBaseBlockEntity> oldBE, BlockEntityType<?> newBE,
            BlockDefinition<? extends AEBaseEntityBlock<?>> newBlock, @Nullable Class<? extends AEBasePart> oldPart, @Nullable PartItem<?> newPart) {
        var pos = context.getClickedPos();
        var world = context.getLevel();
        var entity = world.getBlockEntity(pos);
        if (entity != null) {
            var ctx = new BlockPlaceContext(context);
            var tClazz = entity.getClass();
            if (tClazz == oldBE) {

                var originState = world.getBlockState(pos);
                var state = newBlock.block().getStateForPlacement(ctx);
                if (state == null) {
                    return InteractionResult.PASS;
                }
                for (var sp : originState.getValues().entrySet()) {
                    var pt = sp.getKey();
                    var va = sp.getValue();
                    try {
                        if (state.hasProperty(pt)) {
                            state = state.<Comparable, Comparable>setValue((Property) pt, va);
                        }
                    } catch (Exception ignore) {
                        // NO-OP
                    }
                }

                BlockEntity te = newBE.create(pos, state);
                replaceTile(world, pos, entity, te, state);
                context.getItemInHand().shrink(1);
                return InteractionResult.CONSUME;

            } else if (oldPart != null && entity instanceof CableBusBlockEntity cable) {
                Vec3 hitVec = context.getClickLocation();
                Vec3 hitInBlock = new Vec3(hitVec.x - pos.getX(), hitVec.y - pos.getY(), hitVec.z - pos.getZ());
                var part = cable.getCableBus().selectPartLocal(hitInBlock).part;
                if (part instanceof AEBasePart basePart && part.getClass() == oldPart) {
                    var side = basePart.getSide();
                    var contents = new CompoundTag();

                    part.writeToNBT(contents);
                    var p = cable.replacePart(newPart, side, context.getPlayer(), null);
                    if (p != null) {
                        p.readFromNBT(contents);
                    }
                } else {
                    return InteractionResult.PASS;
                }
                context.getItemInHand().shrink(1);
                return InteractionResult.sidedSuccess(world.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }
}
