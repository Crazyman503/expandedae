package lu.kolja.expandedae.enums;

import appeng.core.localization.LocalizationEnum;

public enum BlockingMode implements LocalizationEnum {
    ALL("Full"),
    SMART("Smart"),
    DEFAULT("Default");

    private final String text;
    private final String key;

    BlockingMode(String text) {
        this.text = text;
        this.key = "gui.expandedae.blocking_mode." + text;
    }

    @Override
    public String getEnglishText() {
        return text;
    }

    @Override
    public String getTranslationKey() {
        return key;
    }
}