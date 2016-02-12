package bioMorpho.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import bioMorpho.data.NetworkData;
import bioMorpho.network.packet.PacketBioMorpho;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author Brighton Ancelin
 *
 */
public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		PacketBioMorpho packetBioMorpho = PacketTypeHandler.buildPacket(packet.data);
		packetBioMorpho.execute(manager, player);
	}
	
}
