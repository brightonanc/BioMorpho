package bioMorpho.handler;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import bioMorpho.data.ModData;
import bioMorpho.lifeform.Lifeform;
import bioMorpho.lifeform.attribute.Attribute;
import bioMorpho.manager.LifeformIndexManager;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * @author Brighton Ancelin
 *
 */
public class LifeformAttributeHandler implements ITickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		EntityPlayer player = (EntityPlayer)tickData[0];
		World world = player.worldObj;
		
		Lifeform lifeform = LifeformIndexManager.getClientInstance().getClientPlayerCurrentLifeform();
		if(lifeform != null) {
			for(Attribute atb : lifeform.getAttributes()) {
				atb.tickStart(player, world);
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		EntityPlayer player = (EntityPlayer)tickData[0];
		World world = player.worldObj;
		
		Lifeform lifeform = LifeformIndexManager.getClientInstance().getClientPlayerCurrentLifeform();
		if(lifeform != null) {
			for(Attribute atb : lifeform.getAttributes()) {
				atb.tickEnd(player, world);
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return ModData.MOD_ID + ": " + this.getClass().getSimpleName();
	}
	
}
