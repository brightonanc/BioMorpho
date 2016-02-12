package bioMorpho.render.entity;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.TextureData.ModelTextures;
import bioMorpho.model.Models;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * @author Brighton Ancelin
 *
 */
public class EntityEnergeticGlassOrbRenderer extends Render {
	
	public void renderOrb(Entity entity, double x, double y, double z, float idk, float partialTickTime) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glTranslated(x, y, z);
		GL11.glScaled(0.7D, 0.7D, 0.7D);
		GL11.glRotatef((entity.ticksExisted+partialTickTime)*43F, 1F, 0F, 1F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.func_110777_b(entity);
		Models.orb.renderAll();
		
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation func_110775_a(Entity entity) {return ModelTextures.ENERGETIC_GLASS_ORB;}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float idkButUsedInPaintings, float partialTickTime) {
		this.renderOrb(entity, x, y, z, idkButUsedInPaintings, partialTickTime);
	}
	
}
