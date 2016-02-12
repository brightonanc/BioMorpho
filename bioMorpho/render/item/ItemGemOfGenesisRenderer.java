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
public class ItemGemOfGenesisRenderer implements IItemRenderer {
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
		switch(type) {
		case ENTITY: this.renderGemOfGenesis(0F, 0F, 0F, 0.3F, 45F, itemStack); break;
		case EQUIPPED: this.renderGemOfGenesis(1F, 0F, 1F, 0.7F, 45F, itemStack); break;
		case EQUIPPED_FIRST_PERSON: this.renderGemOfGenesis(1F, 1F, 1F, 0.7F, -45F, itemStack); break;
		case INVENTORY: this.renderGemOfGenesis(0F, 0F, 0F, 0.7F, 45F, itemStack); break;
		default: break;
		}
	}
	
	protected void renderGemOfGenesis(float x, float y, float z, float scale, float rotX, ItemStack itemStack) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(x, y, z);
		if(rotX != 0F) GL11.glRotatef(rotX, 1F, 0F, 0F);
		GL11.glScalef(scale, scale, scale);
		RenderUtils.bindTexture(ModelTextures.GEM_OF_GENESIS);
		Models.gemOfGenesis.renderAll();
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
