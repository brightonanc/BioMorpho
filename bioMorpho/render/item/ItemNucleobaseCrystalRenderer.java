package bioMorpho.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.NBTData;
import bioMorpho.data.TextureData.ModelTextures;
import bioMorpho.etc.Nucleobases;
import bioMorpho.model.Models;
import bioMorpho.util.MathUtils;
import bioMorpho.util.RenderUtils;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemNucleobaseCrystalRenderer implements IItemRenderer {
	
	@Override
	public boolean handleRenderType(ItemStack itemStack, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemStack, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {		
		switch(type) {
		case ENTITY: this.renderCrystal(0F, 0.5F, 0F, itemStack); break;
		case EQUIPPED: this.renderCrystal(0F, 1F, 0F, itemStack); break;
		case INVENTORY: this.renderCrystal(0F, 0F, 0F, itemStack); break;
		default: break;
		}
	}
	
	protected void renderCrystal(float x, float y, float z, ItemStack itemStack) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y, z);
		GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
		ResourceLocation[] textures = {};
		switch(Nucleobases.values()[itemStack.getTagCompound().getInteger(NBTData.NUCLEOBASE_ENUM)]) {
		case ADENINE: textures = ModelTextures.NUCLEOBASE_CRYSTAL_A; break;
		case GUANINE: textures = ModelTextures.NUCLEOBASE_CRYSTAL_G; break;
		case THYMINE: textures = ModelTextures.NUCLEOBASE_CRYSTAL_T; break;
		case CYTOSINE: textures = ModelTextures.NUCLEOBASE_CRYSTAL_C; break;
		default: break;
		}
		Minecraft mc = FMLClientHandler.instance().getClient();
		int renderIndex = (((int)mc.theWorld.getWorldTime()/7) % textures.length);//!mc.isGamePaused ? (((int)mc.theWorld.getWorldTime()/7) % textures.length) : this.lastRenderIndex;
//		double colorShift = (itemStack.getTagCompound().getInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE) / (double)itemStack.getTagCompound().getInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE_MAX));
//		colorShift = (colorShift + 3) / (3 + 1 + 0);
		RenderUtils.bindTexture(textures[renderIndex]);
//		GL11.glColor3d(colorShift, colorShift, colorShift);
		if(itemStack.getTagCompound().getBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS)) {
			double alteration = MathUtils.sin((mc.theWorld.getWorldTime()/1D)*Math.PI) / 8;
			GL11.glScaled(1-alteration, 1+alteration, 1-alteration);
		}
		Models.nucleobaseCrystal.renderAll();
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
