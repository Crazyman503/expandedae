package lu.kolja.expandedae.xmod.extendedae;

import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.ItemDefinition;
import com.glodblock.github.extendedae.common.EPPItemAndBlock;
import com.glodblock.github.extendedae.common.items.ItemMEPackingTape;
import lu.kolja.expandedae.definition.ExpBlocks;
import lu.kolja.expandedae.definition.ExpItems;
import lu.kolja.expandedae.item.misc.ExtPatternProviderUpgradeItem;

public class ExtendedAE {
    public static ItemDefinition<ExtPatternProviderUpgradeItem> EXT_PATTERN_PROVIDER_UPGRADE;

    public static void initItems() {
        EXT_PATTERN_PROVIDER_UPGRADE = ExpItems.item("Extended Pattern Provider Upgrade", "ext_pattern_provider_upgrade", ExtPatternProviderUpgradeItem::new);
    }

    public ExtendedAE() {
        ItemMEPackingTape.registerPackableDevice(ExpBlocks.EXP_PATTERN_PROVIDER.id());
        ItemMEPackingTape.registerPackableDevice(ExpItems.EXP_PATTERN_PROVIDER_PART.id());
        ItemMEPackingTape.registerPackableDevice(ExpBlocks.EXP_IO_PORT.id());

        Upgrades.add(ExpItems.AUTO_COMPLETE_CARD, EPPItemAndBlock.EX_PATTERN_PROVIDER, 1, "group.ex_pattern_provider.name");
        Upgrades.add(ExpItems.AUTO_COMPLETE_CARD, EPPItemAndBlock.EX_PATTERN_PROVIDER_PART, 1, "group.ex_pattern_provider.name");
        /*
        Upgrades.add(ExpItems.ADVANCED_BLOCKING_CARD, EPPItemAndBlock.EX_INTERFACE,1, "group.ex_interface.name");
        Upgrades.add(ExpItems.ADVANCED_BLOCKING_CARD, EPPItemAndBlock.EX_INTERFACE_PART,1, "group.ex_interface.name");
        Upgrades.add(ExpItems.ADVANCED_BLOCKING_CARD, EPPItemAndBlock.OVERSIZE_INTERFACE,1, "group.oversize_interface.name");
        Upgrades.add(ExpItems.ADVANCED_BLOCKING_CARD, EPPItemAndBlock.OVERSIZE_INTERFACE_PART,1, "group.oversize_interface.name");

        Upgrades.add(ExpItems.STICKY_CARD, EPPItemAndBlock.TAG_STORAGE_BUS,1, "group.tag_storage_bus.name");
        Upgrades.add(ExpItems.STICKY_CARD, EPPItemAndBlock.MOD_STORAGE_BUS,1, "group.mod_storage_bus.name");
        Upgrades.add(ExpItems.STICKY_CARD, EPPItemAndBlock.PRECISE_STORAGE_BUS,1, "group.precise_storage_bus.name");
        */
    }
}