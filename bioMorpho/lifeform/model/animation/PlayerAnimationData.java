package bioMorpho.lifeform.model.animation;

import net.minecraft.entity.player.EntityPlayer;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.util.RandomUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class PlayerAnimationData {
	
	private EntityPlayer entity;
	private ModelCustom model;
	private float partialTickTime;
	
	public PlayerAnimationData(
			EntityPlayer entity,
			ModelCustom model,
			float partialTickTime) {
		this.entity = entity;
		this.model = model;
		this.partialTickTime = partialTickTime;
	}
		
	public EntityPlayer getEntity() { return this.entity; }
	public ModelCustom getModel() { return this.model; }
	public float getHeadRotX() {return RandomUtils.getRotation(this.entity.prevRotationPitch, this.entity.rotationPitch, this.partialTickTime);}
	public float getHeadRotY() {return -(RandomUtils.interpolateRotation(this.entity.prevRotationYawHead, this.entity.rotationYawHead, this.partialTickTime) - RandomUtils.interpolateRotation(this.entity.prevRenderYawOffset, this.entity.renderYawOffset, this.partialTickTime));}
	
}
