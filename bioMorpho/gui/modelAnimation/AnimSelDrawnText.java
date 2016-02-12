package bioMorpho.gui.modelAnimation;

import bioMorpho.lifeform.model.animation.BioMorphoPlayerEvents;
import bioMorpho.lifeform.model.animation.ModelAnimations;
import net.minecraft.client.gui.FontRenderer;

/**
 * @author Brighton Ancelin
 *
 */
public class AnimSelDrawnText {
	
	public int unscaledLeft;
	public int unscaledTop;
	
	public AnimSelDrawnText(int unscaledLeft, int unscaledTop) {
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
	}
	
	public void draw(FontRenderer fontRenderer, BioMorphoPlayerEvents event, ModelAnimations animation) {
		if(event != null) fontRenderer.drawString(event.getGuiName(), this.unscaledLeft+384+5+2, this.unscaledTop+25+1, -1, true);
		if(animation != null) fontRenderer.drawString(animation.getGuiName(), this.unscaledLeft+384+5+2, this.unscaledTop+115+1, -1, true);
	}
	
}
