package bioMorpho.lifeform.attribute;

import bioMorpho.helper.AttributeHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class Attribute {
	
	protected boolean shouldRunTick = false;
	
	public abstract void executeStart(EntityPlayer player, World world);
	public abstract void executeEnd(EntityPlayer player, World world);
	public abstract boolean isActive();
	
	public boolean tickStart(EntityPlayer player, World world) {
		boolean success;
		this.startSetup();
		if(this.shouldRunTick) {
			this.executeStart(player, world);
			success = true;
		} else {
			success = false;
		}
		return success;
	}
	
	public boolean tickEnd(EntityPlayer player, World world) {
		boolean success;
		if(this.shouldRunTick) {
			this.executeEnd(player, world);
			success = true;
		} else {
			success = false;
		}
		this.endSetup();
		return success;
	}
	
	protected void startSetup() {
		this.shouldRunTick = this.isActive();
	}
	
	protected void endSetup() {}
	
}
