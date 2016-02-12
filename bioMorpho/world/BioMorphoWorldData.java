package bioMorpho.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;
import bioMorpho.data.ModData;
import bioMorpho.data.NBTData;

/**
 * @author Brighton Ancelin
 *
 */
public class BioMorphoWorldData extends WorldSavedData {
	
	public NBTTagCompound nbt = new NBTTagCompound();
	
	public BioMorphoWorldData() {
		super(ModData.MOD_ID+":"+"BioMorphoWorldData");
	}
	
	public BioMorphoWorldData(String label) {
		super(label);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtInput) {
		this.nbt = (NBTTagCompound)nbtInput.getTag(NBTData.WORLD_DATA_TAG);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtInput) {
		nbtInput.setTag(NBTData.WORLD_DATA_TAG, this.nbt);
	}
	
}
