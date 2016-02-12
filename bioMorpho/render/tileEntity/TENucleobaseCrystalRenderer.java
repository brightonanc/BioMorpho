package bioMorpho.render.tileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.TextureData.ModelTextures;
import bioMorpho.entity.particle.EntityCustomSmokeFX;
import bioMorpho.model.Models;
import bioMorpho.tileEntity.TENucleobaseCrystal;
import bioMorpho.util.MathUtils;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * @author Brighton Ancelin
 *
 */
public class TENucleobaseCrystalRenderer extends TEBioMorphoRenderer {
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTickTime) {
		if(!(tileEntity instanceof TENucleobaseCrystal)) return;
		TENucleobaseCrystal te = (TENucleobaseCrystal)tileEntity;
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y, z);
		
		GL11.glTranslated(0.5D, 0.5D, 0.5D);
		switch(te.getOrientation()) {
		case UP: break;
		case DOWN: GL11.glRotatef(180, 1F, 0F, 0F); break;
		case WEST: GL11.glRotatef(90, 0F, 0F, 1F); break;
		case EAST: GL11.glRotatef(90, 0F, 0F, -1F); break;
		case NORTH: GL11.glRotatef(90, -1F, 0F, 0F); break;
		case SOUTH: GL11.glRotatef(90, 1F, 0F, 0F); break;
		default: break;
		}
		GL11.glTranslated(-0.5D, -0.5D, -0.5D);
		
		GL11.glTranslated((8/16D), (8/16D), (8/16D));
		GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
		ResourceLocation[] textures = {};
		switch(te.getNucleobase()) {
		case ADENINE: textures = ModelTextures.NUCLEOBASE_CRYSTAL_A; break;
		case GUANINE: textures = ModelTextures.NUCLEOBASE_CRYSTAL_G; break;
		case THYMINE: textures = ModelTextures.NUCLEOBASE_CRYSTAL_T; break;
		case CYTOSINE: textures = ModelTextures.NUCLEOBASE_CRYSTAL_C; break;
		default: break;
		}
		Minecraft mc = FMLClientHandler.instance().getClient();
		int renderIndex = (((int)mc.theWorld.getWorldTime()/7) % textures.length);
//		double colorShift = ((TENucleobaseCrystal)tileEntity).getCharge() / (double)((TENucleobaseCrystal)tileEntity).getMaxCharge();
//		colorShift = (colorShift + 3) / (3 + 1 + 0);
		this.func_110628_a(textures[renderIndex]);
//		GL11.glColor3d(colorShift, colorShift, colorShift);
		if(te.isUber()) {
			double alteration = MathUtils.sin((mc.theWorld.getWorldTime()/1D)*Math.PI) / 8;
			GL11.glScaled(1-alteration, 1+alteration, 1-alteration);
		}
		Models.nucleobaseCrystal.renderAll();
		
		if(te.isAtMaxCharge()) {
			double fxX = te.xCoord + 0.5D;
			double fxY = te.yCoord + 0.5D;
			double fxZ = te.zCoord + 0.5D;
			switch(te.getOrientation()) {
			case EAST: fxX += 0.5D; break;
			case WEST: fxX -= 0.5D; break;
			case UP: fxY += 0.5D; break;
			case DOWN: fxY -= 0.5D; break;
			case SOUTH: fxZ += 0.5D; break;
			case NORTH: fxZ -= 0.5D; break;
			default: break;
			}
			EntityCustomSmokeFX fx = new EntityCustomSmokeFX(te.worldObj, fxX, fxY, fxZ, 0D, 0.01D, 0D, false);
			float[] rgb = te.getNucleobase().getDefaultRGBFloatArray();
			fx.setRBGColorF(rgb[0], rgb[1], rgb[2]);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
		}
		
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
