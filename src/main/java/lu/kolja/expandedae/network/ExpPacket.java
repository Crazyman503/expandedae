package lu.kolja.expandedae.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface ExpPacket<T extends ExpPacket<T>> {
    void encode(T packet, FriendlyByteBuf buf);

    T decode(FriendlyByteBuf buf);

    void handle(T packet, Supplier<NetworkEvent.Context> context);
}
