package bioMorpho.render.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.TextureData.ModelTextures;
import bioMorpho.model.Models;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemEnergeticGlassOrbRenderer implements IItemRenderer {
	
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {return true;}
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {return true;}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type) {
		case ENTITY: this.renderOrb(item, 0D, 0D, 0D, 0.4D); break;
		case EQUIPPED: this.renderOrb(item, 0D, 0D, 0D, 0.7D); break;
		case EQUIPPED_FIRST_PERSON: this.renderOrb(item, 0D, 0D, 0D, 0.7D); break;
		case INVENTORY: this.renderOrb(item, 0D, 0D, 0D, 0.7D); break;
		default: break;
		}
	}
	
	public void renderOrb(ItemStack itemStack, double x, double y, double z, double scale) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y, z);
		GL11.glScaled(scale, scale, scale);
		RenderUtils.bindTexture(ModelTextures.ENERGETIC_GLASS_ORB);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Models.orb.renderAll();
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	
}
