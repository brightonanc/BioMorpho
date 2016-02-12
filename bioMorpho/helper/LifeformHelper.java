package bioMorpho.helper;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import bioMorpho.data.NBTData;
import bioMorpho.lifeform.Lifeform;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.manager.LifeformStorageManager;

/**
 * @author Brighton Ancelin
 *
 */
public class LifeformHelper {
	
	/**
	 * Any alterations to the return value of this method will NOT be sent across the network by default. 
	 * Any network issues will have to be made manually if you choose to alter the return of this method.
	 */
	public static ArrayList<Lifeform> getLifeforms(ItemStack gemOfGenesis) {
		if(gemOfGenesis != null && !gemOfGenesis.hasTagCompound()) attemptInitialization(gemOfGenesis);
		if(gemOfGenesis != null && gemOfGenesis.hasTagCompound()) {
			int id = gemOfGenesis.getTagCompound().getInteger(NBTData.GEM_OF_GENESIS_NBT_ID);
			return LifeformStorageManager.getSidedInstance().getLifeforms(id);
		}
		return null;
	}
	
	/**
	 * WILL SHIFT ALL LIFEFORMS BEYOND THE SPECIFIED INDEX!!!
	 */
	public static void removeLifeformByIndex(ItemStack gemOfGenesis, int index) {
		if(gemOfGenesis != null && !gemOfGenesis.hasTagCompound()) attemptInitialization(gemOfGenesis);
		if(gemOfGenesis != null && gemOfGenesis.hasTagCompound()) {
			int id = gemOfGenesis.getTagCompound().getInteger(NBTData.GEM_OF_GENESIS_NBT_ID);
			LifeformStorageManager lfsm = LifeformStorageManager.getSidedInstance();
			lfsm.getLifeforms(id).remove(index);
			lfsm.sendUpdate(id);
		}
	}
	
	public static void replaceLifeformAtIndex(ItemStack gemOfGenesis, int replacingIndex, Lifeform lifeform) {
		if(gemOfGenesis != null && !gemOfGenesis.hasTagCompound()) attemptInitialization(gemOfGenesis);
		if(gemOfGenesis != null && gemOfGenesis.hasTagCompound()) {
			int id = gemOfGenesis.getTagCompound().getInteger(NBTData.GEM_OF_GENESIS_NBT_ID);
			LifeformStorageManager lfsm = LifeformStorageManager.getSidedInstance();
			ArrayList<Lifeform> lifeforms = lfsm.getLifeforms(id);
			lifeforms.remove(replacingIndex);
			lifeforms.add(replacingIndex, lifeform);
			lfsm.sendUpdate(id);
		}
	}
	
	public static int getLifeformCount(ItemStack gemOfGenesis) {
		ArrayList<Lifeform> lifeforms = getLifeforms(gemOfGenesis);
		return lifeforms == null ? -1 : lifeforms.size();
	}
	
	public static void setLifeforms(ItemStack gemOfGenesis, ArrayList<Lifeform> lifeforms) {
		if(gemOfGenesis != null && !gemOfGenesis.hasTagCompound()) attemptInitialization(gemOfGenesis);
		if(gemOfGenesis != null && gemOfGenesis.hasTagCompound()) {
			int id = gemOfGenesis.getTagCompound().getInteger(NBTData.GEM_OF_GENESIS_NBT_ID);
			LifeformStorageManager lfsm = LifeformStorageManager.getSidedInstance();
			lfsm.registerKeyAndValueWithUpdate(id, lifeforms);
		}
	}
	
	public static void attemptInitialization(ItemStack gemOfGenesis) {
		if(gemOfGenesis != null && !gemOfGenesis.hasTagCompound()) {
			LifeformStorageManager lfsm = LifeformStorageManager.getSidedInstance();
			ArrayList<Lifeform> newLifeformArray = new ArrayList<Lifeform>();
			NBTTagCompound nbt = new NBTTagCompound();
			int id = lfsm.getNewId();
			nbt.setInteger(NBTData.GEM_OF_GENESIS_NBT_ID, id);
			nbt.setIntArray(NBTData.GEM_OF_GENESIS_NBT_INDEX_POINTERS, new int[] {-1, -1, -1, -1, -1, -1, -1});
			gemOfGenesis.setTagCompound(nbt);
			lfsm.putKeyAndValueIntoMap(id, newLifeformArray);
			lfsm.sendUpdate(id);
		}
	}
	
	public static int[] getIndexPointers(ItemStack gemOfGenesis) {
		if(gemOfGenesis != null && !gemOfGenesis.hasTagCompound()) attemptInitialization(gemOfGenesis);
		if(gemOfGenesis != null && gemOfGenesis.hasTagCompound()) {
			return gemOfGenesis.getTagCompound().getIntArray(NBTData.GEM_OF_GENESIS_NBT_INDEX_POINTERS);
		}
		return null;
	}
	
	public static void setIndexPointers(ItemStack gemOfGenesis, int[] indexPointers) {
		if(gemOfGenesis != null && !gemOfGenesis.hasTagCompound()) attemptInitialization(gemOfGenesis);
		if(gemOfGenesis != null && gemOfGenesis.hasTagCompound()) {
			gemOfGenesis.getTagCompound().setIntArray(NBTData.GEM_OF_GENESIS_NBT_INDEX_POINTERS, indexPointers);
		}
	}
	
}
