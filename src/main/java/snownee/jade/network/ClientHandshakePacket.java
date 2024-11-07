package snownee.jade.network;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import snownee.jade.api.JadeIds;
import snownee.jade.util.CommonProxy;

// This class of structure should not be changed
public record ClientHandshakePacket(String protocolVersion) implements CustomPacketPayload {
	public static final Type<ClientHandshakePacket> TYPE = new Type<>(JadeIds.PACKET_CLIENT_HANDSHAKE);
	public static final StreamCodec<RegistryFriendlyByteBuf, ClientHandshakePacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			ClientHandshakePacket::protocolVersion,
			ClientHandshakePacket::new);

	public static void handle(ClientHandshakePacket message, ServerPayloadContext context) {
		context.execute(() -> CommonProxy.playerHandshake(message.protocolVersion, context.player()));
	}

	@Override
	public @NotNull Type<ClientHandshakePacket> type() {
		return TYPE;
	}
}