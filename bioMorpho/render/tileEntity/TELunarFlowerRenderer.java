package bioMorpho.render.tileEntity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import bioMorpho.data.TextureData.ModelTextures;
import bioMorpho.model.Models;
import bioMorpho.tileEntity.TELunarFlower;
import bioMorpho.util.RandomUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class TELunarFlowerRenderer extends TEBioMorphoRenderer {
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTickTime) {
		if(!(tileEntity instanceof TELunarFlower)) return;
		TELunarFlower te = (TELunarFlower)tileEntity;
		
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
		
		GL11.glTranslated(x, y, z);
		
//		int alphaRate = 34;
//		float targetAlpha = te.calcAlpha();
//		te.alphaVal += (targetAlpha-te.alphaVal)/alphaRate;
//		if(Math.abs(targetAlpha-te.alphaVal) <= alphaRate/2048F) te.alphaVal = targetAlpha;
		
		if(te.alphaVal < 1F) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glColor4f(1F, 1F, 1F, te.alphaVal);
		}
		
//		int time = (int)(te.worldObj.getWorldTime() % 24000);
//		if(time < 12000) {
//			int timeDistFromNight = (12000/2) - Math.abs((12000/2) - time);
//			float alphaFactor = timeDistFromNight < 180 ? 1F - (timeDistFromNight/200F) : (20F/200F);
//			GL11.glEnable(GL11.GL_BLEND);
//			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//			GL11.glDisable(GL11.GL_ALPHA_TEST);
//			GL11.glColor4f(1F, 1F, 1F, alphaFactor);
//		}
		
		GL11.glTranslated(0.5D, 0D, 0.5D);
		GL11.glScalef(0.12F, 0.12F, 0.12F);
		this.func_110628_a(ModelTextures.LUNAR_FLOWER);
		Models.lunarFlower.renderAll();
		if(te.hasLunarOrb()) {
			GL11.glTranslated(0D, 0.1D, 0D);
			GL11.glScaled(0.3D, 0.3D, 0.3D);
			this.func_110628_a(ModelTextures.LUNAR_ORB);
			Models.lunarOrb.renderAll();
		}
		
		GL11.glPopAttrib();
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
