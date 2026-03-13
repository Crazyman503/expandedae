package lu.kolja.expandedae.item.part;

import appeng.items.parts.PartItem;
import lu.kolja.expandedae.part.GigaPatternProviderPart;

public class GigaPatternProviderPartItem extends PartItem<GigaPatternProviderPart> {
    public GigaPatternProviderPartItem(Properties properties) {
        super(properties, GigaPatternProviderPart.class, GigaPatternProviderPart::new);
    }
}
