package lu.kolja.expandedae.helper.misc;

import appeng.api.networking.IGrid;
import com.gregtechceu.gtceu.api.pattern.MultiblockState;
import net.minecraft.world.entity.player.Player;

public interface IBlockPattern {
    void eae$autoBuild(Player player, MultiblockState worldState, IGrid grid);
}
