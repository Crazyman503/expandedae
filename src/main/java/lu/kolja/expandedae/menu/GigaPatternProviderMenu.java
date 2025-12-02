package lu.kolja.expandedae.menu;

import appeng.api.config.LockCraftingMode;
import appeng.api.config.Settings;
import appeng.api.config.YesNo;
import appeng.api.stacks.GenericStack;
import appeng.api.upgrades.IUpgradeableObject;
import appeng.helpers.externalstorage.GenericStackInv;
import appeng.helpers.patternprovider.PatternProviderLogic;
import appeng.helpers.patternprovider.PatternProviderLogicHost;
import appeng.helpers.patternprovider.PatternProviderReturnInventory;
import appeng.menu.AEBaseMenu;
import appeng.menu.SlotSemantic;
import appeng.menu.SlotSemantics;
import appeng.menu.ToolboxMenu;
import appeng.menu.guisync.GuiSync;
import appeng.menu.implementations.MenuTypeBuilder;
import appeng.menu.slot.AppEngSlot;
import appeng.menu.slot.RestrictedInputSlot;
import lu.kolja.expandedae.definition.ExpMenus;
import lu.kolja.expandedae.definition.ExpSemantics;
import lu.kolja.expandedae.enums.BlockingMode;
import lu.kolja.expandedae.helper.misc.PatternHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GigaPatternProviderMenu extends AEBaseMenu {
    protected final PatternProviderLogic logic;
    private final ToolboxMenu toolbox;
    private final List<RestrictedInputSlot> slots = new ArrayList<>(72 * 4);

    private final SlotSemantic[] SEMANTICS = {
            ExpSemantics.PAGE_1,
            ExpSemantics.PAGE_2,
            ExpSemantics.PAGE_3,
            ExpSemantics.PAGE_4
    };

    @GuiSync(3)
    public YesNo blockingMode = YesNo.NO;
    @GuiSync(4)
    public YesNo showInAccessTerminal = YesNo.YES;
    @GuiSync(5)
    public LockCraftingMode lockCraftingMode = LockCraftingMode.NONE;
    @GuiSync(6)
    public LockCraftingMode craftingLockedReason = LockCraftingMode.NONE;
    @GuiSync(7)
    public GenericStack unlockStack = null;
    @GuiSync(8)
    private BlockingMode extraBlockingMode = BlockingMode.DEFAULT;
    @GuiSync(9)
    public int currentPage = 0;

    public GigaPatternProviderMenu(int id, Inventory playerInventory, PatternProviderLogicHost host) {
        this(ExpMenus.GIGA_PATTERN_PROVIDER, id, playerInventory, host);
    }

    protected GigaPatternProviderMenu(MenuType<? extends GigaPatternProviderMenu> menuType, int id, Inventory playerInventory,
                                  PatternProviderLogicHost host) {
        super(menuType, id, playerInventory, host);
        this.createPlayerInventorySlots(playerInventory);

        this.logic = host.getLogic();
        this.toolbox = new ToolboxMenu(this);

        this.setupUpgrades(((IUpgradeableObject) host).getUpgrades());

        var patternInv = logic.getPatternInv();
        for (int x = 0; x < patternInv.size(); x++) {
            var slot = new RestrictedInputSlot(RestrictedInputSlot.PlacableItemType.ENCODED_PATTERN, patternInv, x);
            slots.add(slot);
            this.addSlot(slot, SEMANTICS[x / 72]);
        }

        for (int i = 72; i < 72 * 4; i++) {
            slots.get(i).setActive(false);
        }

        // Show first few entries of the return inv
        var returnInv = logic.getReturnInv().createMenuWrapper();
        for (int i = 0; i < PatternProviderReturnInventory.NUMBER_OF_SLOTS; i++) {
            if (i < returnInv.size()) {
                this.addSlot(new AppEngSlot(returnInv, i), SlotSemantics.STORAGE);
            }
        }

        this.registerClientAction("modifyPatterns", Integer.class, this::modifyPatterns);
        this.registerClientAction("setPage", Integer.class, this::setPage);
    }

    public void modifyPatterns(Integer mult) {
        if (this.isClientSide()) this.sendClientAction("modifyPatterns", mult);
        else {
            for (var slot : this.getSlots(SlotSemantics.ENCODED_PATTERN)) {
                slot.set(PatternHelper.modifyPatterns(slot.getItem(), mult, this.getPlayer().level()));
            }
        }
    }

    @Override
    public void broadcastChanges() {
        if (isServerSide()) {
            blockingMode = logic.getConfigManager().getSetting(Settings.BLOCKING_MODE);
            showInAccessTerminal = logic.getConfigManager().getSetting(Settings.PATTERN_ACCESS_TERMINAL);
            lockCraftingMode = logic.getConfigManager().getSetting(Settings.LOCK_CRAFTING_MODE);
            craftingLockedReason = logic.getCraftingLockedReason();
            unlockStack = logic.getUnlockStack();
            toolbox.tick();
        }

        super.broadcastChanges();
    }

    public GenericStackInv getReturnInv() {
        return logic.getReturnInv();
    }

    public YesNo getBlockingMode() {
        return blockingMode;
    }

    public BlockingMode getExtraBlockingMode() {
        return extraBlockingMode;
    }

    public LockCraftingMode getLockCraftingMode() {
        return lockCraftingMode;
    }

    public LockCraftingMode getCraftingLockedReason() {
        return craftingLockedReason;
    }

    public GenericStack getUnlockStack() {
        return unlockStack;
    }

    public YesNo getShowInAccessTerminal() {
        return showInAccessTerminal;
    }

    public ToolboxMenu getToolbox() {
        return toolbox;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setPage(int page) {
        if (this.isClientSide()) {
            sendClientAction("setPage", page);
            for (var slot : slots) {
                slot.setActive(page == slot.getSlotIndex() / 72);
            }
            return;
        }
        this.currentPage = page;
    }

    @Override
    protected ItemStack transferStackToMenu(ItemStack input) {
        for (int i = currentPage * 72; i < currentPage * 72 + 72; i++) {
            var slot = slots.get(i);
            if (slot.hasItem()) continue;
            if (slot.mayPlace(input)) {
                slot.set(input);
                return ItemStack.EMPTY;
            }
        }
        return super.transferStackToMenu(input);
    }
}
