package bioMorpho.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import bioMorpho.data.NBTData;
import bioMorpho.lifeform.Lifeform;
import bioMorpho.network.PacketTypeHandler;
import bioMorpho.network.packet.PacketLFSMEntry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class LifeformStorageManager {
	
	private static LifeformStorageManagerServer serverInstance = new LifeformStorageManagerServer();
	private static LifeformStorageManagerClient clientInstance = new LifeformStorageManagerClient();
	
	public static LifeformStorageManagerServer getServerInstance() {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) return serverInstance;
		return null;
	}
	public static LifeformStorageManagerClient getClientInstance() {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) return clientInstance;
		return null;
	}
	public static LifeformStorageManager getSidedInstance() {
		switch(FMLCommonHandler.instance().getEffectiveSide()) {
		case SERVER: return serverInstance;
		case CLIENT: return clientInstance;
		default: return null;
		}
	}
	
	public static class LifeformStorageManagerServer extends LifeformStorageManager {
		public void sendUpdate(int id) {
			PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.convertToMinecraftPacketForm(new PacketLFSMEntry(id)));
		}
		public void putKeyAndValueIntoMap(Integer key, ArrayList<Lifeform> value) {
			super.putKeyAndValueIntoMap(key, value);
			this.sendUpdate(key);
		}
		@Override
		public void registerKeyAndValueWithUpdate(int key, ArrayList<Lifeform> value) {
			this.putKeyAndValueIntoMap(key, value);
		}
	}
	
	public static class LifeformStorageManagerClient extends LifeformStorageManager {
		public void sendUpdate(int id) {
			PacketDispatcher.sendPacketToServer(PacketTypeHandler.convertToMinecraftPacketForm(new PacketLFSMEntry(id)));
		}
		@Override
		public void registerKeyAndValueWithUpdate(int key, ArrayList<Lifeform> value) {
			this.putKeyAndValueIntoMap(key, value);
			this.sendUpdate(key);
		}
	}
	
	public final Map<Integer, ArrayList<Lifeform>> idToLifeformsMap = new HashMap<Integer, ArrayList<Lifeform>>();
	
	public Integer getNewId() {
		for(int i = 0; true; i++) {
			Integer id = Integer.valueOf(i);
			if(!this.idToLifeformsMap.containsKey(id)) return id;
		}
	}
	
	public ArrayList<Lifeform> getLifeforms(int id) {
		if(this.idToLifeformsMap.containsKey(Integer.valueOf(id))) return this.idToLifeformsMap.get(Integer.valueOf(id));
		return null;
	}
	
	public void putKeyAndValueIntoMap(Integer key, ArrayList<Lifeform> value) {
		this.idToLifeformsMap.put(key, value);
	}
	
	public abstract void sendUpdate(int id);
	public abstract void registerKeyAndValueWithUpdate(int key, ArrayList<Lifeform> value);
	
	public NBTTagCompound writeAllDataToNBT(NBTTagCompound nbt) {
		NBTTagList nbtEntryList = new NBTTagList();
		for(int i = 0; i < this.idToLifeformsMap.size(); i++) {
			nbtEntryList.appendTag(this.writeEntryDataToNBT(new NBTTagCompound(), (Integer)this.idToLifeformsMap.keySet().toArray()[i]));
		}
		nbt.setTag(NBTData.LFSM_MAP, nbtEntryList);
		return nbt;
	}
	public NBTTagCompound writeEntryDataToNBT(NBTTagCompound nbt, Integer id) {
		nbt.setInteger(NBTData.LFSM_MAP_ID, id);
		ArrayList<Lifeform> lifeforms = this.idToLifeformsMap.get(id);
		NBTTagList nbtLifeformList = new NBTTagList();
		for(Lifeform curLifeform : lifeforms) nbtLifeformList.appendTag(curLifeform.writeToNBT(new NBTTagCompound()));
		nbt.setTag(NBTData.LFSM_MAP_LIFEFORM_ARRAY, nbtLifeformList);
		return nbt;
	}
	
	public void readAllDataFromNBT(NBTTagCompound nbt) {
		NBTTagList nbtEntryList = nbt.getTagList(NBTData.LFSM_MAP);
		for(int i = 0; i < nbtEntryList.tagCount(); i++) {
			this.readEntryDataFromNBT((NBTTagCompound)nbtEntryList.tagAt(i));
		}
	}
	public void readEntryDataFromNBT(NBTTagCompound nbt) {
		Integer id = nbt.getInteger(NBTData.LFSM_MAP_ID);
		ArrayList<Lifeform> lifeforms = new ArrayList<Lifeform>();
		NBTTagList nbtLifeformList = nbt.getTagList(NBTData.LFSM_MAP_LIFEFORM_ARRAY);
		for(int i = 0; i < nbtLifeformList.tagCount(); i++) lifeforms.add(Lifeform.loadFromNBT((NBTTagCompound)nbtLifeformList.tagAt(i)));
		this.putKeyAndValueIntoMap(id, lifeforms);
	}
	
}
