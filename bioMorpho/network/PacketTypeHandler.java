package bioMorpho.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import bioMorpho.data.NetworkData;
import bioMorpho.network.packet.PacketBioMorpho;
import bioMorpho.network.packet.PacketInventoryManData;
import bioMorpho.network.packet.PacketKeyBinding;
import bioMorpho.network.packet.PacketLFSMAll;
import bioMorpho.network.packet.PacketLFSMEntry;
import bioMorpho.network.packet.PacketLifeformIndexManData;
import bioMorpho.network.packet.PacketLifeformIndexToClient;
import bioMorpho.network.packet.PacketModelManData;
import bioMorpho.network.packet.PacketModelManData_;
import bioMorpho.network.packet.PacketTileUpdate;

/**
 * @author Brighton Ancelin
 *
 */
public enum PacketTypeHandler {
	
	MODEL_MAN_DATA(PacketModelManData.class),
	TILE_UPDATE(PacketTileUpdate.class),
	KEY_BINDING(PacketKeyBinding.class),
	INVENTORY_MAN_DATA(PacketInventoryManData.class),
	LIFEFORM_INDEX_MAN_DATA(PacketLifeformIndexManData.class),
	MODEL_MAN_DATA_(PacketModelManData_.class),
	LIFEFORM_INDEX_TO_CLIENT(PacketLifeformIndexToClient.class),
	LFSM_ENTRY(PacketLFSMEntry.class),
	LFSM_ALL(PacketLFSMAll.class),
	;
	
	protected Class<? extends PacketBioMorpho> clazz;
	
	private PacketTypeHandler(Class<? extends PacketBioMorpho> clazz) {
		this.clazz = clazz;
	}
	
	public static PacketBioMorpho buildPacket(byte[] data) {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bais);
		
		PacketBioMorpho packet = null;
		try {
			int typeOfPacket = dis.readByte();
			packet = values()[typeOfPacket].clazz.newInstance();
		} catch(Exception e) {e.printStackTrace();}
		
		packet.populateWithData(dis);
		
		return packet;
	}
	
	@Deprecated
	public static PacketBioMorpho newPacket_(PacketTypeHandler type) {
		PacketBioMorpho packet = null;
		try {packet = values()[type.ordinal()].clazz.newInstance();}
		catch(Exception e) {e.printStackTrace();}
		return packet;
	}
	
	public static Packet convertToMinecraftPacketForm(PacketBioMorpho packetBioMorpho) {
		Packet250CustomPayload conversion = new Packet250CustomPayload();
		byte[] data = packetBioMorpho.getData();
		conversion.channel = NetworkData.CHANNEL;
		conversion.data = data;
		conversion.length = data.length;
		conversion.isChunkDataPacket = packetBioMorpho.isChunkDataPacket;
		return conversion;
	}
	
}
