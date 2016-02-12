package bioMorpho.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.TextureData.ModelTextures;
import bioMorpho.entity.EntityBlueMeteor;
import bioMorpho.model.Models;

/**
 * @author Brighton Ancelin
 *
 */
public class EntityBlueMeteorRenderer extends Render {
	
	public void renderBlueMeteor(EntityBlueMeteor entity, double x, double y, double z, float idk, float partialTickTime) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor4f(1F, 1F, 1F, 0.9F);
		GL11.glTranslated(x, y, z);
		GL11.glScaled(entity.getSize(), entity.getSize(), entity.getSize());
		GL11.glRotatef(entity.customRotYaw, 0F, 1F, 0F);
		GL11.glRotatef(entity.customRotPitch, 1F, 0F, 0F);
		this.func_110777_b(entity);
		Models.meteor.renderAll();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation func_110775_a(Entity entity) {return ModelTextures.METEOR_BLUE;}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float idkButUsedInPaintingsForRotation, float partialTickTime) {
		this.renderBlueMeteor((EntityBlueMeteor)entity, x, y, z, idkButUsedInPaintingsForRotation, partialTickTime);
	}
	
}
