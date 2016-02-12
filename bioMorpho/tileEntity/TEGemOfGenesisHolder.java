package bioMorpho.tileEntity;

import java.util.ArrayList;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import bioMorpho.BioMorpho;
import bioMorpho.data.GuiData;
import bioMorpho.etc.Nucleobases;
import bioMorpho.helper.EffectHelper;
import bioMorpho.helper.LifeformExpenseHandler;
import bioMorpho.helper.LifeformHelper;
import bioMorpho.item.BioMorphoItems;
import bioMorpho.lifeform.Lifeform;


/**
 * @author Brighton Ancelin
 *
 */
public class TEGemOfGenesisHolder extends TEBioMorpho {
	
	private ItemStack gemOfGenesis;
	
	protected ArrayList<Lifeform> targetLifeforms;
	protected int[] targetIndexPointers;
	protected int[] nucleobaseCosts;
	protected boolean isAbsorbingForGem = false;
		
	public ItemStack getGem() {return this.gemOfGenesis;}
	public ItemStack getGemCopy() {return this.gemOfGenesis.copy();}
	
	protected void setGem(ItemStack itemStack) {
		this.gemOfGenesis = itemStack != null ? itemStack.copy() : null;
		this.onInventoryChanged();
	}
	
	public boolean isAbsorbing() {return this.isAbsorbingForGem;}
	
	public boolean onRightClick(EntityPlayer player) {
		if(!this.worldObj.isRemote) {
			if(this.isAbsorbingForGem) return false;
			ItemStack itemStack = player.getCurrentEquippedItem();
			if(this.gemOfGenesis == null) {
				if(!player.isSneaking()) return this.attemptSetGem(itemStack);
				else return false;
			} else {
				if(!player.isSneaking()) {
					if(!this.worldObj.isRemote) {
						player.openGui(BioMorpho.instance, GuiData.GEM_OF_GENESIS_HOLDER_ID, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
					}
					return true;
				} else {
					if(itemStack == null) return this.attemptRemoveGem(player);
					else return false;
				}
			}
		}
		else return true;
	}
	
	protected boolean attemptSetGem(ItemStack itemStack) {
		if(itemStack != null && itemStack.getItem() == BioMorphoItems.gemOfGenesis && this.gemOfGenesis == null) {
			this.setGem(itemStack);
			itemStack.stackSize--;
			this.forceSendUpdatePacket();
			return true;
		}
		return false;
	}
	
	protected boolean attemptRemoveGem(EntityPlayer player) {
		if(this.gemOfGenesis != null && player.getCurrentEquippedItem() == null && !this.isAbsorbingForGem) {
			player.inventory.setInventorySlotContents(player.inventory.currentItem, this.gemOfGenesis.copy());
			this.setGem(null);
			this.forceSendUpdatePacket();
			return true;
		}
		return false;
	}
	
	public ArrayList<TENucleobaseCrystal> getAmbientNucleobaseCrystals() {
		ArrayList<TENucleobaseCrystal> tileEntities = new ArrayList<TENucleobaseCrystal>();
		int radius = 3;
		for(int x = this.xCoord-radius; x < this.xCoord+radius; x++) {
			for(int y = this.yCoord-radius; y < this.yCoord+radius; y++) {
				for(int z = this.zCoord-radius; z < this.zCoord+radius; z++) {
					TileEntity tileEntity = this.worldObj.getBlockTileEntity(x, y, z);
					if(tileEntity != null && tileEntity instanceof TENucleobaseCrystal) {
						tileEntities.add((TENucleobaseCrystal)tileEntity);
					}
				}
			}
		}
		return tileEntities;
	}
	
	@Deprecated
	public int drainAmbientCharge(Nucleobases nucleobase, int amount) {
		if(amount == 0) return 0;
		int remainingAmt = amount;
		for(TENucleobaseCrystal curCrystal : this.getAmbientNucleobaseCrystals()) {
			if(curCrystal.getNucleobase() == nucleobase) {
				boolean shouldBreak;
				int drainAmt;
				if(remainingAmt <= curCrystal.getCharge()) {
					drainAmt = remainingAmt;
					shouldBreak = true;
				} else {
					drainAmt = curCrystal.getCharge();
					shouldBreak = false;
				}
				curCrystal.addCharge(-drainAmt);
				curCrystal.forceSendUpdatePacket();
				remainingAmt -= drainAmt;
				if(shouldBreak) break;
			}
		}
		return remainingAmt;
	}
	
	public void attemptDrain(Nucleobases nucleobase) {
		for(TENucleobaseCrystal curCrystal : this.getAmbientNucleobaseCrystals()) {
			if(curCrystal.getNucleobase() == nucleobase) {
				this.nucleobaseCosts[nucleobase.ordinal()] -= curCrystal.drainCharge(this.nucleobaseCosts[nucleobase.ordinal()]);
				if(this.nucleobaseCosts[nucleobase.ordinal()] == 0) break;
			}
		}
	}
	public void attemptFill(Nucleobases nucleobase) {
		for(TENucleobaseCrystal curCrystal : this.getAmbientNucleobaseCrystals()) {
			if(curCrystal.getNucleobase() == nucleobase) {
				this.nucleobaseCosts[nucleobase.ordinal()] += curCrystal.fillCharge(-this.nucleobaseCosts[nucleobase.ordinal()]);
				if(this.nucleobaseCosts[nucleobase.ordinal()] == 0) break;
			}
		}
	}
	
	public void beginAbsorbingForChanges(ArrayList<Lifeform> initLifeforms, ArrayList<Lifeform> newLifeforms, int[] indexPointers) {
		this.targetLifeforms = newLifeforms;
		this.targetIndexPointers = indexPointers;
		this.nucleobaseCosts = LifeformExpenseHandler.getCostForChanges(initLifeforms, newLifeforms);
		this.isAbsorbingForGem = true;
		this.forceSendUpdatePacket();
	}
	
	public void finishAbsorption() {
		LifeformHelper.setLifeforms(this.gemOfGenesis, this.targetLifeforms);
		LifeformHelper.setIndexPointers(this.gemOfGenesis, this.targetIndexPointers);
		this.disposeOfExcessFillViaNucleobaseGoo();
		for(TENucleobaseCrystal curCrystal : this.getAmbientNucleobaseCrystals()) curCrystal.endDraining();
		this.isAbsorbingForGem = false;
		this.nucleobaseCosts = null;
		this.targetLifeforms = null;
		this.targetIndexPointers = null;
		this.forceSendUpdatePacket();
	}
	
	public void disposeOfExcessFillViaNucleobaseGoo() {
		Nucleobases curNucleobase;
		curNucleobase = Nucleobases.ADENINE;
		if(this.nucleobaseCosts[curNucleobase.ordinal()] <= 0) {
			ItemStack itemStack = new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 
					-this.nucleobaseCosts[curNucleobase.ordinal()]/TENucleobaseCrystal.valuePerGoo, curNucleobase.ordinal());
			EntityItem entityItem = 
					new EntityItem(this.worldObj, this.xCoord+0.5D, this.yCoord+1D, this.zCoord+0.5D, itemStack);
			if(itemStack.stackSize > 0) this.worldObj.spawnEntityInWorld(entityItem);
		}
		curNucleobase = Nucleobases.GUANINE;
		if(this.nucleobaseCosts[curNucleobase.ordinal()] <= 0) {
			ItemStack itemStack = new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 
					-this.nucleobaseCosts[curNucleobase.ordinal()]/TENucleobaseCrystal.valuePerGoo, curNucleobase.ordinal());
			EntityItem entityItem = 
					new EntityItem(this.worldObj, this.xCoord+0.5D, this.yCoord+1D, this.zCoord+0.5D, itemStack);
			if(itemStack.stackSize > 0) this.worldObj.spawnEntityInWorld(entityItem);
		}
		curNucleobase = Nucleobases.THYMINE;
		if(this.nucleobaseCosts[curNucleobase.ordinal()] <= 0) {
			ItemStack itemStack = new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 
					-this.nucleobaseCosts[curNucleobase.ordinal()]/TENucleobaseCrystal.valuePerGoo, curNucleobase.ordinal());
			EntityItem entityItem = 
					new EntityItem(this.worldObj, this.xCoord+0.5D, this.yCoord+1D, this.zCoord+0.5D, itemStack);
			if(itemStack.stackSize > 0) this.worldObj.spawnEntityInWorld(entityItem);
		}
		curNucleobase = Nucleobases.CYTOSINE;
		if(this.nucleobaseCosts[curNucleobase.ordinal()] <= 0) {
			ItemStack itemStack = new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 
					-this.nucleobaseCosts[curNucleobase.ordinal()]/TENucleobaseCrystal.valuePerGoo, curNucleobase.ordinal());
			EntityItem entityItem = 
					new EntityItem(this.worldObj, this.xCoord+0.5D, this.yCoord+1D, this.zCoord+0.5D, itemStack);
			if(itemStack.stackSize > 0) this.worldObj.spawnEntityInWorld(entityItem);
		}
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!this.worldObj.isRemote) {
			if(this.isAbsorbingForGem) {/*
				int absorptionAmt = 1;
				int absorptionAmtA = absorptionAmt;
				int absorptionAmtG = absorptionAmt;
				int absorptionAmtT = absorptionAmt;
				int absorptionAmtC = absorptionAmt;
				if(absorptionAmtA > this.nucleobaseCosts[Nucleobases.ADENINE.ordinal()]) {
					absorptionAmtA = this.nucleobaseCosts[Nucleobases.ADENINE.ordinal()];
				}
				if(absorptionAmtG > this.nucleobaseCosts[Nucleobases.GUANINE.ordinal()]) {
					absorptionAmtG = this.nucleobaseCosts[Nucleobases.GUANINE.ordinal()];
				}
				if(absorptionAmtT > this.nucleobaseCosts[Nucleobases.THYMINE.ordinal()]) {
					absorptionAmtT = this.nucleobaseCosts[Nucleobases.THYMINE.ordinal()];
				}
				if(absorptionAmtC > this.nucleobaseCosts[Nucleobases.CYTOSINE.ordinal()]) {
					absorptionAmtC = this.nucleobaseCosts[Nucleobases.CYTOSINE.ordinal()];
				}
				int leftoverA = this.drainAmbientCharge(Nucleobases.ADENINE, absorptionAmtA);
				int leftoverG = this.drainAmbientCharge(Nucleobases.GUANINE, absorptionAmtG);
				int leftoverT = this.drainAmbientCharge(Nucleobases.THYMINE, absorptionAmtT);
				int leftoverC = this.drainAmbientCharge(Nucleobases.CYTOSINE, absorptionAmtC);
				this.nucleobaseCosts[Nucleobases.ADENINE.ordinal()] -= (absorptionAmtA - leftoverA);
				this.nucleobaseCosts[Nucleobases.GUANINE.ordinal()] -= (absorptionAmtG - leftoverG);
				this.nucleobaseCosts[Nucleobases.THYMINE.ordinal()] -= (absorptionAmtT - leftoverT);
				this.nucleobaseCosts[Nucleobases.CYTOSINE.ordinal()] -= (absorptionAmtC - leftoverC);
				if(this.nucleobaseCosts[Nucleobases.ADENINE.ordinal()] == 0 && 
						this.nucleobaseCosts[Nucleobases.GUANINE.ordinal()] == 0 && 
						this.nucleobaseCosts[Nucleobases.THYMINE.ordinal()] == 0 && 
						this.nucleobaseCosts[Nucleobases.CYTOSINE.ordinal()] == 0) {
					this.finishAbsorption();
				}*/
				for(TENucleobaseCrystal curCrystal : this.getAmbientNucleobaseCrystals()) curCrystal.beginPossibleDrainTick();
				
				Nucleobases curNucleobase;
				curNucleobase = Nucleobases.ADENINE;
				if(this.nucleobaseCosts[curNucleobase.ordinal()] > 0) this.attemptDrain(curNucleobase);
				else if(this.nucleobaseCosts[curNucleobase.ordinal()] < 0) this.attemptFill(curNucleobase);
				curNucleobase = Nucleobases.GUANINE;
				if(this.nucleobaseCosts[curNucleobase.ordinal()] > 0) this.attemptDrain(curNucleobase);
				else if(this.nucleobaseCosts[curNucleobase.ordinal()] < 0) this.attemptFill(curNucleobase);
				curNucleobase = Nucleobases.THYMINE;
				if(this.nucleobaseCosts[curNucleobase.ordinal()] > 0) this.attemptDrain(curNucleobase);
				else if(this.nucleobaseCosts[curNucleobase.ordinal()] < 0) this.attemptFill(curNucleobase);
				curNucleobase = Nucleobases.CYTOSINE;
				if(this.nucleobaseCosts[curNucleobase.ordinal()] > 0) this.attemptDrain(curNucleobase);
				else if(this.nucleobaseCosts[curNucleobase.ordinal()] < 0) this.attemptFill(curNucleobase);
				
				for(TENucleobaseCrystal curCrystal : this.getAmbientNucleobaseCrystals()) curCrystal.endPossibleDrainTick();
				
				// It's less than or equal to so that there is a one-time chance to fill surrounding crystals with charge.
				// This addresses the issue of not enough crystals to hold all the extra charge
				if(this.nucleobaseCosts[Nucleobases.ADENINE.ordinal()] <= 0 &&
						this.nucleobaseCosts[Nucleobases.GUANINE.ordinal()] <= 0 &&
						this.nucleobaseCosts[Nucleobases.THYMINE.ordinal()] <= 0 &&
						this.nucleobaseCosts[Nucleobases.CYTOSINE.ordinal()] <= 0) {
					this.finishAbsorption();
				}
			}
		}
		else {
			if(this.isAbsorbingForGem) {
				for(TENucleobaseCrystal curCrystal : this.getAmbientNucleobaseCrystals()) {
					if(curCrystal.isDraining()) {
						for(int i = 0; i < 7; i++) EffectHelper.createNucleobaseFXToHolderWithRandShift(this, curCrystal);
					}
				}
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtCompound) {
		super.writeToNBT(nbtCompound);
		if(this.gemOfGenesis != null) nbtCompound.setTag("gemOfGenesis_Tag", this.gemOfGenesis.writeToNBT(new NBTTagCompound()));
		nbtCompound.setBoolean("isAbsorbing", this.isAbsorbingForGem);
		if(this.nucleobaseCosts != null) nbtCompound.setIntArray("nuclBaseCost", this.nucleobaseCosts);
		if(this.targetLifeforms != null) {
			NBTTagList nbtLifeformList = new NBTTagList();
			for(Lifeform curLifeform : this.targetLifeforms) nbtLifeformList.appendTag(curLifeform.writeToNBT(new NBTTagCompound()));
			nbtCompound.setTag("lifeformList", nbtLifeformList);
		}
		if(this.targetIndexPointers != null) {
			nbtCompound.setIntArray("indexPointers", this.targetIndexPointers);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtCompound) {
		super.readFromNBT(nbtCompound);
		if(nbtCompound.hasKey("gemOfGenesis_Tag")) this.gemOfGenesis = ItemStack.loadItemStackFromNBT(nbtCompound.getCompoundTag("gemOfGenesis_Tag"));
		else this.gemOfGenesis = null;
		this.isAbsorbingForGem = nbtCompound.getBoolean("isAbsorbing");
		if(nbtCompound.hasKey("nuclBaseCost")) this.nucleobaseCosts = nbtCompound.getIntArray("nuclBaseCost");
		else this.nucleobaseCosts = null;
		if(nbtCompound.hasKey("lifeformList")) {
			NBTTagList nbtLifeformList = nbtCompound.getTagList("lifeformList");
			this.targetLifeforms = new ArrayList<Lifeform>();
			for(int i = 0; i < nbtLifeformList.tagCount(); i++) this.targetLifeforms.add(Lifeform.loadFromNBT((NBTTagCompound)nbtLifeformList.tagAt(i)));
		}
		else this.targetLifeforms = null;
		if(nbtCompound.hasKey("indexPointers")) {
			this.targetIndexPointers = nbtCompound.getIntArray("indexPointers");
		}
		else this.targetIndexPointers = null;
	}
	
}
