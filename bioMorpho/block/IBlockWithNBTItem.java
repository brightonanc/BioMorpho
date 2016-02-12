package bioMorpho.block;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author Brighton Ancelin
 *
 */
public interface IBlockWithNBTItem {
	
	public abstract ItemStack createItemStackWithWorld(World world, int x, int y, int z);
	
}
