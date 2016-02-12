package bioMorpho.render.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.TextureData.ModelTextures;
import bioMorpho.model.Models;
import bioMorpho.util.RenderUtils;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemGemOfGenesisHolderRenderer implements IItemRenderer {
	
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
		case ENTITY: this.renderGemOfGenesisHolder(0F, 0F, 0F, 0.5F); break;
		case EQUIPPED: this.renderGemOfGenesisHolder(1F, 0F, 1F, 0.5F); break;
		case EQUIPPED_FIRST_PERSON: this.renderGemOfGenesisHolder(1F, 1F, 1F, 0.5F); break;
		case INVENTORY: this.renderGemOfGenesisHolder(0F, 0F, 0F, 0.5F); break;
		default: break;
		}
	}
	
	protected void renderGemOfGenesisHolder(float x, float y, float z, float scale) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(x, y, z);
		GL11.glScalef(scale, scale, scale);
		RenderUtils.bindTexture(ModelTextures.GEM_OF_GENESIS_HOLDER_DORMANT);
		Models.gemOfGenesisHolder.renderAll();
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
