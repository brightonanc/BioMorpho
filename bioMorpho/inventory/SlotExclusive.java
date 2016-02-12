package bioMorpho.inventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Brighton Ancelin
 *
 */
public class SlotExclusive extends Slot {
	
	public final List<ItemStack> allowedItems = new ArrayList<ItemStack>();
	
	public SlotExclusive(IInventory inventory, int slotIndex, int x, int y) {
		super(inventory, slotIndex, x, y);
	}
	
	public SlotExclusive(IInventory inventory, int slotIndex, int x, int y, ItemStack exclusiveItem) {
		this(inventory, slotIndex, x, y);
		this.addAllowedItem(exclusiveItem);
	}
	
	public void addAllowedItem(ItemStack itemStack) {
		this.allowedItems.add(itemStack);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		for(ItemStack curStack : this.allowedItems) {
			if(curStack.itemID == itemStack.itemID && curStack.getItemDamage() == itemStack.getItemDamage()) return true;
		}
		return false;
	}
	
}
