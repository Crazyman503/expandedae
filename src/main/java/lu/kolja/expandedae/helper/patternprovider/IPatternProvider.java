package lu.kolja.expandedae.helper.patternprovider;

import com.mojang.datafixers.util.Pair;
import lu.kolja.expandedae.enums.BlockingMode;

public interface IPatternProvider {
    void expandedae$modifyPatterns(Pair<Boolean, Integer> info);

    BlockingMode expandedae$getBlockingMode();

    void setBlockingMode(BlockingMode blockingMode);
}
