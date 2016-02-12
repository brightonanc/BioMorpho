package bioMorpho.handler;

import java.util.EnumSet;

import bioMorpho.data.ModData;
import bioMorpho.lifeform.Lifeform;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.manager.LifeformIndexManager;
import bioMorpho.manager.ModelManager_;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * @author Brighton Ancelin
 *
 */
public class ModelUpdateHandler implements ITickHandler {
	
	protected Lifeform prevLifeform;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(FMLClientHandler.instance().getClient().thePlayer == null) return;
		Lifeform lifeform = LifeformIndexManager.getClientInstance().getClientPlayerCurrentLifeform();
		if(lifeform != this.prevLifeform) {
			if(lifeform != null) {
				ModelManager_.getClientInstance().registerPlayerModel(FMLClientHandler.instance().getClient().thePlayer.username, lifeform.getModel());
			} else {
				ModelManager_.getClientInstance().registerPlayerModel(FMLClientHandler.instance().getClient().thePlayer.username, new ModelCustom(false));
			}
		}
		this.prevLifeform = lifeform;
	}
	
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}
	
	@Override
	public String getLabel() {
		return ModData.MOD_ID + ":" + this.getClass().getSimpleName();
	}
	
}
