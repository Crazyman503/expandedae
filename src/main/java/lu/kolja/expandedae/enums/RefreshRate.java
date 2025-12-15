package lu.kolja.expandedae.enums;

public enum RefreshRate {
    TICK_1(1),
    TICK_10(10),
    SECOND_1(20),
    SECOND_10(10 * 20);

    private final int ticks;

    RefreshRate(int ticks) {
        this.ticks = ticks;
    }

    public int getTicks() {
        return ticks;
    }
}
