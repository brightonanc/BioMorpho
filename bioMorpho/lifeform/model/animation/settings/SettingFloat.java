package bioMorpho.lifeform.model.animation.settings;

import bioMorpho.data.NBTData;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Brighton Ancelin
 *
 */
public class SettingFloat extends AnimSetting {
	
	private float lowerBound = Integer.MIN_VALUE;
	private float upperBound = Integer.MAX_VALUE;
	private float value = 0;
	
	public SettingFloat(String guiName) { super(guiName); }
	public SettingFloat(String guiName, float lowerBound, float upperBound) {
		this(guiName);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat(NBTData.SETTING_FLOAT_VALUE, this.value);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.value = nbt.getFloat(NBTData.SETTING_FLOAT_VALUE);
	}
	
	public float getLowerBound() { return this.lowerBound; }
	public float getUpperBound() { return this.upperBound; }
	public void setValue(float val) { this.value = val; }
	public float getValue() { return this.value; }
	
}
