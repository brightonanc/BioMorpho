package bioMorpho.manager;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import bioMorpho.data.InventoryData;
import bioMorpho.data.NBTData;
import bioMorpho.inventory.InventoryBioMorphoPlayer;
import bioMorpho.network.PacketTypeHandler;
import bioMorpho.network.packet.PacketInventoryManData;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Brighton Ancelin
 *
 */
public class InventoryManager {
	
	private static InventoryManagerServer serverInstance = new InventoryManagerServer();
	private static InventoryManagerClient clientInstance = new InventoryManagerClient();
	
	public static InventoryManagerServer getServerInstance() {
		return FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER ? serverInstance : null;
	}
	
	public static InventoryManagerClient getClientInstance() {
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT ? clientInstance : null;
	}
	
	public static InventoryManager getSidedInstance() {
		switch(FMLCommonHandler.instance().getEffectiveSide()) {
		case SERVER: return serverInstance;
		case CLIENT: return clientInstance;
		default: return null;
		}
	}
	
	public static class InventoryManagerServer extends InventoryManager {
		
		public void sendInventoryUpdateToAllClients(String username) {
			PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.convertToMinecraftPacketForm(new PacketInventoryManData(username)));
		}
		
		public void initNewPlayer(String username) {
			if(!this.customInventoryMap.containsKey(username)) this.addDefaultCustomInventoryForPlayer(username);
		}
		
		public void addDefaultCustomInventoryForPlayer(String username) {
			InventoryBioMorphoPlayer customInventory = new InventoryBioMorphoPlayer(username);
			this.putKeyAndValueIntoCustomInventoryMap(username, customInventory);
		}
		
		@Override
		protected void putKeyAndValueIntoCustomInventoryMap(String key, InventoryBioMorphoPlayer value) {
			super.putKeyAndValueIntoCustomInventoryMap(key, value);
			this.sendInventoryUpdateToAllClients(key);
		}
		
	}
	
	public static class InventoryManagerClient extends InventoryManager {
		
		public InventoryBioMorphoPlayer getCustomInventoryForClientPlayer() {
			return this.customInventoryMap.get(FMLClientHandler.instance().getClient().thePlayer.username);
		}
		
		public ItemStack getClientGemOfGenesis() {
			return this.getCustomInventoryForClientPlayer() != null ? this.getCustomInventoryForClientPlayer().getStackInSlot(InventoryData.BIO_MORPHO_PLAYER_GEM_INDEX) : null;
		}
		
		@Deprecated
		public void alertServerOfUpdate(String username) {
			PacketDispatcher.sendPacketToServer(PacketTypeHandler.convertToMinecraftPacketForm(new PacketInventoryManData(username)));
		}
		
	}
	
	public final Map<String, InventoryBioMorphoPlayer> customInventoryMap = new HashMap<String, InventoryBioMorphoPlayer>();
	
	protected void putKeyAndValueIntoCustomInventoryMap(String key, InventoryBioMorphoPlayer value) {
		this.customInventoryMap.put(key, value);
	}
	
	public InventoryBioMorphoPlayer getCustomInventoryForPlayer(String username) {
		return this.customInventoryMap.get(username);
	}
	
	
	
	public NBTTagCompound writeAllDataToNBT(NBTTagCompound nbtInventoryManagerCompound) {
		NBTTagList nbtPlayersList = new NBTTagList();
		for(int i = 0; i < this.customInventoryMap.size(); i++) {
			String key = (String)this.customInventoryMap.keySet().toArray()[i];
			nbtPlayersList.appendTag(this.writePlayerDataToNBT(new NBTTagCompound(), key));
		}
		nbtInventoryManagerCompound.setTag(NBTData.INV_MAN_CUSTOM_INVENTORY_MAP, nbtPlayersList);
		return nbtInventoryManagerCompound;
	}
	public NBTTagCompound writePlayerDataToNBT(NBTTagCompound nbtPlayerDataCompound, String username) {
		nbtPlayerDataCompound.setString(NBTData.INV_MAN_PLAYER_NAME, username);
		nbtPlayerDataCompound.setTag(NBTData.INV_MAN_PLAYER_INVENTORY, this.getCustomInventoryForPlayer(username).writeToNBT(new NBTTagCompound()));
		return nbtPlayerDataCompound;
	}
	
	public void readAllDataFromNBT(NBTTagCompound nbtInventoryManagerCompound) {
		NBTTagList nbtPlayersList = nbtInventoryManagerCompound.getTagList(NBTData.INV_MAN_CUSTOM_INVENTORY_MAP);
		for(int i = 0; i < nbtPlayersList.tagCount(); i++) {
			this.readPlayerDataFromNBT((NBTTagCompound)nbtPlayersList.tagAt(i));
		}
	}
	public void readPlayerDataFromNBT(NBTTagCompound nbtPlayerDataCompound) {
		InventoryBioMorphoPlayer newCustomInventory = new InventoryBioMorphoPlayer(nbtPlayerDataCompound.getString(NBTData.INV_MAN_PLAYER_NAME));
		newCustomInventory.readFromNBT(nbtPlayerDataCompound.getCompoundTag(NBTData.INV_MAN_PLAYER_INVENTORY));
		this.putKeyAndValueIntoCustomInventoryMap(nbtPlayerDataCompound.getString(NBTData.INV_MAN_PLAYER_NAME), newCustomInventory);
	}
	
}
