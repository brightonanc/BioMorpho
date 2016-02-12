package bioMorpho.manager;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import bioMorpho.data.NBTData;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.network.PacketTypeHandler;
import bioMorpho.network.packet.PacketModelManData_;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class ModelManager_ {
	
	private static ModelManagerServer_ serverInstance = new ModelManagerServer_();
	private static ModelManagerClient_ clientInstance = new ModelManagerClient_();
	
	public static ModelManagerServer_ getServerInstance() {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) return serverInstance;
		return null;
	}
	
	public static ModelManagerClient_ getClientInstance() {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) return clientInstance;
		return null;
	}
	
	public static ModelManager_ getSidedInstance() {
		switch(FMLCommonHandler.instance().getEffectiveSide()) {
		case SERVER: return serverInstance;
		case CLIENT: return clientInstance;
		default: return null;
		}
	}
	
	public static class ModelManagerServer_ extends ModelManager_ {
		public void sendUpdateToAllPlayers(String username) {
			PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.convertToMinecraftPacketForm(new PacketModelManData_(username)));
		}
		@Override
		public void putKeyAndValueIntoMap(String key, ModelCustom value) {
			super.putKeyAndValueIntoMap(key, value);
			this.sendUpdateToAllPlayers(key);
		}
	}
	
	public static class ModelManagerClient_ extends ModelManager_ {
		/**
		 * Don't override putKeyAndValueIntoMap because it would cause an endless cycle of packets from clients to server
		 */
		public void registerPlayerModel(String username, ModelCustom model) {
			this.putKeyAndValueIntoMap(username, model);
			PacketDispatcher.sendPacketToServer(PacketTypeHandler.convertToMinecraftPacketForm(new PacketModelManData_(username)));
		}
	}
	
	public final Map<String, ModelCustom> modelMap = new HashMap<String, ModelCustom>();
	
	public void putKeyAndValueIntoMap(String key, ModelCustom value) {
		this.modelMap.put(key, value);
	}
	
	public ModelCustom getModelForPlayer(String username) {
		return this.modelMap.get(username);
	}
	
	public NBTTagCompound writeModelDataToNBT(NBTTagCompound nbtModel, String username) {
		nbtModel.setCompoundTag(NBTData.MODEL_MAN_PLAYER_MODEL, this.modelMap.get(username).writeModelCustomDataToNBT(new NBTTagCompound()));
		return nbtModel;
	}
	public void readModelDataFromNBT(NBTTagCompound nbtModel, String username) {
		this.putKeyAndValueIntoMap(username, ModelCustom.loadFromNBT(nbtModel.getCompoundTag(NBTData.MODEL_MAN_PLAYER_MODEL)));
	}
	
}
