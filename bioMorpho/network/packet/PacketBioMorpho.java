package bioMorpho.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import bioMorpho.manager.ModelManager;
import bioMorpho.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class PacketBioMorpho {
	
	public PacketTypeHandler packetType;
	public boolean isChunkDataPacket;
	
	// IMPORTANT: All child classes of PacketBioMorpho MUST have a constructor with no parameters,
	// otherwise they cannot be generated by the packet factory in PacketTypeHandler
	public PacketBioMorpho(PacketTypeHandler packetType) {
		this.packetType = packetType;
		this.isChunkDataPacket = false;
	}
	
	public byte[] getData() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(baos);
		try {
			dataStream.writeByte((byte)this.packetType.ordinal());
			this.writeData(dataStream);
		} catch(IOException e) {e.printStackTrace();}
		return baos.toByteArray();
	}
	
	public void populateWithData(DataInputStream dataStream) {
		try {this.readData(dataStream);}
		catch(IOException e) {e.printStackTrace();}
	}
	
	public static void writeNBTToStream(DataOutputStream dataStream, NBTTagCompound nbt) throws IOException {
		byte[] byteArray = CompressedStreamTools.compress(nbt);
		dataStream.writeInt(byteArray.length);
		dataStream.write(byteArray);
	}
	public static NBTTagCompound readNBTFromStream(DataInputStream dataStream) throws IOException {
		int size = dataStream.readInt();
		byte[] byteArray = new byte[size];
		dataStream.readFully(byteArray);
		return CompressedStreamTools.decompress(byteArray);
	}
	
	public abstract void writeData(DataOutputStream dataStream) throws IOException;
	public abstract void readData(DataInputStream dataStream) throws IOException;
	public abstract void execute(INetworkManager manager, Player player);
	
}
