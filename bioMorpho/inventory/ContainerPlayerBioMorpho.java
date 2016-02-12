package bioMorpho.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import bioMorpho.data.InventoryData;
import bioMorpho.item.BioMorphoItems;
import bioMorpho.item.ItemGemOfGenesis;
import bioMorpho.manager.InventoryManager;

/**
 * @author Brighton Ancelin
 *
 */
public class ContainerPlayerBioMorpho extends Container {
	
	public final EntityPlayer player;
	
	public final Slot gemSlot;
	
	public ContainerPlayerBioMorpho(EntityPlayer player) {
		this.player = player;
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player.inventory, i, 8+(i*18), 142));
		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(player.inventory, j+((i+1)*9), 8+(j*18), 84+(i*18)));
			}
		}
		this.gemSlot = this.addSlotToContainer(new SlotExclusive(InventoryManager.getSidedInstance().getCustomInventoryForPlayer(player.username), InventoryData.BIO_MORPHO_PLAYER_GEM_INDEX, 80, 35, new ItemStack(BioMorphoItems.gemOfGenesis)));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) { return true; }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		Slot slot = (Slot)this.inventorySlots.get(index);
		ItemStack itemStack = null;
		
		if(slot != null && slot.getHasStack()) {
			ItemStack originalItemStack = slot.getStack();
			itemStack = originalItemStack.copy();
			if(slot == this.gemSlot) {
				// Parameters: stack, min Slot index (inclusive), max Slot index (exclusive), should go in reverse order for checking
				if(!this.mergeItemStack(originalItemStack, 0, 36, false)) return null;
			}
			else if(itemStack.getItem() == BioMorphoItems.gemOfGenesis && slot != this.gemSlot) {
				int destinationIndex = this.gemSlot.slotNumber;
				if(!this.mergeItemStack(originalItemStack, destinationIndex, destinationIndex+1, false)) return null;
			}
			
			if(originalItemStack.stackSize == 0) slot.putStack(null);
			else slot.onSlotChanged();
			
			if(originalItemStack.stackSize == itemStack.stackSize) return null;
		}
		return itemStack;
	}
	
}
