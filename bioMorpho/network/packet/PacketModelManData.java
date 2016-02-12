package bioMorpho.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import bioMorpho.manager.ModelManager;
import bioMorpho.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author Brighton Ancelin
 *
 */
public class PacketModelManData extends PacketBioMorpho {
	
	public static final String NBT_KEY = "NBTTagList with Data";
	
	protected String[] playerSet;
	protected NBTTagList nbtData;
	
	public PacketModelManData() {
		super(PacketTypeHandler.MODEL_MAN_DATA);
	}
	
	public PacketModelManData(String[] playerSet) {
		this();
		this.playerSet = playerSet;
	}
	
	public PacketModelManData(String player) {
		this(new String[] {player});
	}

	@Override
	public void writeData(DataOutputStream dataStream) throws IOException {
		NBTTagList data = new NBTTagList();
		for(int i = 0; i < this.playerSet.length; i++) {
			data.appendTag(ModelManager.getSidedInstance().writePlayerDataToNBT(new NBTTagCompound(), this.playerSet[i]));
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
			ModelManager.getSidedInstance().readPlayerDataFromNBT((NBTTagCompound)this.nbtData.tagAt(i));
		}
	}
}
