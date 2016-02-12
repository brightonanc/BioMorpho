package bioMorpho.entity.particle;

import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;

/**
 * @author Brighton Ancelin
 *
 */
public class EntityCustomSmokeFX extends EntitySmokeFX {
	
	public EntityCustomSmokeFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ, boolean isPrecise) {
		super(world, x, y, z, motionX, motionY, motionZ);
		if(isPrecise) this.fixEntityFXInheritedRandomization(motionX, motionY, motionZ);
	}
	
	public EntityCustomSmokeFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ, float scale, boolean isPrecise) {
		super(world, x, y, z, motionX, motionY, motionZ, scale);
		if(isPrecise) this.fixEntityFXInheritedRandomization(motionX, motionY, motionZ);
	}
	
	private void fixEntityFXInheritedRandomization(double motionX, double motionY, double motionZ) {
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}
	
	public void setMaxParticleAge(int maxAge) {
		this.particleMaxAge = maxAge;
	}
	
	@Override
	public void onUpdate() {
		// Copied from EntitySmokeFX and revised
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		if (this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}
		
		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
	}
	
}
