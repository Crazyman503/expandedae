package lu.kolja.expandedae.client.gui.widgets;

import lu.kolja.expandedae.definition.ExpLang;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public class ExpActionButton extends ExpIconButton{
    private static final Pattern PATTERN_NEW_LINE = Pattern.compile("\\n", 16);
    private final ExpIcon icon;

    public ExpActionButton(ExpActionItems action, Runnable onPress) {
        this(action, (Consumer)((a) -> onPress.run()));
    }

    public ExpActionButton(ExpActionItems action, Consumer<ExpActionItems> onPress) {
        super((btn) -> onPress.accept(action));
        Component displayName;
        Component displayValue;
        switch (action) {
            case MODIFY_PATTERNS -> {
                this.icon = ExpIcon.MODIFY_PATTERNS;
                displayName = ExpLang.GUI_TOOLTIPS_MODIFY_PATTERNS.text();
                displayValue = ExpLang.GUI_TOOLTIPS_MODIFY_PATTERNS_HINT.text();
            }
            case NEXT_PAGE -> {
                this.icon = ExpIcon.NEXT_PAGE;
                displayName = ExpLang.SWITCH_PAGE.text("Next");
                displayValue = ExpLang.SWITCH_PAGE_HINT.text("Next");
            }
            case PREV_PAGE -> {
                this.icon = ExpIcon.PREV_PAGE;
                displayName = ExpLang.SWITCH_PAGE.text("Previous");
                displayValue = ExpLang.SWITCH_PAGE_HINT.text("Previous");
            }
            case ADD_MISSING -> {
                this.icon = ExpIcon.ADD_MISSING;
                displayName = ExpLang.GUI_TOOLTIPS_ADD_MISSING.text();
                displayValue = ExpLang.GUI_TOOLTIPS_ADD_MISSING_HINT.text();
            }
            default -> throw new IllegalArgumentException("Unknown ActionItem: " + action);
        }
        this.setMessage(this.buildMessage(displayName, displayValue));
    }

    protected ExpIcon getIcon() {
        return this.icon;
    }

    private Component buildMessage(Component displayName, @Nullable Component displayValue) {
        String name = displayName.getString();
        if (displayValue == null) {
            return Component.literal(name);
        } else {
            String value = displayValue.getString();
            value = PATTERN_NEW_LINE.matcher(value).replaceAll("\n");
            StringBuilder sb = new StringBuilder(value);
            int i = sb.lastIndexOf("\n");
            if (i <= 0) {
                i = 0;
            }
            return Component.literal(name + "\n" + sb);
        }
    }
}
