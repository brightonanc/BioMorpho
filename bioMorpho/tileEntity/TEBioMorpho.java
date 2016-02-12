package bioMorpho.tileEntity;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import bioMorpho.data.NBTData;
import bioMorpho.network.PacketTypeHandler;
import bioMorpho.network.packet.PacketTileUpdate;
import bioMorpho.util.IdGenerator;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

/**
 * @author Brighton Ancelin
 *
 */
public class TEBioMorpho extends TileEntity {
	
	public static final Random rand = new Random();
	
	protected ForgeDirection orientation;
	
	{
		this.orientation = ForgeDirection.UNKNOWN;
	}
	
	public ForgeDirection getOrientation() { return this.orientation; }
	public void setOrientation(ForgeDirection orientation) { this.orientation = orientation; }
	
	public NBTTagCompound createTileEntityPacketNBT(NBTTagCompound nbtCompound) {
		this.writeToNBT(nbtCompound);
		return nbtCompound;
	}
	
	public void handleTileEntityPacketNBT(NBTTagCompound nbtCompound) {
		this.readFromNBT(nbtCompound);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		return PacketTypeHandler.convertToMinecraftPacketForm(new PacketTileUpdate(this.xCoord, this.yCoord, this.zCoord, this.createTileEntityPacketNBT(new NBTTagCompound()), true));
	}
	
	public void forceSendUpdatePacket() {
		switch(FMLCommonHandler.instance().getEffectiveSide()) {
		case SERVER: PacketDispatcher.sendPacketToAllPlayers(this.getDescriptionPacket()); break;
		case CLIENT: PacketDispatcher.sendPacketToServer(this.getDescriptionPacket()); break;
		default: break;
		}
	}
	
	/**
	 * Used for storage between save loads AND updates
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbtCompound) {
		super.writeToNBT(nbtCompound);
		nbtCompound.setInteger(NBTData.TILE_ORIENTATION, this.orientation.ordinal());
	}
	
	
	/**
	 * Used for storage between save loads AND updates
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbtCompound) {
		super.readFromNBT(nbtCompound);
		this.orientation = ForgeDirection.values()[nbtCompound.getInteger(NBTData.TILE_ORIENTATION)];
	}
	
}
