package lu.kolja.expandedae.helper.misc;

import appeng.api.stacks.AEKey;
import net.minecraft.core.BlockPos;

import java.util.Set;

public interface IStorageLocations {
    Set<BlockPos> eae$getStorageLocations(AEKey what);
}
