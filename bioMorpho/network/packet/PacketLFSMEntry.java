package bioMorpho.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.Player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import bioMorpho.manager.LifeformStorageManager;
import bioMorpho.network.PacketTypeHandler;

/**
 * @author Brighton Ancelin
 *
 */
public class PacketLFSMEntry extends PacketBioMorpho {
	
	protected int id;
	protected NBTTagCompound nbt;
	
	public PacketLFSMEntry() {super(PacketTypeHandler.LFSM_ENTRY);}
	
	public PacketLFSMEntry(int id) {
		this();
		this.id = id;
	}
	
	@Override
	public void writeData(DataOutputStream dataStream) throws IOException {
		writeNBTToStream(dataStream, LifeformStorageManager.getSidedInstance().writeEntryDataToNBT(new NBTTagCompound(), this.id));
	}
	
	@Override
	public void readData(DataInputStream dataStream) throws IOException {
		this.nbt = readNBTFromStream(dataStream);
	}
	
	@Override
	public void execute(INetworkManager manager, Player player) {
		LifeformStorageManager.getSidedInstance().readEntryDataFromNBT(this.nbt);
	}
	
}
