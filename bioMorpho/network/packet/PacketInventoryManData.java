package bioMorpho.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import bioMorpho.manager.InventoryManager;
import bioMorpho.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author Brighton Ancelin
 *
 */
public class PacketInventoryManData extends PacketBioMorpho {
	
	public static final String NBT_KEY = "NBTTagList with Data";
	
	protected String[] players;
	protected NBTTagList nbtData;
	
	public PacketInventoryManData() {
		super(PacketTypeHandler.INVENTORY_MAN_DATA);
	}
	
	public PacketInventoryManData(String[] players) {
		this();
		this.players = players;
	}
	
	public PacketInventoryManData(String player) {
		this(new String[] {player});
	}
	
	@Override
	public void writeData(DataOutputStream dataStream) throws IOException {
		NBTTagList data = new NBTTagList();
		for(int i = 0; i < this.players.length; i++) {
			data.appendTag(InventoryManager.getSidedInstance().writePlayerDataToNBT(new NBTTagCompound(), this.players[i]));
		}
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag(NBT_KEY, data);
		writeNBTToStream(dataStream, nbt);
	}

	@Override
	public void readData(DataInputStream dataStream) throws IOException {
		NBTTagCompound nbt = readNBTFromStream(dataStream);
		this.nbtData = nbt.getTagList(NBT_KEY);
	}

	@Override
	public void execute(INetworkManager manager, Player player) {
		for(int i = 0; i < this.nbtData.tagCount(); i++) {
			InventoryManager.getSidedInstance().readPlayerDataFromNBT((NBTTagCompound)this.nbtData.tagAt(i));
		}
	}
	
}
