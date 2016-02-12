package bioMorpho.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

import bioMorpho.manager.LifeformIndexManager;
import bioMorpho.network.PacketTypeHandler;

/**
 * This packet is designed to be sent to a client from the server only.<br>
 * NOT intended for a client to send to the server.
 * 
 * @author Brighton Ancelin
 * 
 */
public class PacketLifeformIndexToClient extends PacketBioMorpho {
	
	protected int index;
	
	public PacketLifeformIndexToClient() {super(PacketTypeHandler.LIFEFORM_INDEX_TO_CLIENT);}
	
	public PacketLifeformIndexToClient(int index) {
		this();
		this.index = index;
	}
	
	@Override
	public void writeData(DataOutputStream dataStream) throws IOException {
		dataStream.writeInt(this.index);
	}
	
	@Override
	public void readData(DataInputStream dataStream) throws IOException {
		this.index = dataStream.readInt();
	}
	
	@Override
	public void execute(INetworkManager manager, Player player) {
		LifeformIndexManager.getClientInstance().setClientPlayerIndex(this.index);
	}
	
}
