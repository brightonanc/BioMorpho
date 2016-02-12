package bioMorpho.lifeform.model.animation.settings;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class AnimSetting {
	
	private final String guiName;
	
	protected AnimSetting(String guiName) {
		this.guiName = guiName;
	}
	
	public String getGuiName() { return this.guiName; }
	
	public abstract NBTTagCompound writeToNBT(NBTTagCompound nbt);
	public abstract void readFromNBT(NBTTagCompound nbt);
	
}
