package bioMorpho.tileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import bioMorpho.data.NBTData;
import bioMorpho.etc.Nucleobases;
import bioMorpho.item.BioMorphoItems;

/**
 * @author Brighton Ancelin
 *
 */
public class TENucleobaseCrystal extends TEBioMorpho {
	
	protected Nucleobases nucleobase;
	protected int charge;
	
	protected boolean isUber = false;
	
	/** Currently only used for client updates for rendering particles */
	protected boolean prevIsDraining;
	/** Currently only used for client updates for rendering particles */
	protected boolean isDraining;
	
	public static final int baseMaxCharge = 3000;
	public static final int uberMaxCharge = Integer.MAX_VALUE;
	public static final int baseDrainRate = 7;
	public static final int uberDrainRate = 700;
	public static final int valuePerGoo = 300;
	
	/**
	 * Blank constructor MUST exist for server loads.
	 */
	public TENucleobaseCrystal() {
		this(Nucleobases.ADENINE, 0, false);
	}
	
	public TENucleobaseCrystal(Nucleobases nucleobase, int charge, boolean isUberCrystal) {
		this.nucleobase = nucleobase;
		this.charge = charge;
		this.prevIsDraining = this.isDraining = false;
		this.isUber = isUberCrystal;
	}
	
	public void setNucleobase(Nucleobases nucleobase) { this.nucleobase = nucleobase; }
	public void setCharge(int charge) { this.charge = charge; }
	public void setUberness(boolean uberness) {this.isUber = uberness;}
	
	public Nucleobases getNucleobase() { return this.nucleobase; }
	public int getCharge() { return this.charge; }
	public int getMaxCharge() { return this.isUber ? uberMaxCharge : baseMaxCharge; }
	public int getDrainRate() {return this.isUber ? uberDrainRate : baseDrainRate;}
	
	public void addCharge(int chargeBonus) {
		this.charge += chargeBonus;
	}
	
	public int getRemainingChargeStorage() {
		return this.getMaxCharge() - this.charge;
	}
	
	public boolean isAtMaxCharge() {return this.charge >= this.getMaxCharge();}
	public boolean isUber() {return this.isUber;}
	
	public boolean isDraining() {return this.isDraining;}
	public void endDraining() {
		if(this.isDraining != false) {
			this.isDraining = false;
			this.forceSendUpdatePacket();
		}
	}
	
	public void beginPossibleDrainTick() {
		this.prevIsDraining = this.isDraining;
		this.isDraining = false;
	}
	
	public int drainCharge(int maxDrainRequest) {
		int drainAmount = this.getDrainRate();
		if(drainAmount > maxDrainRequest) drainAmount = maxDrainRequest;
		if(this.charge-drainAmount < 0) drainAmount = this.charge;
		this.charge -= drainAmount;
		if(drainAmount > 0) this.isDraining = true;
		this.forceSendUpdatePacket();
		return drainAmount;
	}
	
	public void endPossibleDrainTick() {
		if(this.prevIsDraining != this.isDraining) this.forceSendUpdatePacket();
	}
	
	public int fillCharge(int maxFillRequest) {
		int fillAmount = maxFillRequest;
		if(fillAmount > this.getRemainingChargeStorage()) fillAmount = this.getRemainingChargeStorage();
		this.charge += fillAmount;
		this.forceSendUpdatePacket();
		return fillAmount;
	}
	
	public boolean onRightClick(EntityPlayer player) {
		if(!this.worldObj.isRemote) {
			ItemStack itemStack = player.getCurrentEquippedItem();
			if(itemStack != null) return this.attemptAbsorbCharge(itemStack);
			return false;
		}
		else return true;
	}
	
	/**
	 * @return Success of absorption
	 */
	public boolean attemptAbsorbCharge(ItemStack itemStack) {
		if(!(itemStack.itemID == BioMorphoItems.nucleobaseGoo.itemID && itemStack.getItemDamage() == this.nucleobase.ordinal())) {
			return false;
		}
		int chargeBonus = itemStack.stackSize * valuePerGoo;
		if(chargeBonus > this.getRemainingChargeStorage()) {
			chargeBonus = 0;
			int quantityUsed = 0;
			while(chargeBonus+valuePerGoo <= this.getRemainingChargeStorage()) {
				chargeBonus += valuePerGoo;
				quantityUsed++;
			}
			itemStack.stackSize -= quantityUsed;
		}
		else itemStack.stackSize = 0;
		this.addCharge(chargeBonus);
		this.forceSendUpdatePacket();
		return true;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtCompound) {
		super.writeToNBT(nbtCompound);
		nbtCompound.setInteger(NBTData.NUCLEOBASE_ENUM, this.nucleobase.ordinal());
		nbtCompound.setInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE, this.charge);
		nbtCompound.setBoolean("draining", this.isDraining);
		nbtCompound.setBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS, this.isUber);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtCompound) {
		super.readFromNBT(nbtCompound);
		this.nucleobase = Nucleobases.values()[nbtCompound.getInteger(NBTData.NUCLEOBASE_ENUM)];
		this.charge = nbtCompound.getInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE);
		this.isDraining = nbtCompound.getBoolean("draining");
		this.isUber = nbtCompound.getBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS);
	}
	
}
