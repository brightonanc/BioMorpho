package bioMorpho.lifeform.model.animation.settings;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import bioMorpho.data.NBTData;
import bioMorpho.lifeform.model.animation.ModelAnimations;
import bioMorpho.util.IdGenerator;

/**
 * @author Brighton Ancelin
 *
 */
public class AnimSettings {
	
	private IdGenerator ids = new IdGenerator();
	
	private ArrayList<AnimSetting> settings = new ArrayList<AnimSetting>();
	protected EnumAnimSettings type = EnumAnimSettings.UNKNOWN;
	
	protected AnimSettings(EnumAnimSettings type) {
		this.type = type;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger(NBTData.ANIM_SETTINGS_ENUM, this.type.ordinal());
		NBTTagList nbtSettingsList = new NBTTagList();
		for(AnimSetting curSetting : this.settings) {
			nbtSettingsList.appendTag(curSetting.writeToNBT(new NBTTagCompound()));
		}
		nbt.setTag(NBTData.ANIM_SETTINGS_LIST, nbtSettingsList);
		return nbt;
	}
	
	public static AnimSettings createFromNBT(NBTTagCompound nbt) {
		AnimSettings ret = EnumAnimSettings.values()[nbt.getInteger(NBTData.ANIM_SETTINGS_ENUM)].createNewInstance();
		NBTTagList nbtSettingsList = nbt.getTagList(NBTData.ANIM_SETTINGS_LIST);
		for(int i = 0; i < nbtSettingsList.tagCount(); i++) {
			ret.getSettings().get(i).readFromNBT((NBTTagCompound)nbtSettingsList.tagAt(i));
		}
		return ret;
	}
	
	public int registerSetting(AnimSetting newSetting) {
		int id = ids.getNextId();
		this.settings.add(id, newSetting);
		return id;
	}
	
	public AnimSetting getSetting(int id) { return this.settings.get(id); }
	public ArrayList<AnimSetting> getSettings() { return this.settings; }
	
	public static AnimSettings createNewAnimationSettings(ModelAnimations anim) {
		switch(anim) {
		case NONE: return new SettingsBlank();
		case HEAD: return new SettingsBlank();
		case ROTATION: return new SettingsRotation();
		case OSCILLATION: return new SettingsOscillation();
		case LEG_1_LEFT: return new SettingsLeg1();
		case LEG_1_RIGHT: return new SettingsLeg1();
		case ARM_1_LEFT: return new SettingsArm1();
		case ARM_1_RIGHT: return new SettingsArm1();
		case TAIL_1: return new SettingsTail1();
		case TAIL_2: return new SettingsTail2();
		default: return new SettingsBlank();
		}
	}
	
}
