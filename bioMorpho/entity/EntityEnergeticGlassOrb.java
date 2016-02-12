package bioMorpho.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * @author Brighton Ancelin
 *
 */
public class EntityEnergeticGlassOrb extends EntityThrowable {
	
	public EntityEnergeticGlassOrb(World world) {
		super(world);
	}
	public EntityEnergeticGlassOrb(World world, EntityLivingBase entityLivingBase) {
		super(world, entityLivingBase);
	}
	public EntityEnergeticGlassOrb(World world, double x, double y, double z) {
		super(world, x, y, z);
	}
	
	/** This is basically an initial speed modifier */
	@Override
	protected float func_70182_d() {return 4F;}
	
	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if(!this.worldObj.isRemote) {
			this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 8F, false, true);
			this.setDead();
		}
	}
	
}
