package bioMorpho.handler;

import static bioMorpho.handler.BioMorphoCurRenderInfo.partialTickTime;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosX;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosY;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosZ;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;

import bioMorpho.data.TextureData;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.manager.ModelManager_;
import bioMorpho.util.RandomUtils;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class PlayerRenderingHandler {
	
	@ForgeSubscribe
	public void preRender(RenderPlayerEvent.Pre event) {
		ModelCustom model = ModelManager_.getClientInstance().getModelForPlayer(event.entityPlayer.username);
		if(model != null && !model.isDefaultBipedModel) {
			this.renderPlayer(event.entityPlayer, model);
			event.setCanceled(true);
		}
	}
	
	public void renderPlayer(EntityPlayer entity, ModelCustom model) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_POLYGON_BIT);
		GL11.glPushAttrib(GL11.GL_TRANSFORM_BIT);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		Vec3 vec = entity.getPosition(partialTickTime);
		GL11.glTranslated(vec.xCoord - renderPosX, vec.yCoord - renderPosY, vec.zCoord - renderPosZ);
		
		// Adjust for Client player
		if(entity == FMLClientHandler.instance().getClient().thePlayer) GL11.glTranslated(0D, -1.62D, 0D);
		
		this.preRenderRotations(entity);
		
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(-1F, 1F, -1F);
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		this.renderModel(entity, model);
		
		GL11.glPopAttrib();
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	
	public void preRenderRotations(EntityLivingBase entity) {
		GL11.glRotatef(180F-RandomUtils.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTickTime), 0F, 1F, 0F);
		if(entity.deathTime > 0) {
			// Copied and slightly revised (Still some minor abstraction on the math, like non-paranthesized multiplication and division) from RenderLivingEntity.rotateCorpse()
			float f3 = (entity.deathTime + partialTickTime - 1F) / 20F * 1.6F;
			f3 = MathHelper.sqrt_float(f3);
			
			if(f3 > 1F) f3 = 1F;
			
			GL11.glRotatef(f3*90F, 0F, 0F, 1F);
		}
	}
	
	public void renderModel(EntityPlayer entity, ModelCustom model) {
		RenderUtils.bindTexture(TextureData.PATTERN_DEFAULT);
		if(!entity.isInvisible()) {
			model.render(entity, partialTickTime);
		} else if(!entity.func_98034_c(Minecraft.getMinecraft().thePlayer)) {
			//TODO dat piece of code to teh right
		}
	}
	
}
