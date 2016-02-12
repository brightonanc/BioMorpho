package bioMorpho.handler;

import java.util.EnumSet;

import bioMorpho.data.ModData;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * @author Brighton Ancelin
 *
 */
public class RenderInfoGatherer implements ITickHandler {
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		BioMorphoCurRenderInfo.partialTickTime = (Float)tickData[0];
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		BioMorphoCurRenderInfo.partialTickTime = (Float)tickData[0];
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return ModData.MOD_ID +": " + this.getClass().getSimpleName();
	}
	
	
	
}
