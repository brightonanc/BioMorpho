package bioMorpho.entity;

import java.util.List;
import java.util.Random;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import bioMorpho.block.BioMorphoBlocks;
import bioMorpho.data.ConfigSettings;
import bioMorpho.data.NBTData;
import bioMorpho.entity.particle.EntityCustomSmokeFX;
import bioMorpho.item.BioMorphoItems;
import bioMorpho.util.MathUtils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class EntityBlueMeteor extends Entity implements IEntityAdditionalSpawnData {
	
	public static final double terminalVelocity = -7.3D;
	
	public float customRotYaw;
	public float customRotPitch;
	
	protected boolean isLanded = false;
	protected float landingYaw;
	protected float landingPitch;
	protected double landingPosX;
	protected double landingPosY;
	protected double landingPosZ;
	
	public double size;
	
	protected int health;
	protected int lastTickHurt;
	protected final int damageTimeout = 7;
	
	protected boolean hasCausedImpactExplosion = false;
	
	public static final int healthId = 2;
	
	public EntityBlueMeteor(World world) {
		this(world, ((new Random()).nextInt(24+1) + 8) / 32D);
	}
	
	public EntityBlueMeteor(World world, double size) {
		super(world);
		this.size = size;
		this.init(true);
	}
	
	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(healthId, Integer.valueOf(-1));
		
	}
	
	public void init(boolean setHealth) {
		this.noClip = true;
		this.width = (float)(2.8*this.size);
		this.height = (float)(7.4*this.size);
		if(setHealth) this.health = this.getMaxHealth();
		this.dataWatcher.updateObject(healthId, Integer.valueOf(this.health));
	}
	
	protected void landMeteor() {
		if(!this.hasCausedImpactExplosion) {
			// Explosion Size is made from Kinetic Energy = (1/2) * Mass * Velocity^2; The 1/12 is a custom constant
			float explosionSize = (1F/7F) * (float)(MathUtils.cube(this.size) * (MathUtils.square(this.motionX)+MathUtils.square(this.motionY)+MathUtils.square(this.motionZ)));
			if(!this.worldObj.isRemote) {
				if(ConfigSettings.METEOR_EXPLOSIONS) {
					this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, explosionSize, true, true);
				}
				this.worldObj.playSoundAtEntity(this, "random.explode", 7000F, 0.1F);
			}
			this.motionX /= 3D;
			this.motionY /= 3D;
			this.motionZ /= 3D;
			for(Entity curEntity : (List<Entity>)this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox)) {
				if(curEntity instanceof EntityLivingBase) {
					EntityLivingBase entityLiving = (EntityLivingBase)curEntity;
					entityLiving.attackEntityFrom(DamageSource.fallingBlock, (int)(this.size*40));
				}
			}
			this.hasCausedImpactExplosion = true;
			return;
		}
		this.isLanded = true;
		this.landingYaw = this.customRotYaw;
		this.landingPitch = this.customRotPitch;
		this.landingPosX = this.posX;
		this.landingPosY = this.posY;
		this.landingPosZ = this.posZ;
	}
	
	protected void unlandMeteor() {
		this.isLanded = false;
	}
	
	public int getMaxHealth() {
		return (int)Math.floor(21 * this.size);
	}
	
	public void hurt(int value) {
		this.worldObj.setEntityState(this, (byte)2);
		this.health -= value;
	}
	
	public int getHealth() { return this.health; }
	
	public double getSize() { return this.size; }
	
	public boolean isLanded() { return this.isLanded; }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!this.worldObj.isRemote) {
			this.dataWatcher.updateObject(healthId, Integer.valueOf(this.health));
		} else {
			this.health = this.dataWatcher.getWatchableObjectInt(healthId);
		}
		
		if(this.isLanded()) {
			this.setVelocity(0D, 0D, 0D);
			if(!this.worldObj.isRemote) {
				this.setPosition(this.landingPosX, this.landingPosY, this.landingPosZ);
			}
			this.customRotYaw = this.landingYaw;
			this.customRotPitch = this.landingPitch;
		} else {
			this.motionY -= 0.07; // Apply gravity
			if(this.motionY < terminalVelocity) this.motionY = terminalVelocity;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			double yaw = Math.atan(this.motionX/this.motionZ);
			if(Double.isNaN(yaw)) yaw = 0D;
			double horizontal = Math.sqrt(MathUtils.square(this.motionX) + MathUtils.square(this.motionZ));
			double pitch = Math.atan(horizontal/this.motionY);
			if(Double.isNaN(pitch)) pitch = 0D;
			this.customRotYaw = (float)(yaw * (180/Math.PI)) % 360F;
			this.customRotPitch = (float)(pitch * (180/Math.PI)) % 360F;
		}
		
		if(this.worldObj.checkBlockCollision(this.boundingBox) && !this.worldObj.getCollidingBlockBounds(this.boundingBox).isEmpty()) {
			if(!this.isLanded()) {
				int precision = 7;
				double shift = (7.4D * this.size) / precision;
				for(int i = 0; i < (int)Math.ceil(-this.motionY/shift); i++) {
					this.moveEntity(this.motionX*(shift/this.motionY), shift, this.motionZ*(shift/this.motionY));
					if(this.worldObj.getCollidingBlockBounds(this.boundingBox).isEmpty()) {
						this.moveEntity(this.motionX*(-shift/this.motionY), -shift, this.motionZ*(-shift/this.motionY));
						break;
					}
				}
				this.landMeteor();
			}
		} else {
			if(this.isLanded()) {
				this.unlandMeteor();
				this.addVelocity(0D, -1D, 0D);
			}
		}
		
		double radius = this.width/2;
		double length = this.height;
		if(Math.abs(this.customRotPitch) <= 45F) {
			// +Y
			this.boundingBox.setBounds(this.posX-radius, this.posY, this.posZ-radius, this.posX+radius, this.posY+length, this.posZ+radius);
		} else {
			if(45 > this.customRotYaw || this.customRotYaw >= 315) {
				// +Z
				this.boundingBox.setBounds(this.posX-radius, this.posY-radius, this.posZ, this.posX+radius, this.posY+radius, this.posZ+length);
			} else if(135 > this.customRotYaw && this.customRotYaw >= 45) {
				// -X
				this.boundingBox.setBounds(this.posX-length, this.posY-radius, this.posZ-radius, this.posX, this.posY+radius, this.posZ+radius);
			} else if(225 > this.customRotYaw && this.customRotYaw >= 135) {
				// -Z
				this.boundingBox.setBounds(this.posX-radius, this.posY-radius, this.posZ-length, this.posX+radius, this.posY+radius, this.posZ);
			} else if(315 > this.customRotYaw && this.customRotYaw >= 225) {
				// +X
				this.boundingBox.setBounds(this.posX, this.posY-radius, this.posZ-radius, this.posX+length, this.posY+radius, this.posZ+radius);
			}
			// Default is the +Y form
			else this.boundingBox.setBounds(this.posX-radius, this.posY, this.posZ-radius, this.posX+radius, this.posY+length, this.posZ+radius);
		}
		
		if(this.health <= 0) this.destroyMeteor();
		
		for(Entity curEntity : (List<Entity>)this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox)) {
			double positiveXShift = this.boundingBox.maxX - curEntity.boundingBox.minX;
			double positiveYShift = this.boundingBox.maxY - curEntity.boundingBox.minY;
			double positiveZShift = this.boundingBox.maxZ - curEntity.boundingBox.minZ;
			double negativeXShift = this.boundingBox.minX - curEntity.boundingBox.maxX;
			double negativeYShift = this.boundingBox.minY - curEntity.boundingBox.maxY;
			double negativeZShift = this.boundingBox.minZ - curEntity.boundingBox.maxZ;
			boolean useXShift = !(positiveXShift <= 0 || negativeXShift >= 0);
			boolean useYShift = !(positiveYShift <= 0 || negativeYShift >= 0);
			boolean useZShift = !(positiveZShift <= 0 || negativeZShift >= 0);
			double xShift = Math.abs(positiveXShift) < Math.abs(negativeXShift) ? positiveXShift : negativeXShift;
			double yShift = Math.abs(positiveYShift) < Math.abs(negativeYShift) ? positiveYShift : negativeYShift;
			double zShift = Math.abs(positiveZShift) < Math.abs(negativeZShift) ? positiveZShift : negativeZShift;
			if(useXShift && Math.abs(xShift) <= Math.abs(yShift) && Math.abs(xShift) <= Math.abs(zShift)) {
				curEntity.addVelocity(xShift, 0D, 0D);//curEntity.setVelocity(xShift, curEntity.motionY, curEntity.motionZ);
			} else if(useYShift && Math.abs(yShift) <= Math.abs(xShift) && Math.abs(yShift) <= Math.abs(zShift)) {
				curEntity.setVelocity(0D, yShift, 0D);//curEntity.setVelocity(curEntity.motionX, yShift, curEntity.motionZ);
			} else if(useZShift && Math.abs(zShift) <= Math.abs(xShift) && Math.abs(zShift) <= Math.abs(yShift)) {
				curEntity.addVelocity(0D, 0D, zShift);//curEntity.setVelocity(curEntity.motionX, curEntity.motionY, zShift);
			}
			
			if(curEntity instanceof EntityBlueMeteor) {
				((EntityBlueMeteor)curEntity).destroyMeteor();
			}
		}
		
		if(!this.hasCausedImpactExplosion && !this.worldObj.isRemote && this.ticksExisted % 14 == 0) {
			this.worldObj.playSoundAtEntity(this, "ambient.weather.thunder", 3000F, 3F);
		}
		if(this.worldObj.isRemote && this.ticksExisted % 7 == 0) {
			int flagVal = this.rand.nextInt(8);
			int xMultiplier = (flagVal==0||flagVal==1||flagVal==2)?1:(flagVal==3||flagVal==4)?0:-1;
			int zMultiplier = (flagVal==2||flagVal==4||flagVal==7)?1:(flagVal==1||flagVal==6)?0:-1;
			double x = this.posX + (this.size * 1 * xMultiplier) + (xMultiplier < 0 ? -this.rand.nextDouble() : this.rand.nextDouble());
			double z = this.posZ + (this.size * 1 * zMultiplier) + (zMultiplier < 0 ? -this.rand.nextDouble() : this.rand.nextDouble());
			double y = this.posY + (this.size * 7.4 * this.rand.nextDouble());
			EntityFX entityFX = new EntitySmokeFX(this.worldObj, x, y, z, 0D, 0.01D, 0D);
			entityFX.setRBGColorF(0x0c/255F, 0xee/255F, 0xcb/255F);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(entityFX);
		}
		
		if(!this.worldObj.isRemote && this.ticksExisted % (4*20) == 0) {
			List entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(7D, 7D, 7D));
			for(Entity curEntity : (List<Entity>)entities) {
				if(curEntity instanceof EntityLivingBase) {
					EntityLivingBase entityLiving = (EntityLivingBase)curEntity;
					switch(this.rand.nextInt(7)) {
					case 0: entityLiving.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 8*20, 3, true)); break;
					case 1: entityLiving.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 8*20, 2, true)); break;
					case 2: entityLiving.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 8*20, 1, true)); break;
					case 3: entityLiving.addPotionEffect(new PotionEffect(Potion.jump.getId(), 8*20, 1, true)); break;
					case 4: entityLiving.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 8*20, 1, true)); break;
					case 5: entityLiving.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 8*20, 3, true)); break;
					case 6: entityLiving.addPotionEffect(new PotionEffect(Potion.waterBreathing.getId(), 8*20, 3, true)); break;
					}
				}
			}
		}
		
		if(this.ticksExisted >= 8*24000) this.setDead();
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amountF) {
		int amount = (int)amountF;
		if(!this.worldObj.isRemote) {
			boolean success = false;
			int damage = 0;
			Entity attacker = damageSource.getSourceOfDamage();
			if(attacker != null && attacker instanceof EntityLivingBase) {
				success = true;
				damage = 1;
			} else if(damageSource.isExplosion()) {
				success = true;
				damage = amount;
			}
			if(success && this.ticksExisted - this.lastTickHurt >= this.damageTimeout) {
				this.lastTickHurt = this.ticksExisted;
				this.hurt(damage);
				return true;
			}
		}
		return false;
	}
	
	public void destroyMeteor() {
		if(!this.worldObj.isRemote) {
			int meteorBaseVal = (int)Math.floor(this.size * 64);
			ItemStack blueShardStack = new ItemStack(BioMorphoItems.blueMeteorShard, meteorBaseVal, 0);
			ItemStack lunarFlowerStack = new ItemStack(BioMorphoBlocks.lunarFlower, this.rand.nextInt(2), 0);
			EntityItem entityShards = new EntityItem(this.worldObj, this.posX, this.posY+7.4D, this.posZ, blueShardStack);
			EntityItem entityFlowers = new EntityItem(this.worldObj, this.posX, this.posY+7.4D, this.posZ, lunarFlowerStack);
			this.worldObj.spawnEntityInWorld(entityShards);
			this.worldObj.spawnEntityInWorld(entityFlowers);
			this.setDead();
			this.worldObj.setEntityState(this, (byte)3);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte updateId) {
		switch(updateId) {
		case 2:
			// Hurt
			System.out.println("Hurt");
			double y = 7.4D * this.size * this.rand.nextDouble();
			int particleCount = 64;
			for(int i = 0; i < particleCount; i++) {
				double x = MathUtils.cos(i*(360/particleCount));
				double z = MathUtils.sin(i*(360/particleCount));
				EntityCustomSmokeFX fx = new EntityCustomSmokeFX(this.worldObj, this.posX+(x*this.size), this.posY+y, this.posZ+(z*this.size), x/10D, 0D, z/10D, (float)(3*this.size), true);
				fx.setRBGColorF(0x0c/255F, 0xee/255F, 0xcb/255F);
				FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
			}
			break;
		case 3:
			// Death
			double y_1 = (7.4D * this.size)/2;
			int particleCount_1 = 64;
			for(int i = 0; i < particleCount_1; i++) {
				double x = MathUtils.cos(i*(360/particleCount_1));
				double z = MathUtils.sin(i*(360/particleCount_1));
				EntityCustomSmokeFX fx = new EntityCustomSmokeFX(this.worldObj, this.posX+(x*this.size), this.posY+y_1, this.posZ+(z*this.size), x/10D, 0D, z/10D, (float)(7*this.size), true);
				fx.setRBGColorF(0x0c/255F, 0xee/255F, 0xcb/255F);
				FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
			}
			break;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double par1) {return true;}
	
	@Override
	public boolean canBeCollidedWith() { return true; }
	
	@Override
	public boolean canBePushed() { return true; }
	
	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		double positiveXShift = this.boundingBox.maxX - player.boundingBox.minX;
		double positiveYShift = this.boundingBox.maxY - player.boundingBox.minY;
		double positiveZShift = this.boundingBox.maxZ - player.boundingBox.minZ;
		double negativeXShift = this.boundingBox.minX - player.boundingBox.maxX;
		double negativeYShift = this.boundingBox.minY - player.boundingBox.maxY;
		double negativeZShift = this.boundingBox.minZ - player.boundingBox.maxZ;
		boolean useXShift = !(positiveXShift <= 0 || negativeXShift >= 0);
		boolean useYShift = !(positiveYShift <= 0 || negativeYShift >= 0);
		boolean useZShift = !(positiveZShift <= 0 || negativeZShift >= 0);
		double xShift = Math.abs(positiveXShift) < Math.abs(negativeXShift) ? positiveXShift : negativeXShift;
		double yShift = Math.abs(positiveYShift) < Math.abs(negativeYShift) ? positiveYShift : negativeYShift;
		double zShift = Math.abs(positiveZShift) < Math.abs(negativeZShift) ? positiveZShift : negativeZShift;
		if(useXShift && Math.abs(xShift) <= Math.abs(yShift) && Math.abs(xShift) <= Math.abs(zShift)) {
			player.setVelocity(xShift, player.motionY, player.motionZ);
		} else if(useYShift && Math.abs(yShift) <= Math.abs(xShift) && Math.abs(yShift) <= Math.abs(zShift)) {
			player.setVelocity(player.motionX, yShift, player.motionZ);
		} else if(useZShift && Math.abs(zShift) <= Math.abs(xShift) && Math.abs(zShift) <= Math.abs(yShift)) {
			player.setVelocity(player.motionX, player.motionY, zShift);
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		this.customRotYaw = nbt.getFloat(NBTData.CUSTOM_ROTATION_YAW);
		this.customRotPitch = nbt.getFloat(NBTData.CUSTOM_ROTATION_PITCH);
		this.isLanded = nbt.getBoolean("meteorLanding");
		this.landingYaw = nbt.getFloat("landingYaw");
		this.landingPitch = nbt.getFloat("landingPitch");
		this.landingPosX = nbt.getDouble("landingPosX");
		this.landingPosY = nbt.getDouble("landingPosY");
		this.landingPosZ = nbt.getDouble("landingPosZ");
		this.size = nbt.getDouble("meteorSize");
		this.health = nbt.getInteger("meteorHealth");
		this.hasCausedImpactExplosion = nbt.getBoolean("hasCausedImpactExplosion");
		this.init(false);
	}
	
	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		this.customRotYaw = data.readFloat();
		this.customRotPitch = data.readFloat();
		this.isLanded = data.readBoolean();
		this.landingYaw = data.readFloat();
		this.landingPitch = data.readFloat();
		this.landingPosX = data.readDouble();
		this.landingPosY = data.readDouble();
		this.landingPosZ = data.readDouble();
		this.size = data.readDouble();
		this.health = data.readInt();
		this.hasCausedImpactExplosion = data.readBoolean();
		this.init(false);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat(NBTData.CUSTOM_ROTATION_YAW, this.customRotYaw);
		nbt.setFloat(NBTData.CUSTOM_ROTATION_PITCH, this.customRotPitch);
		nbt.setBoolean("meteorLanding", this.isLanded);
		nbt.setFloat("landingYaw", this.landingYaw);
		nbt.setFloat("landingPitch", this.landingPitch);
		nbt.setDouble("landingPosX", this.landingPosX);
		nbt.setDouble("landingPosY", this.landingPosY);
		nbt.setDouble("landingPosZ", this.landingPosZ);
		nbt.setDouble("meteorSize", this.size);
		nbt.setInteger("meteorHealth", this.health);
		nbt.setBoolean("hasCausedImpactExplosion", this.hasCausedImpactExplosion);
	}
	
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeFloat(this.customRotYaw);
		data.writeFloat(this.customRotPitch);
		data.writeBoolean(this.isLanded);
		data.writeFloat(this.landingYaw);
		data.writeFloat(this.landingPitch);
		data.writeDouble(this.landingPosX);
		data.writeDouble(this.landingPosY);
		data.writeDouble(this.landingPosZ);
		data.writeDouble(this.size);
		data.writeInt(this.health);
		data.writeBoolean(this.hasCausedImpactExplosion);
	}

/*	
	public static final double terminalVelocity = 7.3D;
	
	public float customRotYaw;
	public float customRotPitch;
	
	protected boolean isLanded = false;
	protected float landingYaw;
	protected float landingPitch;
	protected double landingPosX;
	protected double landingPosY;
	protected double landingPosZ;
	
	protected double size;
	
	protected int health;
	protected int lastTickHurt;
	protected final int damageTimeout = 0;
	
	protected boolean hasCausedImpactExplosion = false;
	
	public EntityBlueMeteor(World world) {
		this(world, ((new Random()).nextInt(24+1) + 8) / 32D);
	}
	
	public EntityBlueMeteor(World world, double size) {
		super(world);
		this.size = size;
		this.init(true);
	}
	
	@Override
	protected void entityInit() {}
	
	public void init(boolean setHealth) {
		if(!this.worldObj.isRemote) this.worldObj.playSoundAtEntity(this, "ambient.weather.thunder", 10000.0F, 0.1F);
		this.noClip = true;
		this.width = (float)(2.8*this.size);
		this.height = (float)(7.4*this.size);
		if(setHealth) this.health = this.getMaxHealth();
	}
	
	protected void landMeteor() {
		if(this.worldObj.isRemote) return;
		if(!this.hasCausedImpactExplosion) {
			// Explosion Size is made from Kinetic Energy = (1/2) * Mass * Velocity^2; The 1/12 is a custom constant
			float explosionSize = (1F/12F) * (float)(MathUtils.cube(this.size) * (MathUtils.square(this.motionX)+MathUtils.square(this.motionY)+MathUtils.square(this.motionZ)));
			this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, explosionSize, true, true);
			this.motionX /= 3D;
			this.motionY /= 3D;
			this.motionZ /= 3D;
			this.hasCausedImpactExplosion = true;
			System.out.println("Explosion on "+FMLCommonHandler.instance().getEffectiveSide()+" at "+this.posX+" "+this.posZ);
			return;
		}
		this.isLanded = true;
		this.landingYaw = this.customRotYaw;
		this.landingPitch = this.customRotPitch;
		this.landingPosX = this.posX;
		this.landingPosY = this.posY;
		this.landingPosZ = this.posZ;
	}
	
	protected void unlandMeteor() {
		this.isLanded = false;
	}
	
	public int getMaxHealth() {
		return (int)Math.floor(21 * this.size);
	}
	
	public void hurt(int value) {
		this.worldObj.setEntityState(this, (byte)2);
		this.health -= value;
	}
	
	public int getHealth() { return this.health; }
	
	public double getSize() { return this.size; }
	
	public boolean isLanded() { return this.isLanded; }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.isLanded()) {
			this.setVelocity(0D, 0D, 0D);
			this.setPosition(this.landingPosX, this.landingPosY, this.landingPosZ);
			this.customRotYaw = this.landingYaw;
			this.customRotPitch = this.landingPitch;
		} else {
			this.motionY -= 0.07; // Apply gravity
			if(this.motionY > terminalVelocity) this.motionY = terminalVelocity;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			double yaw = Math.atan(this.motionX/this.motionZ);
			if(Double.isNaN(yaw)) yaw = 0D;
			double horizontal = Math.sqrt(MathUtils.square(this.motionX) + MathUtils.square(this.motionZ));
			double pitch = Math.atan(horizontal/this.motionY);
			if(Double.isNaN(pitch)) pitch = 0D;
			this.customRotYaw = (float)(yaw * (180/Math.PI)) % 360F;
			this.customRotPitch = (float)(pitch * (180/Math.PI)) % 360F;
		}
		
		if(this.worldObj.checkBlockCollision(this.boundingBox) && !this.worldObj.getCollidingBlockBounds(this.boundingBox).isEmpty()) {
			if(!this.isLanded()) this.landMeteor();
		} else {
			if(this.isLanded()) {
				this.unlandMeteor();
				this.addVelocity(0D, -1D, 0D);
			}
		}
		
		double radius = this.width/2;
		double length = this.height;
		if(Math.abs(this.customRotPitch) <= 45F) {
			// +Y
			this.boundingBox.setBounds(this.posX-radius, this.posY, this.posZ-radius, this.posX+radius, this.posY+length, this.posZ+radius);
		} else {
			if(45 > this.customRotYaw || this.customRotYaw >= 315) {
				// +Z
				this.boundingBox.setBounds(this.posX-radius, this.posY-radius, this.posZ, this.posX+radius, this.posY+radius, this.posZ+length);
			} else if(135 > this.customRotYaw && this.customRotYaw >= 45) {
				// -X
				this.boundingBox.setBounds(this.posX-length, this.posY-radius, this.posZ-radius, this.posX, this.posY+radius, this.posZ+radius);
			} else if(225 > this.customRotYaw && this.customRotYaw >= 135) {
				// -Z
				this.boundingBox.setBounds(this.posX-radius, this.posY-radius, this.posZ-length, this.posX+radius, this.posY+radius, this.posZ);
			} else if(315 > this.customRotYaw && this.customRotYaw >= 225) {
				// +X
				this.boundingBox.setBounds(this.posX, this.posY-radius, this.posZ-radius, this.posX+length, this.posY+radius, this.posZ+radius);
			}
			// Default is the +Y form
			else this.boundingBox.setBounds(this.posX-radius, this.posY, this.posZ-radius, this.posX+radius, this.posY+length, this.posZ+radius);
		}
		
		if(this.health <= 0) this.destroyMeteor();
		
		for(Entity curEntity : (List<Entity>)this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox)) {
			double positiveXShift = this.boundingBox.maxX - curEntity.boundingBox.minX;
			double positiveYShift = this.boundingBox.maxY - curEntity.boundingBox.minY;
			double positiveZShift = this.boundingBox.maxZ - curEntity.boundingBox.minZ;
			double negativeXShift = this.boundingBox.minX - curEntity.boundingBox.maxX;
			double negativeYShift = this.boundingBox.minY - curEntity.boundingBox.maxY;
			double negativeZShift = this.boundingBox.minZ - curEntity.boundingBox.maxZ;
			boolean useXShift = !(positiveXShift <= 0 || negativeXShift >= 0);
			boolean useYShift = !(positiveYShift <= 0 || negativeYShift >= 0);
			boolean useZShift = !(positiveZShift <= 0 || negativeZShift >= 0);
			double xShift = Math.abs(positiveXShift) < Math.abs(negativeXShift) ? positiveXShift : negativeXShift;
			double yShift = Math.abs(positiveYShift) < Math.abs(negativeYShift) ? positiveYShift : negativeYShift;
			double zShift = Math.abs(positiveZShift) < Math.abs(negativeZShift) ? positiveZShift : negativeZShift;
			if(useXShift && Math.abs(xShift) <= Math.abs(yShift) && Math.abs(xShift) <= Math.abs(zShift)) {
				curEntity.setVelocity(xShift, curEntity.motionY, curEntity.motionZ);
			} else if(useYShift && Math.abs(yShift) <= Math.abs(xShift) && Math.abs(yShift) <= Math.abs(zShift)) {
				curEntity.setVelocity(curEntity.motionX, yShift, curEntity.motionZ);
			} else if(useZShift && Math.abs(zShift) <= Math.abs(xShift) && Math.abs(zShift) <= Math.abs(yShift)) {
				curEntity.setVelocity(curEntity.motionX, curEntity.motionY, zShift);
			}
			
			if(curEntity instanceof EntityBlueMeteor) {
				((EntityBlueMeteor)curEntity).destroyMeteor();
			}
		}
		
		if(!this.hasCausedImpactExplosion)if(!this.worldObj.isRemote && this.ticksExisted % 14 == 0) this.worldObj.playSoundAtEntity(this, "ambient.weather.thunder", 1000F, 3F);
		
		if(this.ticksExisted >= 7000) this.setDead();
		if(this.ticksExisted % 47 == 0) System.out.println(this.posX+" "+this.posY+" "+this.posZ);
	}
	
	@Override
	public void setDead() {
		Thread.dumpStack();
		super.setDead();
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource damageSource, int amount) {
		if(!this.worldObj.isRemote) {
			System.out.println("Health is "+this.health);
			boolean success = false;
			int damage = 0;
			Entity attacker = damageSource.getSourceOfDamage();
			if(attacker != null && attacker instanceof EntityLiving) {
				success = true;
				damage = 1;
			} else if(damageSource.isExplosion()) {
				success = true;
				damage = amount;
			}
			if(success && this.ticksExisted - this.lastTickHurt >= this.damageTimeout) {
				this.lastTickHurt = this.ticksExisted;
				this.hurt(damage);
				return true;
			}
		}
		return false;
	}
	
	public void destroyMeteor() {
		if(!this.worldObj.isRemote) {
			int meteorShardCount = (int)Math.floor(this.size * 64);
			int stackCount = meteorShardCount > 16 ? 16 : meteorShardCount;
			int excessStackCount = meteorShardCount % stackCount;
			for(int i = 0; i < stackCount; i++) {
				int amount = meteorShardCount/stackCount;
				if(i < excessStackCount) amount += 1;
				ItemStack curStack = new ItemStack(Item.enderPearl.itemID, amount, 0);
				double xShift = (this.size/8) * (this.rand.nextInt(32+1)/16D);
				double zShift = (this.size/8) * (this.rand.nextInt(32+1)/16D);
				xShift *= this.rand.nextBoolean() ? 1 : -1;
				zShift *= this.rand.nextBoolean() ? 1 : -1;
				double x = this.posX + xShift;
				double z = this.posZ + zShift;
				double y = this.posY + ((this.rand.nextInt(32+1)/8) + 3.4);
				EntityItem entityItem = new EntityItem(this.worldObj, x, y, z, curStack);
				entityItem.motionX = xShift * (this.rand.nextDouble());
				entityItem.motionZ = zShift * (this.rand.nextDouble());
				this.worldObj.spawnEntityInWorld(entityItem);
			}
			this.setDead();
			this.worldObj.setEntityState(this, (byte)3);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte updateId) {
		switch(updateId) {
		case 2:
			// Hurt
			break;
		case 3:
			// Death
			this.health = 0;
			this.setDead();
			break;
		}
	}
	
	
	
	@Override
	public boolean canBeCollidedWith() { return true; }
	
	@Override
	public boolean canBePushed() {
		return true;
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		double positiveXShift = this.boundingBox.maxX - player.boundingBox.minX;
		double positiveYShift = this.boundingBox.maxY - player.boundingBox.minY;
		double positiveZShift = this.boundingBox.maxZ - player.boundingBox.minZ;
		double negativeXShift = this.boundingBox.minX - player.boundingBox.maxX;
		double negativeYShift = this.boundingBox.minY - player.boundingBox.maxY;
		double negativeZShift = this.boundingBox.minZ - player.boundingBox.maxZ;
		boolean useXShift = !(positiveXShift <= 0 || negativeXShift >= 0);
		boolean useYShift = !(positiveYShift <= 0 || negativeYShift >= 0);
		boolean useZShift = !(positiveZShift <= 0 || negativeZShift >= 0);
		double xShift = Math.abs(positiveXShift) < Math.abs(negativeXShift) ? positiveXShift : negativeXShift;
		double yShift = Math.abs(positiveYShift) < Math.abs(negativeYShift) ? positiveYShift : negativeYShift;
		double zShift = Math.abs(positiveZShift) < Math.abs(negativeZShift) ? positiveZShift : negativeZShift;
		if(useXShift && Math.abs(xShift) <= Math.abs(yShift) && Math.abs(xShift) <= Math.abs(zShift)) {
			player.setVelocity(xShift, player.motionY, player.motionZ);
		} else if(useYShift && Math.abs(yShift) <= Math.abs(xShift) && Math.abs(yShift) <= Math.abs(zShift)) {
			player.setVelocity(player.motionX, yShift, player.motionZ);
		} else if(useZShift && Math.abs(zShift) <= Math.abs(xShift) && Math.abs(zShift) <= Math.abs(yShift)) {
			player.setVelocity(player.motionX, player.motionY, zShift);
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		this.customRotYaw = nbt.getFloat(NBTData.CUSTOM_ROTATION_YAW);
		this.customRotPitch = nbt.getFloat(NBTData.CUSTOM_ROTATION_PITCH);
		this.isLanded = nbt.getBoolean("meteorLanding");
		this.landingYaw = nbt.getFloat("landingYaw");
		this.landingPitch = nbt.getFloat("landingPitch");
		this.landingPosX = nbt.getDouble("landingPosX");
		this.landingPosY = nbt.getDouble("landingPosY");
		this.landingPosZ = nbt.getDouble("landingPosZ");
		this.size = nbt.getDouble("meteorSize");
		this.health = nbt.getInteger("meteorHealth");
		this.hasCausedImpactExplosion = nbt.getBoolean("hasCausedImpactExplosion");
		this.init(false);
	}
	
	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		this.customRotYaw = data.readFloat();
		this.customRotPitch = data.readFloat();
		this.isLanded = data.readBoolean();
		this.landingYaw = data.readFloat();
		this.landingPitch = data.readFloat();
		this.landingPosX = data.readDouble();
		this.landingPosY = data.readDouble();
		this.landingPosZ = data.readDouble();
		this.size = data.readDouble();
		this.health = data.readInt();
		this.hasCausedImpactExplosion = data.readBoolean();
		this.init(false);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat(NBTData.CUSTOM_ROTATION_YAW, this.customRotYaw);
		nbt.setFloat(NBTData.CUSTOM_ROTATION_PITCH, this.customRotPitch);
		nbt.setBoolean("meteorLanding", this.isLanded);
		nbt.setFloat("landingYaw", this.landingYaw);
		nbt.setFloat("landingPitch", this.landingPitch);
		nbt.setDouble("landingPosX", this.landingPosX);
		nbt.setDouble("landingPosY", this.landingPosY);
		nbt.setDouble("landingPosZ", this.landingPosZ);
		nbt.setDouble("meteorSize", this.size);
		nbt.setInteger("meteorHealth", this.health);
		nbt.setBoolean("hasCausedImpactExplosion", this.hasCausedImpactExplosion);
	}
	
	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeFloat(this.customRotYaw);
		data.writeFloat(this.customRotPitch);
		data.writeBoolean(this.isLanded);
		data.writeFloat(this.landingYaw);
		data.writeFloat(this.landingPitch);
		data.writeDouble(this.landingPosX);
		data.writeDouble(this.landingPosY);
		data.writeDouble(this.landingPosZ);
		data.writeDouble(this.size);
		data.writeInt(this.health);
		data.writeBoolean(this.hasCausedImpactExplosion);
	}
*/
}
