package lu.kolja.expandedae.helper.misc;

import appeng.api.crafting.PatternDetailsHelper;
import appeng.api.stacks.GenericStack;
import appeng.crafting.pattern.AEProcessingPattern;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Arrays;

public class PatternHelper {

    public static final int BASE_FACTOR = 2;

    public static ItemStack modifyPatterns(ItemStack stack, Pair<Boolean, Integer> info, Level level) {
        var detail = PatternDetailsHelper.decodePattern(stack, level);
        if (detail instanceof AEProcessingPattern processingPattern) {
            var input = Arrays.stream(processingPattern.getSparseInputs()).toArray(GenericStack[]::new);
            var output = Arrays.stream(processingPattern.getOutputs()).toArray(GenericStack[]::new);
            if (checkModify(input, getScale(info.getSecond()), info.getFirst()) && checkModify(output, getScale(info.getSecond()), info.getFirst())) {
                var mulInput = new GenericStack[input.length];
                var mulOutput = new GenericStack[output.length];
                modifyStacks(input, mulInput, getScale(info.getSecond()), info.getFirst());
                modifyStacks(output, mulOutput, getScale(info.getSecond()), info.getFirst());
                return PatternDetailsHelper.encodeProcessingPattern(
                        mulInput,
                        mulOutput
                );
            }
            return stack;
        }
        return stack;
    }

    public static int getScale(int multiplier) {
        return BASE_FACTOR * multiplier;
    }

    public static boolean checkModify(GenericStack[] stacks, int scale, boolean division) {
        if (division) {
            for (var stack : stacks) {
                if (stack != null) {
                    if (stack.amount() % scale != 0) {
                        return false;
                    }
                }
            }
        } else {
            for (var stack : stacks) {
                if (stack != null) {
                    long upper = 999999L * stack.what().getAmountPerUnit();
                    if (stack.amount() * scale > upper) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void modifyStacks(GenericStack[] stacks, GenericStack[] des, int scale, boolean division) {
        for (int i = 0; i < stacks.length; i ++) {
            if (stacks[i] != null) {
                long amt = division ? stacks[i].amount() / scale : stacks[i].amount() * scale;
                des[i] = new GenericStack(stacks[i].what(), amt);
            }
        }
    }
}
