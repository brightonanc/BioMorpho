package bioMorpho.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import bioMorpho.data.InventoryData;
import bioMorpho.data.NBTData;
import bioMorpho.helper.LifeformHelper;
import bioMorpho.inventory.InventoryBioMorphoPlayer;
import bioMorpho.item.BioMorphoItems;
import bioMorpho.lifeform.Lifeform;
import bioMorpho.network.PacketTypeHandler;
import bioMorpho.network.packet.PacketLifeformIndexManData;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class LifeformIndexManager {
	
	private static LifeformIndexManagerServer serverInstance = new LifeformIndexManagerServer();
	private static LifeformIndexManagerClient clientInstance = new LifeformIndexManagerClient();
	
	public static LifeformIndexManagerServer getServerInstance() {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) return serverInstance;
		return null;
	}
	public static LifeformIndexManagerClient getClientInstance() {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) return clientInstance;
		return null;
	}
	public static LifeformIndexManager getSidedInstance() {
		switch(FMLCommonHandler.instance().getEffectiveSide()) {
		case SERVER: return serverInstance;
		case CLIENT: return clientInstance;
		default: return null;
		}
	}
	
	public static class LifeformIndexManagerServer extends LifeformIndexManager {
		
		private final Map<String, Integer> lifeformIndexMap = new HashMap<String, Integer>();
		
		public Lifeform getCurrentLifeformForPlayer(String username) {
			if(this.lifeformIndexMap.containsKey(username)) {
				int index = this.lifeformIndexMap.get(username);
				ItemStack gemOfGenesis = InventoryManager.getServerInstance().getCustomInventoryForPlayer(username).getStackInSlot(InventoryData.BIO_MORPHO_PLAYER_GEM_INDEX);
				ArrayList<Lifeform> lifeforms = LifeformHelper.getLifeforms(gemOfGenesis);
				if(lifeforms != null && index < lifeforms.size() && index >= 0) {
					return lifeforms.get(index);
				}
			}
			return null;
		}
		
		public Integer getLifeformIndexOfPlayer(String username) {return this.lifeformIndexMap.get(username);}
		
		public void putKeyAndValueIntoMap(String key, int value) {
			this.lifeformIndexMap.put(key, value);
		}
		
		public NBTTagCompound writeDataToNBT(NBTTagCompound nbt) {
			NBTTagList nbtList = new NBTTagList();
			for(int i = 0; i < this.lifeformIndexMap.size(); i++) {
				NBTTagCompound nbtCompound = new NBTTagCompound();
				nbtCompound.setString(NBTData.LIFEFORM_USERNAME, (String)this.lifeformIndexMap.keySet().toArray()[i]);
				nbtCompound.setInteger(NBTData.LIFEFORM_INDEX, (Integer)this.lifeformIndexMap.values().toArray()[i]);
				nbtList.appendTag(nbtCompound);
			}
			nbt.setTag(NBTData.LIFEFORMS_KEY, nbtList);
			return nbt;
		}
		public void readDataFromNBT(NBTTagCompound nbt) {
			NBTTagList nbtList = nbt.getTagList(NBTData.LIFEFORMS_KEY);
			for(int i = 0; i < nbtList.tagCount(); i++) {
				NBTTagCompound nbtCompound = (NBTTagCompound)nbtList.tagAt(i);
				this.putKeyAndValueIntoMap(nbtCompound.getString(NBTData.LIFEFORM_USERNAME), nbtCompound.getInteger(NBTData.LIFEFORM_INDEX));
			}
		}
		
		public void initPlayerIfNecessary(String username) {
			if(this.getLifeformIndexOfPlayer(username) == null) this.putKeyAndValueIntoMap(username, 0);
		}
		
	}
	
	public static class LifeformIndexManagerClient extends LifeformIndexManager {
		
		private int index;
		
		public Lifeform getClientPlayerCurrentLifeform() {
			ItemStack gemOfGenesis = InventoryManager.getClientInstance().getClientGemOfGenesis();
			if(gemOfGenesis != null && gemOfGenesis.getItem() == BioMorphoItems.gemOfGenesis) {
				ArrayList<Lifeform> lifeforms = LifeformHelper.getLifeforms(gemOfGenesis);
				if(lifeforms != null) {
					if(this.index < lifeforms.size() && this.index >= 0) {
						return lifeforms.get(this.index);
					}
				}
			}
			return null;
		}
		
		public void setClientPlayerIndex(int index) {
			this.index = index;
			PacketDispatcher.sendPacketToServer(PacketTypeHandler.convertToMinecraftPacketForm(new PacketLifeformIndexManData()));
		}
		
		public String getPlayerUsername() {return FMLClientHandler.instance().getClient().thePlayer.username;}
		
		public int getIndex() {return this.index;}
		
	}
	
}
