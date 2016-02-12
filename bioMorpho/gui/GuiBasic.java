package bioMorpho.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class GuiBasic extends GuiContainer {

	public GuiBasic(Container container) {
		super(container);
	}
	
	public Minecraft getMinecraft() {return this.mc;}
	
	public void drawTexturedRect(int x, int y, int width, int height, int u, int v, int textureFileWidth, int textureFileHeight) {
		double pixelSizeX = 1 / (double)textureFileWidth;
		double pixelSizeY = 1 / (double)textureFileWidth;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(x+0), (double)(y+height), (double)this.zLevel, (double)((float)(u+0)*pixelSizeX), (double)((float)(v+height)*pixelSizeY));
		tessellator.addVertexWithUV((double)(x+width), (double)(y+height), (double)this.zLevel, (double)((float)(u+width)*pixelSizeX), (double)((float)(v+height)*pixelSizeY));
		tessellator.addVertexWithUV((double)(x+width), (double)(y+0), (double)this.zLevel, (double)((float)(u+width)*pixelSizeX), (double)((float)(v+0)*pixelSizeY));
		tessellator.addVertexWithUV((double)(x+0), (double)(y+0), (double)this.zLevel, (double)((float)(u+0)*pixelSizeX), (double)((float)(v+0)*pixelSizeY));
		tessellator.draw();
	}
}
