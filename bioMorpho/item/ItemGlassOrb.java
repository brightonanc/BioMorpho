package bioMorpho.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import bioMorpho.entity.EntityEnergeticGlassOrb;
import bioMorpho.item.BasicItems.ItemBasic;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemGlassOrb extends ItemBasic {
	
	public ItemGlassOrb(int id, String unlocalizedName) {
		super(id, unlocalizedName);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			world.spawnEntityInWorld(new EntityEnergeticGlassOrb(world, player));
		}
		itemStack.stackSize--;
		return itemStack;
	}
	
}
