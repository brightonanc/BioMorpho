package bioMorpho.inventory;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import bioMorpho.manager.InventoryManager;
import bioMorpho.manager.InventoryManager.InventoryManagerServer;

/**
 * @author Brighton Ancelin
 *
 */
public class InventoryBioMorphoPlayer extends InventoryBasic {
	
	public static final String NBT_KEY ="All data";
	
	public final String playerUsername;
	
	protected boolean ignoreInvChangeOnce = false;
	
	public InventoryBioMorphoPlayer(String playerUsername) {
		// Parameters: name, doesn't need localization, inventory size
		super("BioMorphoPlayerInventory", true, 1);
		this.playerUsername = playerUsername;;
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		if(this.ignoreInvChangeOnce) {
			this.ignoreInvChangeOnce = false;
			return;
		}
		InventoryManagerServer imServer = InventoryManager.getServerInstance();
		if(imServer != null) imServer.sendInventoryUpdateToAllClients(this.playerUsername);
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbtItemStacksList = new NBTTagList();
		for(int i = 0; i < this.getSizeInventory(); i++) {
			if(this.getStackInSlot(i) == null) continue;
			NBTTagCompound nbtCurItemCompound = new NBTTagCompound();
			nbtCurItemCompound.setInteger("Slot index/count", i);
			nbtItemStacksList.appendTag(this.getStackInSlot(i).writeToNBT(nbtCurItemCompound));
		}
		nbt.setTag(NBT_KEY, nbtItemStacksList);
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList nbtItemStacksList = nbt.getTagList(NBT_KEY);
		for(int i = 0; i < nbtItemStacksList.tagCount(); i++) {
			NBTTagCompound nbtCurItemCompound = (NBTTagCompound)nbtItemStacksList.tagAt(i);
			// Setting this.ignoreInvChangeOnce to true will stop the onInventoryChanged() method 
			// from forcing an unnecessary update that causes crashes on world loads
			this.ignoreInvChangeOnce = true;
			this.setInventorySlotContents(nbtCurItemCompound.getInteger("Slot index/count"), ItemStack.loadItemStackFromNBT(nbtCurItemCompound));
		}
	}
		
}
