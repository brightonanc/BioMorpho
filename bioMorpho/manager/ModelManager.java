package bioMorpho.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import bioMorpho.data.NBTData;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.network.PacketTypeHandler;
import bioMorpho.network.packet.PacketModelManData;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Brighton Ancelin
 *
 */
public class ModelManager {
	
	private static ModelManagerServer serverInstance = new ModelManagerServer();
	private static ModelManagerClient clientInstance = new ModelManagerClient();
	
	public static ModelManagerServer getServerInstance() {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) return serverInstance;
		return null;
	}
	
	public static ModelManagerClient getClientInstance() {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) return clientInstance;
		return null;
	}
	
	public static ModelManager getSidedInstance() {
		switch(FMLCommonHandler.instance().getEffectiveSide()) {
		case SERVER: return serverInstance;
		case CLIENT: return clientInstance;
		default: return null;
		}
	}
	
	public static class ModelManagerServer extends ModelManager {
		
		protected void sendModelArrayUpdateToAllPlayers(String key) {
			PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.convertToMinecraftPacketForm(new PacketModelManData(key)));
		}
		
		@Override
		public void putKeyAndValueIntoPlayerModelMap(String key, ArrayList<ModelCustom> value) {
			super.putKeyAndValueIntoPlayerModelMap(key, value);
			this.sendModelArrayUpdateToAllPlayers(key);
		}
		
	}
	
	public static class ModelManagerClient extends ModelManager {
		
		public void registerPlayerAndModelArray(String key, ArrayList<ModelCustom> modelArray) {
			this.putKeyAndValueIntoPlayerModelMap(key, modelArray);
			PacketDispatcher.sendPacketToServer(PacketTypeHandler.convertToMinecraftPacketForm(new PacketModelManData(key)));
		}
		
		@Override
		public ArrayList<ModelCustom> getModelArrayFromPlayerModelMap(String key) {
			ArrayList<ModelCustom> modelArray = super.getModelArrayFromPlayerModelMap(key);
			if(modelArray != null) return modelArray;
			else return new ArrayList<ModelCustom>();
		}
		
	}
	
	public Map<String, ArrayList<ModelCustom>> playerModelMap = new HashMap<String, ArrayList<ModelCustom>>();
	
	/*
	 * The below method is only supposed to be used by the PlayerTracker on new player logins
	 */
	public void initNewPlayerToMap(String playerUsername) {
		if(!this.playerModelMap.containsKey(playerUsername)) {
			ArrayList<ModelCustom> newModelCustomArrayList = new ArrayList<ModelCustom>();
			this.putKeyAndValueIntoPlayerModelMap(playerUsername, newModelCustomArrayList);
		}
	}
	
	public ModelCustom getCurrentModelForPlayer(String playerUsername) {
		ArrayList<ModelCustom> modelCustomArray = this.getModelArrayFromPlayerModelMap(playerUsername);
		int currentIndex = 0; //TODO find current selected model somehow
		if(!(currentIndex < modelCustomArray.size())) {
			this.addDefaultModelToPlayerModelArray(playerUsername, currentIndex);
			modelCustomArray = this.getModelArrayFromPlayerModelMap(playerUsername);
		}
		ModelCustom currentModel = modelCustomArray.get(currentIndex);
		return currentModel;
	}
	
	public void addDefaultModelToPlayerModelArray(String playerUsername, int index) {
		ModelCustom defaultModel = new ModelCustom(false);
		ArrayList<ModelCustom> modelCustomArray = this.getModelArrayFromPlayerModelMap(playerUsername);
		modelCustomArray.add(index, defaultModel);
		this.putKeyAndValueIntoPlayerModelMap(playerUsername, modelCustomArray);
	}
	
	public ArrayList<ModelCustom> getModelArrayFromPlayerModelMap(String key) {
		return this.playerModelMap.get(key);
	}
	
	protected void putKeyAndValueIntoPlayerModelMap(String key, ArrayList<ModelCustom> value) {
		this.playerModelMap.put(key, value);
	}
	
	public NBTTagCompound writeAllModelManagerDataToNBT(NBTTagCompound nbtModelManagerCompound) {
		nbtModelManagerCompound.setTag(NBTData.NBT_PLAYER_MODEL_MAP_KEY, this.writePlayerModelMapDataToNBT(new NBTTagList()));
		// TODO add more ModelManager data to nbtModelManagerCompound
		return nbtModelManagerCompound;
	}
	
	public NBTTagList writePlayerModelMapDataToNBT(NBTTagList nbtPlayerModelMapList) {
		// NOTE: If there is an issue with using Map.size(), I might want to change it to Map.keySet().toArray().length
		for(int i = 0; i < this.playerModelMap.size(); i++) {
			String playerUsername = (String)this.playerModelMap.keySet().toArray()[i];
			nbtPlayerModelMapList.appendTag(this.writePlayerDataToNBT(new NBTTagCompound(), playerUsername));
		}
		return nbtPlayerModelMapList;
	}
	
	public NBTTagCompound writePlayerDataToNBT(NBTTagCompound nbtPlayerSpecificCompound, String playerUsername) {
		nbtPlayerSpecificCompound.setString(NBTData.NBT_PLAYER_USERNAME_KEY, playerUsername);
		nbtPlayerSpecificCompound.setTag(NBTData.NBT_PLAYER_MODEL_ARRAY_KEY, this.writePlayerModelArrayDataToNBT(new NBTTagList(), playerUsername));
		// Add more info here if needed
		return nbtPlayerSpecificCompound;
	}
	
	public NBTTagList writePlayerModelArrayDataToNBT(NBTTagList nbtPlayerSpecificModelArrayList, String playerUsername) {
		//The below method will give me an error if I change the Map's values to arrays instead of ArrayLists - that way I'll then fix this method
		//If I simply typecasted, it would have been inaccurate if I changed the Map value and I wouldn't see the error
		ArrayList<ModelCustom> modelCustomArray = this.getModelArrayFromPlayerModelMap(playerUsername); 
		for(int i = 0; i < modelCustomArray.size(); i++) {
			ModelCustom currentModelCustom = modelCustomArray.get(i);
			nbtPlayerSpecificModelArrayList.appendTag(currentModelCustom.writeModelCustomDataToNBT(new NBTTagCompound()));
		}
		return nbtPlayerSpecificModelArrayList;
	}
	
	public void readAllModelManagerDataFromNBT(NBTTagCompound nbtModelManagerCompound) {
		NBTTagList nbtPlayerModelMapList = nbtModelManagerCompound.getTagList(NBTData.NBT_PLAYER_MODEL_MAP_KEY);
		this.readPlayerModelMapDataFromNBT(nbtPlayerModelMapList);
		//TODO add any more code to NBT for ModelManager - REMEMBER to add corresponding write values!!!
	}
	
	public void readPlayerModelMapDataFromNBT(NBTTagList nbtPlayerModelMapList) {
		this.playerModelMap.clear(); // Clears all data because we are about to re-enter the data we store in NBT
		for(int i = 0; i < nbtPlayerModelMapList.tagCount(); i++) {
			NBTTagCompound nbtPlayerSpecificCompound = (NBTTagCompound)nbtPlayerModelMapList.tagAt(i);
			this.readPlayerDataFromNBT(nbtPlayerSpecificCompound);
		}
	}
	
	public void readPlayerDataFromNBT(NBTTagCompound nbtPlayerSpecificCompound) {
		String playerUsername = nbtPlayerSpecificCompound.getString(NBTData.NBT_PLAYER_USERNAME_KEY);
		NBTTagList nbtPlayerSpecificModelArrayList = (NBTTagList)nbtPlayerSpecificCompound.getTag(NBTData.NBT_PLAYER_MODEL_ARRAY_KEY);
		this.readPlayerModelArrayDataFromNBT(nbtPlayerSpecificModelArrayList, playerUsername);
	}
	
	public void readPlayerModelArrayDataFromNBT(NBTTagList nbtPlayerSpecificModelArrayList, String playerUsername) {
		ArrayList<ModelCustom> modelCustomArray = new ArrayList<ModelCustom>();
		for(int i = 0; i < nbtPlayerSpecificModelArrayList.tagCount(); i++) {
			NBTTagCompound nbtModelCustomCompound = (NBTTagCompound)nbtPlayerSpecificModelArrayList.tagAt(i);
			ModelCustom currentModelCustom = new ModelCustom(false);
			currentModelCustom.readModelCustomDataFromNBT(nbtModelCustomCompound);
			modelCustomArray.add(currentModelCustom);
		}
		this.putKeyAndValueIntoPlayerModelMap(playerUsername, modelCustomArray);
	}
	
	public ArrayList<ModelCustom> copy(ArrayList<ModelCustom> array) {
		ArrayList<ModelCustom> newArray = new ArrayList<ModelCustom>();
		for(int i = 0; i < array.size(); i++) {
			NBTTagCompound nbtModelCustomCompound = array.get(i).writeModelCustomDataToNBT(new NBTTagCompound());
			ModelCustom currentModel = new ModelCustom(false);
			currentModel.readModelCustomDataFromNBT(nbtModelCustomCompound);
			newArray.add(i, currentModel);
		}
		return newArray;
	}
	
	public ModelCustom copy(ModelCustom model) {
		NBTTagCompound nbtModelCustomCompound = model.writeModelCustomDataToNBT(new NBTTagCompound());
		ModelCustom newModel = new ModelCustom(!model.isDefaultBipedModel);
		newModel.readModelCustomDataFromNBT(nbtModelCustomCompound);
		return newModel;
	}
	
}
