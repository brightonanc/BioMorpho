package bioMorpho.gui.modelEditor;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;

/**
 * @author Brighton Ancelin
 *
 */
public class MNIDrawnText {
	
	private int unscaledLeft;
	private int unscaledTop;
	private FontRenderer fontRenderer;
	
	public MNIDrawnText(int unscaledLeft, int unscaledTop, FontRenderer fontRenderer) {
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
		this.fontRenderer.drawString("Component Data", uL+389+2, uT+3+1, -1, true);
		this.fontRenderer.drawString("Rotation", uL+451+2, uT+18+1, -1, true);
		this.fontRenderer.drawString("Point", uL+451+2, uT+27+1, -1, true);
		this.fontRenderer.drawString("X", uL+387+2, uT+21+1, -1, true);
		this.fontRenderer.drawString("Y", uL+387+2, uT+35+1, -1, true);
		this.fontRenderer.drawString("Z", uL+387+2, uT+49+1, -1, true);
		this.fontRenderer.drawString("Rotation", uL+451+2, uT+66+1, -1, true);
		this.fontRenderer.drawString("Angle", uL+451+2, uT+75+1, -1, true);
		this.fontRenderer.drawString("X", uL+387+2, uT+69+1, -1, true);
		this.fontRenderer.drawString("Y", uL+387+2, uT+83+1, -1, true);
		this.fontRenderer.drawString("Z", uL+387+2, uT+97+1, -1, true);
		this.fontRenderer.drawString("Box Data", uL+389+2, uT+115+1, -1, true);
		this.fontRenderer.drawString("Rotation", uL+451+2, uT+130+1, -1, true);
		this.fontRenderer.drawString("Point", uL+451+2, uT+139+1, -1, true);
		this.fontRenderer.drawString("X", uL+387+2, uT+133+1, -1, true);
		this.fontRenderer.drawString("Y", uL+387+2, uT+147+1, -1, true);
		this.fontRenderer.drawString("Z", uL+387+2, uT+161+1, -1, true);
		this.fontRenderer.drawString("Rotation", uL+451+2, uT+178+1, -1, true);
		this.fontRenderer.drawString("Angle", uL+451+2, uT+187+1, -1, true);
		this.fontRenderer.drawString("X", uL+387+2, uT+181+1, -1, true);
		this.fontRenderer.drawString("Y", uL+387+2, uT+195+1, -1, true);
		this.fontRenderer.drawString("Z", uL+387+2, uT+209+1, -1, true);
		this.fontRenderer.drawString("Position", uL+451+2, uT+226+1, -1, true);
		this.fontRenderer.drawString("X", uL+387+2, uT+229+1, -1, true);
		this.fontRenderer.drawString("Y", uL+387+2, uT+243+1, -1, true);
		this.fontRenderer.drawString("Z", uL+387+2, uT+257+1, -1, true);
		this.fontRenderer.drawString("Size", uL+451+2, uT+274+1, -1, true);
		this.fontRenderer.drawString("X", uL+387+2, uT+277+1, -1, true);
		this.fontRenderer.drawString("Y", uL+387+2, uT+291+1, -1, true);
		this.fontRenderer.drawString("Z", uL+387+2, uT+305+1, -1, true);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
