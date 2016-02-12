package bioMorpho.gui.lifeformSelection;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;

/**
 * @author Brighton Ancelin
 *
 */
public class LDPDrawnText {
	
	public final int unscaledLeft;
	public final int unscaledTop;
	public final FontRenderer fontRenderer;
	
	public LDPDrawnText(int unscaledLeft, int unscaledTop, FontRenderer fontRenderer) {
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		this.fontRenderer = fontRenderer;
	}
	
	public void draw() {
		// Smaller names for less typing
		int uL = this.unscaledLeft;
		int uT = this.unscaledTop;
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		this.fontRenderer.drawString("Y Offset", uL+326+2, uT+34+1, -1, true);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
