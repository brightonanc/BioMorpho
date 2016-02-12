package bioMorpho.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import bioMorpho.manager.LifeformStorageManager;
import bioMorpho.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author Brighton Ancelin
 *
 */
public class PacketLFSMAll extends PacketBioMorpho {
	
	protected NBTTagCompound nbt;
	
	public PacketLFSMAll() {
		super(PacketTypeHandler.LFSM_ALL);
	}
	
	@Override
	public void writeData(DataOutputStream dataStream) throws IOException {
		writeNBTToStream(dataStream, LifeformStorageManager.getSidedInstance().writeAllDataToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void readData(DataInputStream dataStream) throws IOException {
		this.nbt = readNBTFromStream(dataStream);
	}
	
	@Override
	public void execute(INetworkManager manager, Player player) {
		LifeformStorageManager.getSidedInstance().readAllDataFromNBT(this.nbt);
	}
	
}
