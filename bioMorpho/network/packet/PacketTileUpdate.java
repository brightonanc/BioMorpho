package bioMorpho.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import bioMorpho.network.PacketTypeHandler;
import bioMorpho.tileEntity.TEBioMorpho;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * @author Brighton Ancelin
 *
 */
public class PacketTileUpdate extends PacketBioMorpho {
	
	protected int x, y, z;
	protected NBTTagCompound nbtCompound;
	protected boolean updatesAllClients;
	
	public PacketTileUpdate() {
		super(PacketTypeHandler.TILE_UPDATE);
		this.isChunkDataPacket = true;
	}
	
	public PacketTileUpdate(int x, int y, int z, NBTTagCompound nbtCompound, boolean sendToAllClientsIfApplicable) {
		this();
		this.x = x;
		this.y = y;
		this.z = z;
		this.nbtCompound = nbtCompound;
		this.updatesAllClients = sendToAllClientsIfApplicable;
	}

	@Override
	public void writeData(DataOutputStream dataStream) throws IOException {
		dataStream.writeInt(this.x);
		dataStream.writeInt(this.y);
		dataStream.writeInt(this.z);
		dataStream.writeBoolean(this.updatesAllClients);
		writeNBTToStream(dataStream, nbtCompound);
	}

	@Override
	public void readData(DataInputStream dataStream) throws IOException {
		this.x  = dataStream.readInt();
		this.y  = dataStream.readInt();
		this.z  = dataStream.readInt();
		this.updatesAllClients = dataStream.readBoolean();
		this.nbtCompound = readNBTFromStream(dataStream);
	}

	@Override
	public void execute(INetworkManager manager, Player player) {
		TileEntity tileEntity = ((EntityPlayer)player).worldObj.getBlockTileEntity(this.x, this.y, this.z);
		switch(FMLCommonHandler.instance().getEffectiveSide()) {
		case CLIENT:
			//tileEntity = FMLClientHandler.instance().getClient().theWorld.getBlockTileEntity(this.x, this.y, this.z);
			if(tileEntity != null  && tileEntity instanceof TEBioMorpho) {
				((TEBioMorpho)tileEntity).handleTileEntityPacketNBT(this.nbtCompound);
			}
			break;
		case SERVER:
			//tileEntity = ((EntityPlayer)player).worldObj.getBlockTileEntity(this.x, this.y, this.z);
			if(tileEntity != null  && tileEntity instanceof TEBioMorpho) {
				((TEBioMorpho)tileEntity).handleTileEntityPacketNBT(this.nbtCompound);
			}
			if(this.updatesAllClients) PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.convertToMinecraftPacketForm(new PacketTileUpdate(this.x, this.y, this.z, this.nbtCompound, this.updatesAllClients)));
			break;
		default: break;
		}
	}

}
