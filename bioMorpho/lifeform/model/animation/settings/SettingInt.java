package bioMorpho.lifeform.model.animation.settings;

import bioMorpho.data.NBTData;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Brighton Ancelin
 *
 */
public class SettingInt extends AnimSetting {
	
	private int lowerBound = Integer.MIN_VALUE;
	private int upperBound = Integer.MAX_VALUE;
	private int value = 0;
	
	public SettingInt(String guiName) { super(guiName); }
	public SettingInt(String guiName, int lowerBound, int upperBound) {
		this(guiName);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger(NBTData.SETTING_INT_VALUE, this.value);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.value = nbt.getInteger(NBTData.SETTING_INT_VALUE);
	}
	
	public int getLowerBound() { return this.lowerBound; }
	public int getUpperBound() { return this.upperBound; }
	public void setValue(int val) { this.value = val; }
	public int getValue() { return this.value; }
	
}
