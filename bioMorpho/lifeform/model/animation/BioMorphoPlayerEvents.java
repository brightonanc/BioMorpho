package bioMorpho.lifeform.model.animation;

import java.util.Comparator;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.Entity;

/**
 * @author Brighton Ancelin
 *
 */
public enum BioMorphoPlayerEvents {
	
	ALWAYS("Always"),
	IN_WATER("In Water"),
	;
	
	private final String guiName;
	
	private BioMorphoPlayerEvents(String guiName) {
		this.guiName = guiName;
	}
	
	public static Comparator getGuiComparator() {
		class GuiComparator implements Comparator {
			public int compare(Object event1, Object event2) {
				return ((BioMorphoPlayerEvents)event1).getGuiName().compareTo(((BioMorphoPlayerEvents)event2).getGuiName());
			}
		}
		return new GuiComparator();
	}
	public String getGuiName() { return this.guiName; }
	
	public boolean isValid(Entity entity) {
		switch(this) {
		case ALWAYS:
			return true;
		case IN_WATER:
			if(entity.isInWater()) return true;
			else return false;
		default: return false;
		}
	}
	
}
