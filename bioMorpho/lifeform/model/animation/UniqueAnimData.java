package bioMorpho.lifeform.model.animation;

import net.minecraft.nbt.NBTTagCompound;
import bioMorpho.data.NBTData;
import bioMorpho.lifeform.model.animation.settings.AnimSettings;

/**
 * @author Brighton Ancelin
 *
 */
public class UniqueAnimData {
	
	private AnimSettings settings;
	private RecordedAnimData recordAnimData = new RecordedAnimData();
	private RecordedEventData recordEventData = new RecordedEventData();
	
	public void setSettings(AnimSettings animSettings) { this.settings = animSettings; }
	
	public AnimSettings getSettings() { return this.settings; }
	public RecordedAnimData getAnimRecordData() { return this.recordAnimData; }
	public RecordedEventData getEventRecordData() { return this.recordEventData; }
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		// Currently, we only need to store data from this.settings
		nbt.setCompoundTag(NBTData.NBT_COMPONENT_ANIM_SETTINGS_KEY, this.settings.writeToNBT(new NBTTagCompound()));
		return nbt;
	}
	public static UniqueAnimData readAndCreateFromNBT(NBTTagCompound nbt) {
		UniqueAnimData ret = new UniqueAnimData();
		ret.settings = AnimSettings.createFromNBT(nbt.getCompoundTag(NBTData.NBT_COMPONENT_ANIM_SETTINGS_KEY));
		return ret;
	}
	
}
