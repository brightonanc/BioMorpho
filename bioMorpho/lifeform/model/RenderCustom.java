package bioMorpho.lifeform.model;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import bioMorpho.data.TextureData;
import bioMorpho.manager.ModelManager_;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Deprecated as of the 1.6.2 Update due to use of a new Forge Event {@link RenderPlayerEvent}
 * 
 * @author Brighton Ancelin
 *
 */
@SideOnly(Side.CLIENT)
@Deprecated
public class RenderCustom extends RenderPlayer {
	
	public RenderCustom() {
		this.shadowSize = 0.5F; //TODO add a shadowSize finder
	}
	
	public void renderPlayerCustom(EntityPlayer player, double posX, double posY, double posZ, float yaw, float partialTickTime) {
		//TODO add more code
//		this.mainModel = ModelManager_.getClientInstance().getModelForPlayer(player.username);
//		if(this.mainModel == null) this.mainModel = new ModelCustom(false);
		super.func_130000_a(player, posX, posY, posZ, yaw, partialTickTime);
	}
	
	@Override
	public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float partialTickTime) {
		this.renderPlayerCustom((EntityPlayer)entity, posX, posY, posZ, yaw, partialTickTime);
	}
	
	/*
	 * This method shouldn't be necessary, but just in case, I called my rendering method from it
	 */
	@Override
	public void func_130000_a(EntityLivingBase entity, double posX, double posY, double posZ, float yaw, float partialTickTime) {
		this.renderPlayerCustom((EntityPlayer)entity, posX, posY, posZ, yaw, partialTickTime);
	}
	
	@Override
	protected void func_110777_b(Entity entity) {
		this.func_110776_a(TextureData.PATTERN_DEFAULT);
	}
}
