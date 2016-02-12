package bioMorpho.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import bioMorpho.manager.ModelManager;
import bioMorpho.manager.ModelManager_;
import bioMorpho.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author Brighton Ancelin
 *
 */
public class PacketModelManData_ extends PacketBioMorpho {
	
	public static final String NBT_KEY = "NBTTagList with Data";
	
	protected String[] usernames;
	protected NBTTagList nbtData;
	
	public PacketModelManData_() {
		super(PacketTypeHandler.MODEL_MAN_DATA_);
	}
	
	public PacketModelManData_(String username) {
		this(new String[] {username});
	}
	
	public PacketModelManData_(String[] usernames) {
		this();
		this.usernames = usernames;
	}

	@Override
	public void writeData(DataOutputStream dataStream) throws IOException {
		dataStream.writeInt(this.usernames.length);
		for(String curUsername : this.usernames) dataStream.writeUTF(curUsername);
		NBTTagList nbtList = new NBTTagList();
		for(String curUsername : this.usernames) {
			nbtList.appendTag(ModelManager_.getSidedInstance().writeModelDataToNBT(new NBTTagCompound(), curUsername));
		}
		NBTTagCompound nbtCompound = new NBTTagCompound();
		nbtCompound.setTag(NBT_KEY, nbtList);
		writeNBTToStream(dataStream, nbtCompound);
	}

	@Override
	public void readData(DataInputStream dataStream) throws IOException {
		int length = dataStream.readInt();
		this.usernames = new String[length];
		for(int i = 0; i < length; i++) this.usernames[i] = dataStream.readUTF();
		NBTTagCompound nbtCompound = readNBTFromStream(dataStream);
		this.nbtData = nbtCompound.getTagList(NBT_KEY);
	}

	@Override
	public void execute(INetworkManager manager, Player player) {
		for(int i = 0; i < this.nbtData.tagCount(); i++) {
			ModelManager_.getSidedInstance().readModelDataFromNBT((NBTTagCompound)this.nbtData.tagAt(i), this.usernames[i]);
		}
	}
}
