package bioMorpho.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import bioMorpho.manager.LifeformIndexManager;
import bioMorpho.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

/**
 * This packet is designed to be sent to the server from a client only.<br>
 * NOT intended for a server to send to a client.
 * 
 * @author Brighton Ancelin
 * 
 */
public class PacketLifeformIndexManData extends PacketBioMorpho {
	
	protected String username;
	protected int index;
	
	public PacketLifeformIndexManData() {super(PacketTypeHandler.LIFEFORM_INDEX_MAN_DATA);}
	
	@Override
	public void writeData(DataOutputStream dataStream) throws IOException {
		dataStream.writeUTF(LifeformIndexManager.getClientInstance().getPlayerUsername());
		dataStream.writeInt(LifeformIndexManager.getClientInstance().getIndex());
	}
	
	@Override
	public void readData(DataInputStream dataStream) throws IOException {
		this.username = dataStream.readUTF();
		this.index = dataStream.readInt();
	}
	
	@Override
	public void execute(INetworkManager manager, Player player) {
		LifeformIndexManager.getServerInstance().putKeyAndValueIntoMap(this.username, this.index);
	}
	
}
