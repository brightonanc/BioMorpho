package bioMorpho.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * @author Brighton Ancelin
 *
 */
public class RenderUtils {
	
	public static void drawTexturedRect(double x, double y, double z, int width, int height, int u, int v, int textureFileWidth, int textureFileHeight) {
		double pixelSizeX = 1 / (double)textureFileWidth;
		double pixelSizeY = 1 / (double)textureFileWidth;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(x+0), (double)(y+height), (double)z, (double)((float)(u+0)*pixelSizeX), (double)((float)(v+height)*pixelSizeY));
		tessellator.addVertexWithUV((double)(x+width), (double)(y+height), (double)z, (double)((float)(u+width)*pixelSizeX), (double)((float)(v+height)*pixelSizeY));
		tessellator.addVertexWithUV((double)(x+width), (double)(y+0), (double)z, (double)((float)(u+width)*pixelSizeX), (double)((float)(v+0)*pixelSizeY));
		tessellator.addVertexWithUV((double)(x+0), (double)(y+0), (double)z, (double)((float)(u+0)*pixelSizeX), (double)((float)(v+0)*pixelSizeY));
		tessellator.draw();
	}
	
	public static void bindTexture(TextureManager renderEngine, ResourceLocation texture) {
		renderEngine.func_110577_a(texture);
	}
	
	public static void bindTexture(ResourceLocation texture) {
		bindTexture(FMLClientHandler.instance().getClient().renderEngine, texture);
	}
	
}
