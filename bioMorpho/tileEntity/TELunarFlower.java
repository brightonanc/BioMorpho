package bioMorpho.tileEntity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import bioMorpho.entity.particle.EntityCustomSmokeFX;
import bioMorpho.item.BioMorphoItems;
import bioMorpho.util.MathUtils;
import bioMorpho.util.RandomUtils;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class TELunarFlower extends TEBioMorpho {
	
	public static final int chargePerOrb = 70000;
	
	/**CLIENT SIDE ONLY*/
	public float alphaVal;
	
	protected ItemStack lunarOrb;
	protected int charge = 0;
	
	public boolean hasLunarOrb() {return this.lunarOrb != null;}
	
	public ItemStack getLunarOrb() {return this.lunarOrb;}
	public ItemStack getLunarOrbCopy() {return this.lunarOrb != null ? this.lunarOrb.copy() : null;}
	
	public boolean onRightClick() {
		if(!this.worldObj.isRemote) {
			if(this.lunarOrb != null) {
				if(!this.worldObj.isRemote) {
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord+0.5D, this.yCoord+0.3D, this.zCoord + 0.5D, this.lunarOrb));
				}
				this.lunarOrb = null;
				this.forceSendUpdatePacket();
				return true;
			}
			return false;
		}
		else return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!this.worldObj.isRemote) {
			if(this.canAbsorbLunarRadiation()) {
				int moonCharge = Math.abs(this.worldObj.getMoonPhase() - 4);
				this.charge += moonCharge;
				if(this.charge >= chargePerOrb) {
					this.lunarOrb = new ItemStack(BioMorphoItems.lunarOrb, 1, 0);
					this.charge -= chargePerOrb;
					this.forceSendUpdatePacket();
				}
			}
		} else {
			if(this.canAbsorbLunarRadiation()) {
				if(this.worldObj.getWorldTime() % 7 == 0) {
					int flag = rand.nextInt(8);
					double x = this.xCoord + 0.5D + (MathUtils.cos(flag*45)/7D);
					double z = this.zCoord + 0.5D + (MathUtils.sin(flag*45)/7D);
					double y = this.yCoord + 0.1D;
					double motionX = ((this.xCoord + 0.5D) - x) / 20D;
					double motionZ = ((this.zCoord + 0.5D) - z) / 20D;
					double motionY = ((this.yCoord + 0.1D) - y) / 20D;
					EntityCustomSmokeFX fx = new EntityCustomSmokeFX(this.worldObj, x, y, z, motionX, motionY, motionZ, 0.14F, true);
					fx.setMaxParticleAge(20);
					fx.setRBGColorF(0xff, 0xff, 0xff);
					FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
				}
			}
			int alphaRate = 34;
			float targetAlpha = this.calcAlpha();
			this.alphaVal += (targetAlpha-this.alphaVal)/alphaRate;
			if(Math.abs(targetAlpha-this.alphaVal) <= alphaRate/2048F) this.alphaVal = targetAlpha;
		}
	}
	
	private boolean canAbsorbLunarRadiation() {
		boolean isNight = this.worldObj.getWorldTime() % 24000 >= 12000;
		boolean isEmpty = this.lunarOrb == null;
		boolean canSeeMoon = this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord);
		boolean isDarkEnough = RandomUtils.getAbsLightValue(this.worldObj, this.xCoord, this.yCoord, this.zCoord) <= 7;
		return isNight && isEmpty && canSeeMoon && isDarkEnough;
	}
	
	@Override
	public void setWorldObj(World world) {
		super.setWorldObj(world);
		this.alphaVal = this.calcAlpha();
	}
	
	public float calcAlpha() {
		if(this.worldObj.isRemote) {
			int light = RandomUtils.getAbsLightValue(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			if(light <= 7) return 1F;
			else return ((15F-light)/9F) + (1F/9F);
		}
		return Float.NaN;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtCompound) {
		super.writeToNBT(nbtCompound);
		if(this.lunarOrb != null) nbtCompound.setTag("lunarOrb", this.lunarOrb.writeToNBT(new NBTTagCompound()));
		nbtCompound.setInteger("lunarCharge", this.charge);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtCompound) {
		super.readFromNBT(nbtCompound);
		if(nbtCompound.hasKey("lunarOrb")) this.lunarOrb = ItemStack.loadItemStackFromNBT(nbtCompound.getCompoundTag("lunarOrb"));
		else this.lunarOrb = null;
		this.charge = nbtCompound.getInteger("lunarCharge");
	}
	
}
